package com.household.finances.data.model

import org.junit.Assert.*
import org.junit.Test

class LiabilityTest {

    @Test
    fun `Liability creation with all fields`() {
        val liability = Liability(
            id = 1,
            name = "房贷",
            type = LiabilityType.MORTGAGE,
            amount = 1000000.0,
            interestRate = 4.5,
            monthlyPayment = 5066.85,
            remainingMonths = 360,
            startDate = System.currentTimeMillis(),
            description = "测试房贷",
            category = "房贷",
            tags = listOf("房贷", "长期")
        )

        assertEquals(1L, liability.id)
        assertEquals("房贷", liability.name)
        assertEquals(LiabilityType.MORTGAGE, liability.type)
        assertEquals(1000000.0, liability.amount, 0.001)
        assertEquals(4.5, liability.interestRate, 0.001)
        assertEquals(5066.85, liability.monthlyPayment, 0.001)
        assertEquals(360, liability.remainingMonths)
        assertEquals("测试房贷", liability.description)
    }

    @Test
    fun `Liability creation with default values`() {
        val liability = Liability(
            name = "信用卡",
            type = LiabilityType.CREDIT_CARD,
            amount = 10000.0
        )

        assertEquals(0L, liability.id)
        assertEquals("信用卡", liability.name)
        assertEquals(LiabilityType.CREDIT_CARD, liability.type)
        assertEquals(10000.0, liability.amount, 0.001)
        assertEquals(0.0, liability.interestRate, 0.001)
        assertEquals(0.0, liability.monthlyPayment, 0.001)
        assertEquals(0, liability.remainingMonths)
    }

    @Test
    fun `LiabilityType enum values`() {
        assertEquals(5, LiabilityType.entries.size)
        assertNotNull(LiabilityType.MORTGAGE)
        assertNotNull(LiabilityType.CAR_LOAN)
        assertNotNull(LiabilityType.CREDIT_CARD)
        assertNotNull(LiabilityType.CONSUMER_LOAN)
        assertNotNull(LiabilityType.OTHER)
    }

    @Test
    fun `Liability copy with modified amount`() {
        val original = Liability(
            id = 1,
            name = "测试",
            type = LiabilityType.CREDIT_CARD,
            amount = 10000.0
        )
        val modified = original.copy(amount = 5000.0)

        assertEquals(1L, modified.id)
        assertEquals("测试", modified.name)
        assertEquals(5000.0, modified.amount, 0.001)
    }
}
