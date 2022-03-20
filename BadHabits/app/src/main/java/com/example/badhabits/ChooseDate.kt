package com.example.badhabits

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import java.time.LocalDate

open class ChooseDateBase: AppCompatActivity() {
    lateinit var calendarView : CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_date)
        this.calendarView = findViewById<CalendarView>(R.id.calendarView)
        this.calendarView.maxDate = System.currentTimeMillis();
    }
}

class ChooseDate : ChooseDateBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, HowDayBefore::class.java)
        var date = ""
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = "$dayOfMonth.${month + 1}.$year"
            intent.putExtra(HowDayBefore.DATE, date)
            startActivity(intent)
        }
    }
}

class ChooseHabitDate : ChooseDateBase() {
    companion object{
        const val HABIT = "habit"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val habitName = intent.getStringExtra(HABIT)
        val intent = Intent(this, Habit::class.java)
        var date = ""
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = LocalDate.of(year, month + 1, dayOfMonth).toString()
            intent.putExtra(Habit.DATE, date)
            intent.putExtra(Habit.HABIT, habitName)
            startActivity(intent)
        }
    }
}