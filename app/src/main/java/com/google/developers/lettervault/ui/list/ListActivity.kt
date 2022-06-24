package com.google.developers.lettervault.ui.list

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.developers.lettervault.R
import com.google.developers.lettervault.ui.setting.SettingActivity
import com.google.developers.lettervault.util.DataViewModelFactory
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_list.*

class ListActivity : AppCompatActivity() {

    private lateinit var viewModel: LetterViewModel
    private  val adapter by lazy {
        LetterAdapter{
            viewModel.filter("All")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = DataViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(LetterViewModel::class.java)

        val pixelSize = resources.getDimensionPixelSize(R.dimen.item_decoration_margin)
        letter_list_recycler_view.addItemDecoration(ItemDecoration(pixelSize))
//recyclerView = findViewById(R.id.letter_list_recycler_view)
        viewModel.letters.observe(this){
            letter_list_recycler_view.adapter =adapter
            letter_list_recycler_view.layoutManager = LinearLayoutManager(this)
           adapter.submitList(it)

        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.future, R.id.opened, R.id.all -> {
                item.isChecked = !item.isChecked
                val itemName = resources.getResourceEntryName(item.itemId)
                try {
                    viewModel.filter(itemName)
                } catch (e: IllegalArgumentException) {
                    Log.e(this.javaClass.name, "Invalid application state: ${e.message}")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
