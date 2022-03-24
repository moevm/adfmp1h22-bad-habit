package com.example.badhabits

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class HowDay : AppCompatActivity() ,AdapterView.OnItemSelectedListener{

    var list_of_items = arrayOf("Курение", "Алкоголизм", "Наркомания")

    var disruptionWas: Boolean = false
    var habbit:String = ""
    var currentDate: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
    var moodToday: String = ""
    var feelToday: String = ""

    var filename = "userHabbits"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_day)
        var spinner = findViewById<Spinner>(R.id.spinner)
        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
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
    fun onSaveButtonClick(view: View){
        // логика сохранения ответа
        var seekBarM:SeekBar = findViewById(R.id.moodSeek);
        var seekBarF:SeekBar = findViewById(R.id.feelSeek);
        var spinnerH:Spinner = findViewById(R.id.spinner);
        moodToday = seekBarM.getProgress().toString()
        feelToday = seekBarF.getProgress().toString()
        habbit = spinnerH.selectedItem.toString()

        val rootObject= JSONObject()
        rootObject.put("habbit", habbit)
        rootObject.put("distruption", disruptionWas)
        rootObject.put("Date",currentDate)
        rootObject.put("Mood",moodToday)
        rootObject.put("feel",feelToday)


        var tmpUserData:String = rootObject.toString() + "\n"
        try {
            // отрываем поток для записи
            val bw = BufferedWriter(
                OutputStreamWriter(openFileOutput(filename, MODE_APPEND))
            )
            // пишем данные
            bw.write(tmpUserData)
            // закрываем поток
            bw.close()
            Log.d("fileIn", "Файл записан")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Toast.makeText(this@HowDay, "Сохранено", Toast.LENGTH_SHORT).show()
        try {
            // открываем поток для чтения
            val br = BufferedReader(
                InputStreamReader(openFileInput(filename))
            )
            var str: String? = ""
            // читаем содержимое
            while (br.readLine().also { str = it } != null) {
                Log.d("fileOut", str!!)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }



        Log.d("Mood", rootObject.toString())
    }

}


