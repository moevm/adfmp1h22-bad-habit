package com.example.badhabits

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.ArraySet
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class DataController {


    @RequiresApi(Build.VERSION_CODES.M)
    fun loadOnCreateMainActivity(mSettingsHabits: SharedPreferences)
    {
        val hasVisited: Boolean = mSettingsHabits.getBoolean("hasVisited", false)
        if (!hasVisited) {
            // выводим нужную активность
            //Log.d("cond",hasVisited.toString())
            val habits = ArraySet<String>()
            habits.add("Курение")
            habits.add("Алкоголизм")
            habits.add("Чавкание")
            val e: SharedPreferences.Editor = mSettingsHabits.edit()
            e.putBoolean("hasVisited", true)
            e.putStringSet("habits", habits)
            e.apply() // не забудьте подтвердить изменения
        }
    }

    fun getDateFromDates(mSettingsDates: SharedPreferences, habit_name: String): String {
        var currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        if(mSettingsDates?.contains("habits" + habit_name) == true) {
            currentDate =
                mSettingsDates!!.getString("habits" + habit_name,
                    "2022-10-02")!!
        }
        return currentDate
    }

    fun getNotificationFromSettings(mSettings:SharedPreferences, habit_name: String): Boolean {
        var notificationBoolean = false
        if(mSettings?.contains(habit_name) == true) {
            notificationBoolean = mSettings!!.getBoolean(habit_name, false)
        }
        return notificationBoolean
    }

    fun putDataIntoStorage(mSettingsHabits: SharedPreferences, userInput: String)
    {
        var habits = HashSet<String>()
        var habitsTmp = HashSet<String>()
        if(mSettingsHabits?.contains("habits") == true) habits =
            (mSettingsHabits.getStringSet("habits", emptySet()) as HashSet<String>)

        var flagContain:Boolean = false

        for(i in habits)
        {
            if(i == userInput)
            {
                flagContain = true
            }
        }
        habitsTmp = habits
        if(!flagContain)
        {
            habitsTmp.add(userInput)
        }
        //Log.d("Habbit",habits.toString())
        val e: SharedPreferences.Editor = mSettingsHabits.edit()
        e.clear()
        e.putBoolean("hasVisited", true)
        e.putStringSet("habits", habitsTmp)
        e.commit()
    }

    fun checkVisited(mSettingsDates: SharedPreferences, userInput: String)
    {
        val hasVisited: Boolean = mSettingsDates.getBoolean("hasVisited" + userInput, false)
        if (!hasVisited) {
            // выводим нужную активность
            val ed: SharedPreferences.Editor = mSettingsDates.edit()
            ed.putBoolean("hasVisited" + userInput, true)
            ed.putString("habits"+ userInput, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
            ed.commit() // не забудьте подтвердить изменения
        }
    }

}