package com.example.badhabits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Habbit : AppCompatActivity() {
    companion object{
        const val HABBIT = "habbit"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habbit)
        showHabbitName()
    }

    fun showHabbitName(){
        val name = intent.getStringExtra(HABBIT)
        findViewById<TextView>(R.id.habitName).text = name
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}