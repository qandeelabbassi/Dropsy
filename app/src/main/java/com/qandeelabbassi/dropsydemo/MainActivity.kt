package com.qandeelabbassi.dropsydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // set listener
        dropdown_fruits.setItemClickListener { i, item ->
            Toast.makeText(this, "${item.text} clicked at index $i", Toast.LENGTH_SHORT).show()
        }
        // programmatically show and dismiss DropDownView
        btn_show.setOnClickListener { dropdown_fruits.showDropdown() }
        btn_hide.setOnClickListener { dropdown_fruits.hideDropdown() }
    }
}