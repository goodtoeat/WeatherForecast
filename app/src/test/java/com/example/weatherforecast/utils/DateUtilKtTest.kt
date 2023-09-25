package com.example.weatherforecast.utils

import com.google.common.truth.Truth
import org.junit.Test

class DateUtilKtTest {

    @Test
    fun `input timestamp return 12h hour`() {
        val result1 = get12Hour(1695470400)
        Truth.assertThat(result1).isEqualTo("下午08時")
    }

    @Test
    fun `input timestamp return 24h hour`() {
        val result1 = getHour(1695470400)
        Truth.assertThat(result1).isEqualTo(20)
    }

    @Test
    fun `input timestamp return date`() {
        val result1 = getDate(1695470400)
        Truth.assertThat(result1).isEqualTo("09 / 23")
    }

    @Test
    fun `input timestamp return week`() {
        val result1 = getWeek(1695470400)
        Truth.assertThat(result1).isEqualTo("星期六")
    }
}