package com.example.badhabits

import junit.framework.TestCase
import java.time.LocalDate
import java.time.Period

class HabitTest : TestCase() {

    fun testGetPeriod() {
        val today = LocalDate.now()
        val output = Habit.getPeriod(Period.between(today.minusDays(3), today))
        assertEquals(output, 3)
    }
}