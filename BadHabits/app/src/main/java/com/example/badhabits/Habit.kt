package com.example.badhabits

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.material.button.MaterialButton
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.fixedRateTimer

class Habit : AppCompatActivity() {
    lateinit var habit: String
    lateinit var timer: Timer
    var showNotifications = false

    companion object{
        const val HABIT = "habit"
        const val DATE = "date"
        const val ShowNotifications = "showNotifications"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habbit)
        showHabitName()
        showDate()
        showNotifications = intent.getBooleanExtra(ShowNotifications, false)
        if(showNotifications) {
            timer = startTimer(findViewById(R.id.enableNotifications))
        }
        setButtonText(showNotifications)
    }
    private fun showHabitName(){
        val name = intent.getStringExtra(HABIT)
        findViewById<TextView>(R.id.habitName).text = name
        habit = name.toString()
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
    fun switchNotifications(view: View) {
        if(!showNotifications) {
            timer = startTimer(view)
            showNotifications = true
        } else {
            showNotifications = false
            timer.cancel()
        }
        setButtonText(showNotifications)
        //NotificationUtils().setNotification(habit.lowercase(), 60000, this@Habit)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDate(){
        val date = LocalDate.parse(intent.getStringExtra(Habit.DATE))
        val period = Period.between(date, LocalDate.now())
        val days = period.years * 365 + period.months * 30 + period.days
        val dateInFormat = date.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        findViewById<TextView>(R.id.daysCountDown).text = "Дней без привычки: $days"
        findViewById<TextView>(R.id.dateCountDown).text = "Начало отказа: $dateInFormat"
    }
    private fun setButtonText(showNotifications: Boolean) {
        if(!showNotifications) {
            findViewById<MaterialButton>(R.id.enableNotifications).text = "Включить оповещения"
        } else {
            findViewById<MaterialButton>(R.id.enableNotifications).text = "Выключить оповещения"
        }
    }
    private  fun startTimer(view: View): Timer {
        return fixedRateTimer("timer",false,0,10000){
            this@Habit.runOnUiThread {
                val service = Intent(view.context, NotificationService::class.java)
                //val habit = intent.getStringExtra("broadcast.Message")
                service.putExtra("reason", "notification")
                service.putExtra("timestamp", Calendar.getInstance().timeInMillis)
                service.putExtra(HABIT, habit.lowercase())
                startService(service)
            }
        }
    }
}
