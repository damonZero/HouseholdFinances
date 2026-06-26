package com.household.finances.util

import org.junit.Assert.*
import org.junit.Test

class CurrencyUtilsTest {

    @Test
    fun `formatCurrency formats positive amount correctly`() {
        val result = CurrencyUtils.formatCurrency(12345.67)
        assertNotNull(result)
        assertTrue(result.contains("12,345.67"))
    }

    @Test
    fun `formatCurrency formats zero correctly`() {
        val result = CurrencyUtils.formatCurrency(0.0)
        assertNotNull(result)
        assertTrue(result.contains("0"))
    }

    @Test
    fun `formatCurrency formats negative amount correctly`() {
        val result = CurrencyUtils.formatCurrency(-12345.67)
        assertNotNull(result)
        assertTrue(result.contains("12,345.67"))
    }

    @Test
    fun `formatDecimal formats correctly`() {
        val result = CurrencyUtils.formatDecimal(12345.67)
        assertEquals("12,345.67", result)
    }

    @Test
    fun `formatDecimal handles zero`() {
        val result = CurrencyUtils.formatDecimal(0.0)
        assertEquals("0.00", result)
    }

    @Test
    fun `formatPercentage formats correctly`() {
        val result = CurrencyUtils.formatPercentage(0.1234)
        assertEquals("12.34%", result)
    }

    @Test
    fun `formatPercentage handles zero`() {
        val result = CurrencyUtils.formatPercentage(0.0)
        assertEquals("0.00%", result)
    }

    @Test
    fun `formatPercentage handles one`() {
        val result = CurrencyUtils.formatPercentage(1.0)
        assertEquals("100.00%", result)
    }

    @Test
    fun `formatCompact formats billions correctly`() {
        val result = CurrencyUtils.formatCompact(150000000.0)
        assertTrue(result.contains("亿"))
    }

    @Test
    fun `formatCompact formats ten thousands correctly`() {
        val result = CurrencyUtils.formatCompact(50000.0)
        assertTrue(result.contains("万"))
    }

    @Test
    fun `formatCompact formats small amounts with currency`() {
        val result = CurrencyUtils.formatCompact(5000.0)
        assertNotNull(result)
    }

    @Test
    fun `parseCurrency parses valid amount`() {
        val result = CurrencyUtils.parseCurrency("12,345.67")
        assertEquals(12345.67, result!!, 0.001)
    }

    @Test
    fun `parseCurrency parses amount with currency symbol`() {
        val result = CurrencyUtils.parseCurrency("¥12,345.67")
        assertEquals(12345.67, result!!, 0.001)
    }

    @Test
    fun `parseCurrency returns null for invalid input`() {
        val result = CurrencyUtils.parseCurrency("invalid")
        assertNull(result)
    }

    @Test
    fun `parseCurrency handles empty string`() {
        val result = CurrencyUtils.parseCurrency("")
        assertNull(result)
    }
}
