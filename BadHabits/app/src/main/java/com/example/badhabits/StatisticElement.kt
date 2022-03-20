package com.example.badhabits

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.util.*

class StatisticElement {
    var habit: String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    var start: LocalDate = LocalDate.now()
    var daysWithout: Int = 0
    var shortestPeriod: Int = Int.MAX_VALUE
    var longestPeriod: Int = 0
    var failures: List<LocalDate> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(habit: String, start: LocalDate, failures: List<LocalDate>) {
        this.habit = habit
        this.start = start
        this.failures = failures

        val today = LocalDate.now()
        var firstFailure = today
        var lastFailure = start
        if(failures.isNotEmpty()) {
            firstFailure = failures.first()
            lastFailure = failures.last()
        }

        this.daysWithout = countDays(Period.between(lastFailure, today))
        this.shortestPeriod = countDays(Period.between(start, firstFailure))
        this.longestPeriod = this.shortestPeriod
        for ((i, failure) in failures.withIndex()) {
            var period = this.daysWithout
            if(failure != failures.last()) {
                period = countDays(Period.between(failure, failures[i+1]))
            }
            if(period > this.longestPeriod) this.longestPeriod = period
            if(period < this.shortestPeriod) this.shortestPeriod = period
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun countDays(period: Period): Int {
        return period.years * 365 + period.months * 30 + period.days
    }
}
