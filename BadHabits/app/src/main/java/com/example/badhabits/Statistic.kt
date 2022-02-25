package com.example.badhabits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Statistic : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}