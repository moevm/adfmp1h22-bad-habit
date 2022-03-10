package com.example.badhabits

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.sql.Date

class HowDayBefore : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object{
        const val DATE = "date"
    }
    var list_of_items = arrayOf("Курение", "Алкоголизм", "Наркомания")

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_day_before)
            showDate()
        var spinner = findViewById<Spinner>(R.id.spinner2)
        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    fun onNoButtonClick(view: View){
        // логика сохранения ответа

    }
    fun onYesButtonClick(view: View){
        // логика сохранения ответа

    }

    fun getDate(view: View){
        val intent = Intent(this, ChooseDate::class.java)
        startActivity(intent)
    }

    fun showDate(){
        val name = intent.getStringExtra(DATE)
        findViewById<TextView>(R.id.date).text = name
    }
}