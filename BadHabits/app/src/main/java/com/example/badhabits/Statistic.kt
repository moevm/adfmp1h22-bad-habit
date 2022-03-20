package com.example.badhabits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.fragment.app.*
import java.util.*
import kotlin.collections.ArrayList

class Statistic : AppCompatActivity(R.layout.activity_statistic) {
    private val sources = ArrayList<StatisticElement>()
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
    private fun initSources() {
        var dates = ArrayList<Date>()
        dates.add(Date())
        dates.add(Date(122, 10, 12, 18, 12, 31))
        dates.add(Date(122, 3, 12, 18, 12, 31))
        dates.add(Date(122, 4, 12, 18, 12, 31))
        sources.add(StatisticElement("Курение", Date(), 2, 1 ,3, dates))
        sources.add(StatisticElement("Алкоголизм", Date(), 1, 2 ,3, dates))
        sources.add(StatisticElement("Чавкание", Date(), 22, 1 ,22, dates))
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
