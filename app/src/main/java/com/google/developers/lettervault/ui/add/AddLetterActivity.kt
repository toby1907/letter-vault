package com.google.developers.lettervault.ui.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.google.developers.lettervault.R
import com.google.developers.lettervault.ui.list.ListActivity
import com.google.developers.lettervault.util.DataViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone.SHORT

class AddLetterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    private lateinit var viewModel: AddLetterViewModel
    private lateinit var letterTitle: TextInputEditText
    private lateinit var letterContent: TextInputEditText
    private lateinit var dueDateTextView: TextView
    private lateinit var addButton: Button
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var setDay = 0
    var setMonth: Int = 0
    var setYear: Int = 0
    private  var dueDateInMillis: Long? = null
    var  minute = 0
    var hourOfDay = 0
    var setMinute = 0
    var setHourOfDay = 0
    var subject : String = ""
    var content : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_letter)
        initializeViews()
        val factory = DataViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(AddLetterViewModel::class.java)

        dueDateTextView.text = getString(R.string.set_time_date)

        addButton.setOnClickListener {
            addLetter(subject,content)
            Toast.makeText(this, "letter added", Toast.LENGTH_SHORT).show()
            Log.d("letter",it.toString() )
            val addIntent = Intent(this, ListActivity::class.java)

            startActivity(addIntent)
        }
    }
    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                subject = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
       letterTitle.addTextChangedListener(titleWatcher)
        //for content EditText

        val contentWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
               content = sequence.toString().trim()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        letterContent.addTextChangedListener(contentWatcher)
    }




    override fun onDateSet(p0: DatePicker?, p1year: Int, p2month: Int, p3dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
      setYear = p1year
        setDay = p3dayOfMonth
        setMonth = p2month
        hourOfDay = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(this, this, hourOfDay, minute,false)
        timePickerDialog.show()



    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val calendar = Calendar.getInstance()
        setHourOfDay = p1
        setMinute = p2

        val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        dueDateTextView.text = dateFormat.format(calendar.time)



        calendar.set(setYear,setMonth,setDay,setHourOfDay,setMinute)
        dueDateInMillis = calendar.timeInMillis

        /*  val dateFormat = SimpleDateFormat("hh:mm:ss")
          val string = dateFormat.format(calendar.time)*/
var timeSign : String =""
        if (p1 == 0) {



            timeSign = "AM";
        }
        else if (p1 == 12) {


        }
        else if (p1 > 12) {



          timeSign = "PM";

        }
        else {

            timeSign = "AM";
        }

        val timeString = "Date: $setDay/$setMonth/$setYear ,Time:$setHourOfDay:$setMinute$timeSign"

        dueDateTextView.text = timeString
     /*   dateFormat.format(calendar.time).also { dueDateTextView.text = it
      }*/


    }
    private fun initializeViews() {
        letterTitle = findViewById(R.id.title_textView)
        letterContent = findViewById(R.id.content_textView)
        dueDateTextView = findViewById(R.id.calender_text)
        addButton = findViewById(R.id.add_button)

        //setting the dueDate OnClickListener
        dueDateTextView.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)

         //   dueDateInMillis = calendar.timeInMillis
          val datePickerDialog =
                DatePickerDialog(this, this, year, month,day)
            datePickerDialog.show()
        }


        }

    private fun addLetter(title: String, letterContent: String) {
viewModel.save(title,letterContent)
        viewModel.setExpirationTime(setHourOfDay,setMinute)
    }


}