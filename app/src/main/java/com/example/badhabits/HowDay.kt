package com.example.badhabits

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class HowDay : AppCompatActivity() ,AdapterView.OnItemSelectedListener{

    var list_of_items = arrayOf("Курение", "Алкоголизм", "Чавкание")

    var disruptionWas: Boolean = false
    var habbit:String = ""
    var currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    var moodToday: String = ""
    var feelToday: String = ""

    var filename = "userHabbits"

    val APP_PREFERENCES_HABITS:String = "userHabits"
    lateinit var mSettingsHabits: SharedPreferences

    val file_controller:FileController = FileController()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_day)

        findViewById<TextView>(R.id.date2).text = currentDate

        mSettingsHabits = getSharedPreferences(APP_PREFERENCES_HABITS, Context.MODE_PRIVATE)
        var habits = HashSet<String>()
        if(mSettingsHabits?.contains("habits") == true) habits =
            (mSettingsHabits.getStringSet("habits", emptySet()) as HashSet<String>)

        var habitsTmp = Array<String>(habits.size, init= {i:Int -> String.toString()})
        var spinner = findViewById<Spinner>(R.id.spinner)
        var counter:Int = 0
        for(i in habits)
        {
            habitsTmp[counter] = i
            counter++
        }
        spinner!!.onItemSelectedListener = this
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, habitsTmp)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = aa
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var seekBarM:SeekBar = findViewById(R.id.moodSeek);
        var seekBarF:SeekBar = findViewById(R.id.feelSeek);
        findViewById<TextView>(R.id.textViewFeel).text = "0%"
        findViewById<TextView>(R.id.textViewMood).text = "0%"
        seekBarM?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                findViewById<TextView>(R.id.textViewMood).text   = (progress*10).toString()  + "%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }


        })
        seekBarF?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                findViewById<TextView>(R.id.textViewFeel).text   = (progress*10).toString()  + "%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }


        })
    }

    fun writeOnDrawable(drawableId: Int, text: String?): BitmapDrawable? {
        val bm =
            BitmapFactory.decodeResource(resources, drawableId).copy(Bitmap.Config.ARGB_8888, true)
        val paint = Paint()
        paint.setStyle(Paint.Style.FILL)
        paint.setColor(Color.BLACK)
        paint.setTextSize(20F)
        val canvas = Canvas(bm)
        if (text != null) {
            canvas.drawText(text, 0F, (bm.height / 2).toFloat(), paint)
        }
        return BitmapDrawable(bm)
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    fun onNoButtonClick(view: View){
        disruptionWas = false
        Toast.makeText(this@HowDay, "Без срыва", Toast.LENGTH_SHORT).show()

    }
    fun onYesButtonClick(view: View){
        disruptionWas = true
        Toast.makeText(this@HowDay, "Срыв был", Toast.LENGTH_SHORT).show()
    }
    fun onSaveButtonClick(view: View){
        var seekBarM:SeekBar = findViewById(R.id.moodSeek);
        var seekBarF:SeekBar = findViewById(R.id.feelSeek);
        var spinnerH:Spinner = findViewById(R.id.spinner);
        moodToday = (seekBarM.getProgress()*10).toString()
        feelToday = (seekBarF.getProgress()*10).toString()
        habbit = spinnerH.selectedItem.toString()

        val rootObject= JSONObject()
        rootObject.put("habbit", habbit)
        rootObject.put("distruption", disruptionWas)
        rootObject.put("Date",currentDate)
        rootObject.put("Mood",moodToday)
        rootObject.put("feel",feelToday)


        var tmpUserData:String = rootObject.toString() + "\n"
        file_controller.saveToFile(tmpUserData,openFileOutput(filename, MODE_APPEND))

        Toast.makeText(this@HowDay, "Сохранено", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}


