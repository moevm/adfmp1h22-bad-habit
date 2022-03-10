package com.example.badhabits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView

class ChooseDate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_date)
        var calendarView = findViewById<CalendarView>(R.id.calendarView)
        val intent = Intent(this, HowDayBefore::class.java)
        var date = ""
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = "$dayOfMonth.${month + 1}.$year"
            intent.putExtra(HowDayBefore.DATE, date)
            startActivity(intent)
        }
    }
}