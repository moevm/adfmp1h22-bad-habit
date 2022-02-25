package com.example.badhabits

import java.util.*

class StatisticElement {
    var habit: String = ""
    var start: Date = Date()
    var daysWithout: Int = 0
    var shortestPeriod: Int = 0
    var longestPeriod: Int = 0
    var failures: List<Date> = ArrayList()

    constructor(habit: String, start: Date, daysWithout: Int, shortestPeriod: Int, longestPeriod: Int, failures: List<Date>) {
        this.habit = habit
        this.start = start
        this.daysWithout = daysWithout
        this.shortestPeriod = shortestPeriod
        this.longestPeriod = longestPeriod
        this.failures = failures
    }
}
