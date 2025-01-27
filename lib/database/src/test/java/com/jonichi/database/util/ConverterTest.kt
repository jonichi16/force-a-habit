package com.jonichi.database.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalTime

class ConverterTest {

    private val converter = Converter()

    @Test
    fun `converter should convert time to string`() {
        // given
        val time = LocalTime.of(7, 50)
        val expected = "07:50"

        // when
        val actual = converter.fromLocalTime(time)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `converter should convert string to time`() {
        // given
        val timeString = "08:30"
        val expected = LocalTime.of(8, 30)

        // when
        val actual = converter.toLocalTime(timeString)

        // then
        assertEquals(expected, actual)
    }
}