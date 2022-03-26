package com.example.badhabits

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import org.json.JSONObject
import java.io.*
import java.sql.Date
import java.util.*

class HowDayBefore : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object{
        const val DATE = "date"
    }
    var list_of_items = arrayOf("Курение", "Алкоголизм", "Чавкание")

    var disruptionWas: Boolean = false
    var habbit:String = ""
    var currentDate: String = ""
    var moodToday: String = ""
    var feelToday: String = ""

    var filename = "userHabbits"

    val APP_PREFERENCES_HABITS:String = "userHabits"
    lateinit var mSettingsHabits: SharedPreferences

    val file_controller: FileController = FileController()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_day_before)
            showDate()
        var spinner = findViewById<Spinner>(R.id.spinner2)

        mSettingsHabits = getSharedPreferences(APP_PREFERENCES_HABITS, Context.MODE_PRIVATE)
        var habits = HashSet<String>()
        if(mSettingsHabits?.contains("habits") == true) habits =
            (mSettingsHabits.getStringSet("habits", emptySet()) as HashSet<String>)

        var habitsTmp = Array<String>(habits.size, init= {i:Int -> String.toString()})
        var counter:Int = 0
        for(i in habits)
        {
            habitsTmp[counter] = i
            counter++
        }

        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, habitsTmp)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    fun onNoButtonClick(view: View){
        // логика сохранения ответа
        disruptionWas = false
    }
    fun onYesButtonClick(view: View){
        // логика сохранения ответа
        disruptionWas = true
    }

    fun getDate(view: View){
        val intent = Intent(this, ChooseDate::class.java)
        startActivity(intent)
    }

    fun showDate(){
        val name = intent.getStringExtra(DATE)
        findViewById<TextView>(R.id.date).text = name
    }

    fun onSaveButtonClick(view: View){
        // логика сохранения ответа
        var seekBarM: SeekBar = findViewById(R.id.moodSeek);
        var seekBarF: SeekBar = findViewById(R.id.feelSeek);
        var spinnerH:Spinner = findViewById(R.id.spinner2);
        moodToday = seekBarM.getProgress().toString()
        feelToday = seekBarF.getProgress().toString()
        habbit = spinnerH.selectedItem.toString()
        currentDate = findViewById<TextView>(R.id.date).text.toString()


        val rootObject= JSONObject()
        rootObject.put("habbit", habbit)
        rootObject.put("distruption", disruptionWas)
        rootObject.put("Date",currentDate)
        rootObject.put("Mood",moodToday)
        rootObject.put("feel",feelToday)


        var tmpUserData:String = rootObject.toString() + "\n"
        file_controller.saveToFile(tmpUserData, openFileOutput(filename, MODE_APPEND))

        Toast.makeText(this@HowDayBefore, "Сохранено", Toast.LENGTH_SHORT).show()

    }
}