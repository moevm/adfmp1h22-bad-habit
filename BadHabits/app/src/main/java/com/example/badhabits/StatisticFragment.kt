package com.example.badhabits
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import androidx.core.view.marginStart
import com.example.badhabits.databinding.FragmentStatisticBinding
import java.time.format.DateTimeFormatter

class StatisticFragment : Fragment {
    private var _source: StatisticElement? = null
    private var _binding: FragmentStatisticBinding? = null

    private val binding get() = _binding!!

    constructor(source: StatisticElement) {
        _source = source
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var habit = _source!!.habit.toString()
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)

        _binding!!.statisticsStart.text = _source!!.start.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()
        _binding!!.statisticsLongest.text = _source!!.longestPeriod.toString()
        _binding!!.statisticsShortest.text = _source!!.shortestPeriod.toString()
        _binding!!.statisticsDays.text = _source!!.daysWithout.toString()
        _binding!!.statisticsHabit.text = habit

        val constraintLayout = _binding!!.statisticsFailures
        for ((failureId, failure) in _source!!.failures.withIndex()) {
            val textView = TextView(constraintLayout.context)
            textView.id = ("$habit failureId$failureId").hashCode()
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textView.text = failure.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()
            constraintLayout.addView(textView)

            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(
                textView.id,
                ConstraintSet.TOP, // put text view top side bottom of button
                PARENT_ID, // button to put text view bellow it
                ConstraintSet.TOP, // button bottom to put text view bellow it,
                failureId * 20
            )
            constraintSet.applyTo(constraintLayout)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
