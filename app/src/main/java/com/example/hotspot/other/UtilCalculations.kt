package com.example.hotspot.other

import java.time.LocalDate
import java.time.Period

object UtilCalculations {

    fun getAge(year: Int, month: Int, dayOfMonth: Int): Int {
        return Period.between(
            LocalDate.of(year, month, dayOfMonth),
            LocalDate.now()
        ).years
    }
}