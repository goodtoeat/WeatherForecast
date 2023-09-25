package com.example.weatherforecast.utils

import com.google.common.truth.Truth
import org.junit.Test

class StringUtilKtTest{
    @Test
    fun `Input double value return Integer String`() {
        val result1 = removeFloat(100.111)
        Truth.assertThat(result1).isEqualTo("100")
        val result2 = removeFloat(100.toDouble())
        Truth.assertThat(result2).isEqualTo("100")
        val result3 = removeFloat(100.111)
        Truth.assertThat(result3).isNotEqualTo("100.111")
    }
}