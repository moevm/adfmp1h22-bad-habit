package com.example.badhabits

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.ArraySet
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.withTimeout
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule


class Habit : AppCompatActivity() {
    lateinit var habit: String
    lateinit var timer: Timer
    var showNotifications = false

    private val notificationTimeout = 20000L
    private val APP_PREFERENCES:String = "userSettings"
    private val APP_PREFERENCES_DATES:String = "userDates"
    private val APP_PREFERENCES_HABITS:String = "userHabits"

    lateinit var mSettings: SharedPreferences
    lateinit var mSettingsDates: SharedPreferences
    lateinit var mSettingsHabits: SharedPreferences

    private var timerTask: TimerTask? = null

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun getPeriod(between: Period?): Int {
            val days = (between?.years ?: 3) * 365 + (between?.months ?: 3) * 30 + (between?.days
                ?: 3)
            return days
        }

        const val HABIT = "habit"
        const val DATE = "date"
        const val ShowNotifications = "showNotifications"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habbit)
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mSettingsDates = getSharedPreferences(APP_PREFERENCES_DATES, Context.MODE_PRIVATE);
        mSettingsHabits = getSharedPreferences(APP_PREFERENCES_HABITS, Context.MODE_PRIVATE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showHabitName()

        val hasVisited: Boolean = mSettingsDates.getBoolean("hasVisited" + habit, false)
        if (!hasVisited) {
            val e: SharedPreferences.Editor = mSettingsDates.edit()
            e.putBoolean("hasVisited" + habit, true)
            e.putString("habits"+ habit, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
            e.commit() // не забудьте подтвердить изменения
        }

        showDate()
        showNotifications = intent.getBooleanExtra(Habit.ShowNotifications, false)
        if(showNotifications) {
            startTimer(findViewById(R.id.enableNotifications))
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
            startTimer(view)
            showNotifications = true
            val editor: SharedPreferences.Editor = mSettings!!.edit()
            editor.putBoolean(habit, showNotifications)
            editor.apply()
        } else {
            showNotifications = false
            val editor: SharedPreferences.Editor = mSettings!!.edit()
            editor.putBoolean(habit, showNotifications)
            editor.apply()
        }
        setButtonText(showNotifications)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun getPeriod(period: Period): Int {
        val days = period.years * 365 + period.months * 30 + period.days
        return days
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDate(){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val date = LocalDate.parse(intent.getStringExtra(Habit.DATE), formatter)
        val period = Period.between(date, LocalDate.now())
        val days = getPeriod(period)
        val dateInFormat = date.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        val editor: SharedPreferences.Editor = mSettingsDates!!.edit()
        editor.putString("habits" + habit, date.toString())
        editor.putBoolean("hasVisited" + habit, true)
        editor.apply()

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
    private  fun startTimer(view: View) {
        timerTask?.cancel()
        timerTask = Timer().schedule(notificationTimeout) {
            val showNotifications = mSettings!!.getBoolean(habit, false)
            print("$habit $showNotifications")
            if(showNotifications) {
                notifyUser(view)
                startTimer(view)
            }
        }
    }

    private fun notifyUser(view: View) {
        val service = Intent(view.context, NotificationService::class.java)
        service.putExtra("reason", "notification")
        service.putExtra("timestamp", Calendar.getInstance().timeInMillis)
        service.putExtra(HABIT, habit.lowercase())
        startService(service)
    }

    fun removeHabit(view: View)
    {
            var habits = HashSet<String>()
            if(mSettingsHabits?.contains("habits") == true) {
                habits =
                    (mSettingsHabits.getStringSet("habits", emptySet()) as HashSet<String>)
                if(habits.contains(habit))
                {
                    habits.remove(habit)
                }
                if(habits.size < 1)
                {
                    habits.add("Курение")
                    habits.add("Алкоголизм")
                    habits.add("Чавкание")
                }
                val editor:SharedPreferences.Editor = mSettingsHabits.edit()
                editor.clear()
                editor.putBoolean("hasVisited", true)
                editor.putStringSet("habits", habits)
                editor.apply()
            }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}
