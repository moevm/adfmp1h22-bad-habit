package com.example.badhabits

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.badhabits.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    fun goToHabit(view: View) {
        val intent = Intent(this, Habit::class.java)
        val habit_name = (view as MaterialButton).text.toString()
        intent.putExtra(Habit.HABIT, habit_name)
        intent.putExtra(Habit.DATE, "2022-01-02")
        intent.putExtra(Habit.ShowNotifications, false)
        startActivity(intent)
    }
    fun goToStatistic(view: View){
        val intent = Intent(this, Statistic::class.java)
        startActivity(intent)
    }
    fun goToHowDay(view: View){
        val intent = Intent(this, HowDay::class.java)
        startActivity(intent)
    }
    fun goToHowDayBefore(view: View){
        val intent = Intent(this, HowDayBefore::class.java)
        startActivity(intent)
    }
}