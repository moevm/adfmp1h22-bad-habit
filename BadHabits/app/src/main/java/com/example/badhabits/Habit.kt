package com.example.badhabits

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class Habit : AppCompatActivity() {
    companion object{
        const val HABIT = "habit"
        const val DATE = "2022-01-01"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habbit)
        showHabitName()
        showDate()
    }

    private fun showHabitName(){
        val name = intent.getStringExtra(HABIT)
        findViewById<TextView>(R.id.habitName).text = name
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun getDate(view: View){
        val intent = Intent(this, ChooseHabitDate::class.java)
        intent.putExtra(ChooseHabitDate.HABIT, findViewById<TextView>(R.id.habitName).text)
        startActivity(intent)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDate(){
        val date = LocalDate.parse(intent.getStringExtra(Habit.DATE))
        val period = Period.between(date, LocalDate.now())
        val days = period.years * 365 + period.months * 30 + period.days
        findViewById<TextView>(R.id.daysCountDown).text = "Дней без привычки: $days"
        findViewById<TextView>(R.id.dateCountDown).text = "Начало отказа: ${date.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy"))}"
    }
}