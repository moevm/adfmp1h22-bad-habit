package com.example.badhabits

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.fragment.app.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class Statistic : AppCompatActivity(R.layout.activity_statistic) {
    private val sources = ArrayList<StatisticElement>()

    var filename = "userHabbits"

    val APP_PREFERENCES_DATES:String = "userDates"
    var mSettingsDates: SharedPreferences? = null

    var list_of_items = arrayOf("Курение", "Алкоголизм", "Чавкание")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSettingsDates = getSharedPreferences(APP_PREFERENCES_DATES, Context.MODE_PRIVATE);
        this.initSources()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        var habitId = 0

        if (savedInstanceState == null) {
            val constraintLayout = findViewById<ConstraintLayout>(R.id.statistic_scroll_layout)
            var lastFragmentId = R.id.statistic_scroll

            for (element in sources) {

                var fragment = FragmentContainerView(this)
                fragment.id = ("habitId_$habitId").hashCode()
                constraintLayout.addView(fragment)
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add(fragment.id, StatisticFragment(element))
                }
                val constraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)
                constraintSet.connect(
                    fragment.id,
                    TOP, // put text view top side bottom of button
                    lastFragmentId, // button to put text view bellow it
                    BOTTOM
                )
                constraintSet.applyTo(constraintLayout)
                lastFragmentId = fragment.id
                habitId++
            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initSources() {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val datesSmoke = ArrayList<LocalDate>()
        val datesAlcohol = ArrayList<LocalDate>()

        var userDateFromFile:JSONArray = JSONArray()

        try {
            // открываем поток для чтения
            val br = BufferedReader(
                InputStreamReader(openFileInput(filename))
            )
            var str: String? = ""
            // читаем содержимое
            while (br.readLine().also { str = it } != null) {
                val obj = JSONObject(str.toString())
                userDateFromFile.put(obj)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Log.d("fileOut", inputFileData.toString())

        Log.d("fileOut", userDateFromFile.toString())

        for(habbitName in list_of_items)
        {
            var currentDate: String = "31.12.2019"
            mSettingsDates?.all

            if(mSettingsDates?.contains(habbitName) == true) {
                currentDate =
                    mSettingsDates!!.getString(habbitName,
                        "")!!
            }
            //Log.d("Habbit", habbitName)
            //Log.d("Date", mSettingsDates?.all.toString())
            val dateToCompare = LocalDate.parse(currentDate, formatter2)
            val datesDis = ArrayList<LocalDate>()

            for (i in 0 until userDateFromFile.length())
            {
                val item = userDateFromFile.getJSONObject(i)
                if(habbitName == item.getString("habbit"))
                {
                    //Log.d("distruption",item.getBoolean("distruption").toString())
                    if(item.getBoolean("distruption") == true)
                    {
                        //Log.d("Item", item.getString("habbit"))

                        val tmpDate = LocalDate.parse(item.getString("Date"), formatter2)
                        //val tmpDate = LocalDate.parse("16.03.2022", formatter)
                        if(tmpDate.isAfter(dateToCompare))
                        {
                            datesDis.add(tmpDate)
                        }

                    }
                }
            }
            sources.add(StatisticElement(habbitName, dateToCompare, datesDis))

        }

        //datesSmoke.add(LocalDate.parse("31.12.2019", formatter))
        //datesSmoke.add(LocalDate.parse("11.11.2021", formatter))
        //datesAlcohol.add(LocalDate.parse("07.02.2022", formatter))
        //sources.add(StatisticElement("Курение", LocalDate.parse("31.12.2018", formatter), datesSmoke))
        //sources.add(StatisticElement("Алкоголизм", LocalDate.parse("03.01.2022", formatter), datesAlcohol))
        //sources.add(StatisticElement("Чавкание", LocalDate.parse("13.10.2021", formatter), ArrayList<LocalDate>()))
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
