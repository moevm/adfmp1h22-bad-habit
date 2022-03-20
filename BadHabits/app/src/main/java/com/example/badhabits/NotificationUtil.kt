package com.example.badhabits

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

class NotificationUtils {
    fun setNotification(habit: String, timeInMilliSeconds: Long, activity: Activity) {
        if (timeInMilliSeconds > 0) {
            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java) // AlarmReceiver1 = broadcast receiver
            val calendar = Calendar.getInstance()
            alarmIntent.removeExtra("broadcast.Message")
            alarmIntent.putExtra("reason", "notification")
            alarmIntent.action = "Habit.Name";
            alarmIntent.putExtra("broadcast.Message", habit);
            alarmIntent.putExtra("timestamp", calendar.timeInMillis)
            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_MUTABLE)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, timeInMilliSeconds, pendingIntent)
        }
    }

}
