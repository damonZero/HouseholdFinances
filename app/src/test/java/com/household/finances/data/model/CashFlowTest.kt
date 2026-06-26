package com.household.finances.data.model

import org.junit.Assert.*
import org.junit.Test

class CashFlowTest {

    @Test
    fun `CashFlow creation with all fields`() {
        val cashFlow = CashFlow(
            id = 1,
            date = System.currentTimeMillis(),
            type = CashFlowType.INCOME,
            amount = 10000.0,
            category = "工资",
            description = "月工资",
            account = "招商银行",
            tags = listOf("工资", "收入")
        )

        assertEquals(1L, cashFlow.id)
        assertEquals(CashFlowType.INCOME, cashFlow.type)
        assertEquals(10000.0, cashFlow.amount, 0.001)
        assertEquals("工资", cashFlow.category)
        assertEquals("月工资", cashFlow.description)
        assertEquals("招商银行", cashFlow.account)
        assertEquals(2, cashFlow.tags.size)
    }

    @Test
    fun `CashFlow creation with default values`() {
        val cashFlow = CashFlow(
            type = CashFlowType.EXPENSE,
            amount = 500.0,
            category = "餐饮"
        )

        assertEquals(0L, cashFlow.id)
        assertEquals(CashFlowType.EXPENSE, cashFlow.type)
        assertEquals(500.0, cashFlow.amount, 0.001)
        assertEquals("餐饮", cashFlow.category)
        assertEquals("", cashFlow.description)
        assertEquals("", cashFlow.account)
        assertTrue(cashFlow.tags.isEmpty())
    }

    @Test
    fun `CashFlowType enum values`() {
        assertEquals(2, CashFlowType.entries.size)
        assertNotNull(CashFlowType.INCOME)
        assertNotNull(CashFlowType.EXPENSE)
    }

    @Test
    fun `CashFlow income type`() {
        val cashFlow = CashFlow(
            type = CashFlowType.INCOME,
            amount = 10000.0,
            category = "工资"
        )
        assertEquals(CashFlowType.INCOME, cashFlow.type)
    }

    @Test
    fun `CashFlow expense type`() {
        val cashFlow = CashFlow(
            type = CashFlowType.EXPENSE,
            amount = 500.0,
            category = "餐饮"
        )
        assertEquals(CashFlowType.EXPENSE, cashFlow.type)
    }
}
