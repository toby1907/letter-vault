package com.google.developers.lettervault.ui.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.developers.lettervault.R
import com.google.developers.lettervault.ui.list.LetterViewModel
import com.google.developers.lettervault.util.DataViewModelFactory
import java.util.*

class AddLetterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    private lateinit var viewModel: AddLetterViewModel
    private lateinit var letterTitle: TextInputEditText
    private lateinit var letterContent: TextInputEditText
    private lateinit var dueDateTextView: TextView
    var day = 0
    var month: Int = 0
    var year: Int = 0
    private  var dueDateInMillis: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_letter)
initializeViews()
        val factory = DataViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(AddLetterViewModel::class.java)
        val systemTime = System.currentTimeMillis()

        dueDateTextView.text = systemTime.toString()
    }


    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        viewModel.setExpirationTime(p1, p2)
        TODO("Not yet implemented")
    }
    private fun initializeViews() {
        letterTitle = findViewById(R.id.title_textView)
        letterContent = findViewById(R.id.content_textView)
        dueDateTextView = findViewById(R.id.calender_text)

        dueDateTextView.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month,day)
            datePickerDialog.show()
        }

        }

}