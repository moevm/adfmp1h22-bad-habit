package com.example.badhabits
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.badhabits.databinding.FragmentStatisticBinding

class StatisticFragment : Fragment {
    private var _source: StatisticElement? = null
    private var _binding: FragmentStatisticBinding? = null

    private val binding get() = _binding!!

    constructor(source: StatisticElement) {
        _source = source
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        _binding!!.statisticsFailures.text = _source!!.failures[0].toLocaleString()
        _binding!!.statisticsStart.text = _source!!.start.toLocaleString()
        _binding!!.statisticsLongest.text = _source!!.longestPeriod.toString()
        _binding!!.statisticsShortest.text = _source!!.shortestPeriod.toString()
        _binding!!.statisticsDays.text = _source!!.daysWithout.toString()
        _binding!!.statisticsHabit.text = _source!!.habit.toString()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
