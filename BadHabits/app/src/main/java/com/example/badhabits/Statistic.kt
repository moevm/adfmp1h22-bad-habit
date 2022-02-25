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
            val constraintLayout = findViewById<ConstraintLayout>(R.id.statistic_view)
            var lastFragmentId = PARENT_ID

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
                var margin = 100
                if(lastFragmentId != PARENT_ID) {
                    margin = 500
                }
                constraintSet.connect(
                    fragment.id,
                    TOP, // put text view top side bottom of button
                    lastFragmentId, // button to put text view bellow it
                    TOP, // button bottom to put text view bellow it,
                    margin
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
        sources.add(StatisticElement("Курение", Date(), 2, 1 ,3, dates))
        sources.add(StatisticElement("Алкоголизм", Date(), 1, 2 ,3, dates))
        sources.add(StatisticElement("Чавкание", Date(), 22, 1 ,22, dates))
    }
    fun returnToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
