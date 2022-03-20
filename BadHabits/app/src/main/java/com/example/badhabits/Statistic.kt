package com.example.badhabits

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.fragment.app.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Statistic : AppCompatActivity(R.layout.activity_statistic) {
    private val sources = ArrayList<StatisticElement>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initSources()
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
        val datesSmoke = ArrayList<LocalDate>()
        val datesAlcohol = ArrayList<LocalDate>()
        datesSmoke.add(LocalDate.parse("31.12.2019", formatter))
        datesSmoke.add(LocalDate.parse("11.11.2021", formatter))
        datesAlcohol.add(LocalDate.parse("07.02.2022", formatter))
        sources.add(StatisticElement("Курение", LocalDate.parse("31.12.2018", formatter), datesSmoke))
        sources.add(StatisticElement("Алкоголизм", LocalDate.parse("03.01.2022", formatter), datesAlcohol))
        sources.add(StatisticElement("Чавкание", LocalDate.parse("13.10.2021", formatter), ArrayList<LocalDate>()))
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
