package com.example.hotspot.other.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object AgeAuthentication {

    fun getYear(dateOfBirth: Int): Int {
        val dobString: String = dateOfBirth.toString()
        return Integer.parseInt(dobString.substring(0, 4))
    }
    fun getMonth(dateOfBirth: Int): Int {
        val dobString: String = dateOfBirth.toString()
        return Integer.parseInt(dobString.substring(4, 6))
    }
    fun getDay(dateOfBirth: Int): Int {
        val dobString: String = dateOfBirth.toString()
        return Integer.parseInt(dobString.substring(6, 8))
    }

    fun verifyOver18(year: Int, month: Int, day: Int): Boolean {
        val dateOfBirth = LocalDate.of(year, month, day)
        val dateNow: LocalDate = LocalDate.now()
        val years = ChronoUnit.YEARS.between(dateOfBirth, dateNow)
        var isOver18 = years > 18
        print("Date of Birth = $dateOfBirth")
        print("Date Now = $dateNow")
        print("Exact time between dates = $years")
        print("Is over 18? -> $isOver18")
        return isOver18
    }

    fun roundedAge(year: Int, month: Int, day: Int): Int {
        val dateOfBirth = LocalDate.of(year, month, day)
        val dateNow: LocalDate = LocalDate.now()
        var years = ChronoUnit.YEARS.between(dateOfBirth, dateNow)
        years = Math.floor(years.toDouble()).toLong()
        return years.toInt()
    }
}