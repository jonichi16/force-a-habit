package com.jonichi.common.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalTime

class TimeFormatterTest {
    @Test
    fun `TimeFormatter should return correct time`() {
        val time = LocalTime.of(6, 0)
        val formattedTime = timeFormatter(time)

        assertEquals("06:00 AM", formattedTime)
    }

    @Test
    fun `TimeFormatter should return correct time when PM`() {
        val time = LocalTime.of(14, 0)
        val formattedTime = timeFormatter(time)

        assertEquals("02:00 PM", formattedTime)
    }
}
