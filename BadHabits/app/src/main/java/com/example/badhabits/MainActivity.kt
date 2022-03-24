package com.example.badhabits

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    val context: Context = this
    private var button: FloatingActionButton? = null
    private val final_text: TextView? = null

    val APP_PREFERENCES:String = "userSettings"
    val APP_PREFERENCES_DATES:String = "userDates"

    var mSettings: SharedPreferences? = null
    var mSettingsDates: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.prompt_button);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mSettingsDates = getSharedPreferences(APP_PREFERENCES_DATES, Context.MODE_PRIVATE);
        //final_text = (TextView) findViewById(R.id.final_text);
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

        //Log.d("Name", habit_name)

        var currentDate: String = SimpleDateFormat("yyyy.dd.MM", Locale.getDefault()).format(Date())
        if(mSettingsDates?.contains(habit_name) == true) {
            currentDate =
                mSettingsDates!!.getString(habit_name,
                        "2022.10.02")!!
        }
        //Log.d("Date", currentDate)
        //intent.putExtra(Habit.DATE, "2022-10-02")
        intent.putExtra(Habit.DATE, currentDate)

        var notificationBoolean = false
        if(mSettings?.contains(habit_name) == true) {
            notificationBoolean = mSettings!!.getBoolean(habit_name, false)
        }

        intent.putExtra(Habit.ShowNotifications, notificationBoolean)
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
    fun newHabbitDialog(view: View)
    {
        val li = LayoutInflater.from(context)
        var promptsView: View = li.inflate(R.layout.new_habbit_dialog, null)

        val mDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        mDialogBuilder.setView(promptsView)

        val userInput:EditText = promptsView.findViewById(R.id.input_text)

        mDialogBuilder.setCancelable(false)
        mDialogBuilder.setPositiveButton("Ok", DialogInterface.OnClickListener()
        { dialogInterface: DialogInterface, i: Int ->
            fun onClick(dialog: DialogInterface,id: Int)
            {
                Log.d("Habbit",userInput.getText().toString())
            }
        })
        mDialogBuilder.setNegativeButton("Отмена", DialogInterface.OnClickListener()
        { dialogInterface: DialogInterface, i: Int ->
            fun onClick(dialog: DialogInterface,id: Int)
            {
                dialog.cancel()
            }
        })

        var alertDialog: AlertDialog = mDialogBuilder.create()
        alertDialog.show()
    }
}

private fun FloatingActionButton.setOnClickListener(onClickListener: DialogInterface.OnClickListener) {

}
