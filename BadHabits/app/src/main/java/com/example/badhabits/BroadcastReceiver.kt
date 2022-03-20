package com.example.badhabits;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {
        companion object{
                const val HABIT = "habit"
        }
        override fun onReceive(context: Context, intent: Intent) {
                val service = Intent(context, NotificationService::class.java)
                val habit = intent.getStringExtra("broadcast.Message")
                service.putExtra("reason", intent.getStringExtra("reason"))
                service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
                service.putExtra(HABIT, habit)
                context.startService(service)
        }
}
