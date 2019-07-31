package com.sudhirkhanger.apod

import com.sudhirkhanger.apod.utilities.Utilities
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class UtilitiesUnitTest {

    @Test
    fun testGetCurrentDate() {
        val actual = "2019-7-31"
        val cal = Calendar.getInstance()
        cal.set(2019, 6, 31)
        val expected = Utilities.getCurrentDate(cal)
        assertEquals(expected, actual)
    }

    @Test
    fun testConvertDateFormat() {
        val actual = "2019-07-31"
        val expected = Utilities.convertDateFormat(Date(1564531200000))
        assertEquals(expected, actual)
    }

    @Test
    fun testConvertStringToDate() {
        val actual = Date(1564511400000)
        val expected = Utilities.convertStringToDate("2019-07-31")
        assertEquals(expected, actual)
    }
}
