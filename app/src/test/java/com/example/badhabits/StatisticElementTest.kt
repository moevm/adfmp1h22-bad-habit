package com.example.badhabits

import junit.framework.TestCase
import java.time.LocalDate
import java.time.Period

class StatisticElementTest : TestCase() {

    fun testCountDays() {
        val elem = StatisticElement()
        val today = LocalDate.now()
        val output = elem.countDays(Period.between(today.minusDays(3), today))
		
        assertEquals(output, 3)
    }
}