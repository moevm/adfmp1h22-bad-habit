package com.example.badhabits

import android.app.ActionBar
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.ArraySet
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import com.example.badhabits.DataController as DataController


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var appBarConfiguration: AppBarConfiguration

    val context: Context = this
    private var button: FloatingActionButton? = null
    private val final_text: TextView? = null

    val APP_PREFERENCES:String = "userSettings"
    val APP_PREFERENCES_DATES:String = "userDates"
    val APP_PREFERENCES_HABITS:String = "userHabits"

    lateinit var mSettings: SharedPreferences
    lateinit var mSettingsDates: SharedPreferences
    lateinit var mSettingsHabits: SharedPreferences

    val dataController: DataController = DataController()

    var list_of_advices= arrayOf(
        "Курить - здоровью вредить!",
        "Быть упорным – это очень важное качество, но без мотивации у вас вряд ли что-либо получится.",
        "Вам нужно твердо решиться начать борьбу. Здесь нельзя проявлять слабость, будьте стойкими в своем решении.",
        "Триггеры – это те обстоятельства, которые способны спровоцировать вас вновь обратиться к вредным привычкам.",
        "Близкие люди могут помочь вам в борьбе, если вы посвятите их в свои намерения.",
        "Заниматься самогипнозом – вполне реально.",
        "В борьбе с нежелательными привычками нельзя торопиться. Важно не форсировать события.",
    )


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.prompt_button)
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        mSettingsDates = getSharedPreferences(APP_PREFERENCES_DATES, Context.MODE_PRIVATE)
        mSettingsHabits = getSharedPreferences(APP_PREFERENCES_HABITS, Context.MODE_PRIVATE)
        //Log.d("AllMain", mSettingsHabits.all.toString())
        dataController.loadOnCreateMainActivity(mSettingsHabits)

        showHabitButtons()
        showRandowAdvice()
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

        var currentDate: String = dataController.getDateFromDates(mSettingsDates, habit_name)
        //intent.putExtra(Habit.DATE, "2022-10-02")
        intent.putExtra(Habit.DATE, currentDate)

        var notificationBoolean = dataController.getNotificationFromSettings(mSettings, habit_name)

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
    @RequiresApi(Build.VERSION_CODES.M)
    fun newHabbitDialog(view: View)
    {
        val li = LayoutInflater.from(context)
        var promptsView: View = li.inflate(R.layout.new_habbit_dialog, null)

        val mDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        mDialogBuilder.setView(promptsView)

        val userInput:EditText = promptsView.findViewById(R.id.input_text)
        mDialogBuilder.setCancelable(false)
        mDialogBuilder.setPositiveButton("Сохранить", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            //Log.d("Habbit",userInput.getText().toString())
            dataController.putDataIntoStorage(mSettingsHabits, userInput.getText().toString())

            val ll:LinearLayout = findViewById<LinearLayout>(R.id.ll1)
            ll.removeAllViews()
            showHabitButtons()

            dataController.checkVisited(mSettingsDates,userInput.getText().toString())
        })
        mDialogBuilder.setNegativeButton("Отмена", DialogInterface.OnClickListener { dialog, which ->
            //showHabitButtons()
            dialog.cancel()
        })

        var alertDialog: AlertDialog = mDialogBuilder.create()
        alertDialog.show()
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            goToHabit(p0)
        }
    }

    fun showRandowAdvice()
    {
        val adviceT:TextView = findViewById(R.id.advice)
        val numOfAdvice = (0..6).random()
        adviceT.text = list_of_advices[numOfAdvice]
    }

    fun showHabitButtons()
    {
        var habits = HashSet<String>()
        if(mSettingsHabits?.contains("habits") == true) habits =
            (mSettingsHabits.getStringSet("habits", emptySet()) as HashSet<String>)



        val ll:LinearLayout = findViewById<LinearLayout>(R.id.ll1)
        var cp:LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        cp.gravity = Gravity.CENTER_HORIZONTAL
        cp.width = 250
        cp.height = 60
        cp.topMargin = 10

        for(i in habits)
        {
            val myButton = com.google.android.material.button.MaterialButton(this)
            myButton.setText(i)
            myButton.setBackgroundColor(Color.parseColor("#6200EE"))
            myButton.setTextColor(Color.parseColor("#FFFFFF"))
            myButton.setOnClickListener(this)
            ll.addView(myButton, cp)
        }
    }
}

private fun FloatingActionButton.setOnClickListener(onClickListener: DialogInterface.OnClickListener) {

}
