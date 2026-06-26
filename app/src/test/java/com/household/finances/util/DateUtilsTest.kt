package com.household.finances.util

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DateUtilsTest {

    @Test
    fun `formatDate returns correct format`() {
        // 2024-01-15 timestamp
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 15, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val result = DateUtils.formatDate(calendar.timeInMillis)
        assertEquals("2024-01-15", result)
    }

    @Test
    fun `formatDateTime returns correct format`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 15, 14, 30, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val result = DateUtils.formatDateTime(calendar.timeInMillis)
        assertTrue(result.contains("2024-01-15"))
        assertTrue(result.contains("14:30:00"))
    }

    @Test
    fun `formatMonth returns correct format`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val result = DateUtils.formatMonth(calendar.timeInMillis)
        assertEquals("2024年01月", result)
    }

    @Test
    fun `parseDate parses valid date`() {
        val result = DateUtils.parseDate("2024-01-15")
        assertNotNull(result)
        val calendar = Calendar.getInstance().apply {
            timeInMillis = result!!
        }
        assertEquals(2024, calendar.get(Calendar.YEAR))
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH))
        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `parseDate returns null for invalid date`() {
        val result = DateUtils.parseDate("invalid-date")
        assertNull(result)
    }

    @Test
    fun `getStartOfDay returns midnight`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 15, 14, 30, 45)
            set(Calendar.MILLISECOND, 500)
        }
        val startOfDay = DateUtils.getStartOfDay(calendar.timeInMillis)
        val result = Calendar.getInstance().apply {
            timeInMillis = startOfDay
        }
        assertEquals(0, result.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, result.get(Calendar.MINUTE))
        assertEquals(0, result.get(Calendar.SECOND))
        assertEquals(0, result.get(Calendar.MILLISECOND))
    }

    @Test
    fun `getEndOfDay returns end of day`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 15, 14, 30, 45)
            set(Calendar.MILLISECOND, 500)
        }
        val endOfDay = DateUtils.getEndOfDay(calendar.timeInMillis)
        val result = Calendar.getInstance().apply {
            timeInMillis = endOfDay
        }
        assertEquals(23, result.get(Calendar.HOUR_OF_DAY))
        assertEquals(59, result.get(Calendar.MINUTE))
        assertEquals(59, result.get(Calendar.SECOND))
    }

    @Test
    fun `getStartOfMonth returns first day of month`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 15, 14, 30, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfMonth = DateUtils.getStartOfMonth(calendar.timeInMillis)
        val result = Calendar.getInstance().apply {
            timeInMillis = startOfMonth
        }
        assertEquals(1, result.get(Calendar.DAY_OF_MONTH))
        assertEquals(0, result.get(Calendar.HOUR_OF_DAY))
    }

    @Test
    fun `getEndOfMonth returns last day of month`() {
        val calendar = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 15, 14, 30, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endOfMonth = DateUtils.getEndOfMonth(calendar.timeInMillis)
        val result = Calendar.getInstance().apply {
            timeInMillis = endOfMonth
        }
        assertEquals(31, result.get(Calendar.DAY_OF_MONTH))
        assertEquals(23, result.get(Calendar.HOUR_OF_DAY))
    }

    @Test
    fun `getDaysBetween calculates correctly`() {
        val start = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 1, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val end = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 31, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val days = DateUtils.getDaysBetween(start, end)
        assertEquals(30, days)
    }

    @Test
    fun `isToday returns true for today`() {
        val now = System.currentTimeMillis()
        assertTrue(DateUtils.isToday(now))
    }

    @Test
    fun `isToday returns false for past date`() {
        val past = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis
        assertFalse(DateUtils.isToday(past))
    }
}
