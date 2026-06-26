package com.household.finances.data.model

import org.junit.Assert.*
import org.junit.Test

class InsuranceTest {

    @Test
    fun `Insurance creation with all fields`() {
        val insurance = Insurance(
            id = 1,
            name = "重疾险",
            company = "中国人寿",
            type = InsuranceType.HEALTH,
            coverageAmount = 500000.0,
            annualPremium = 5000.0,
            paymentYears = 20,
            startDate = System.currentTimeMillis(),
            insuredPerson = "张三",
            description = "重大疾病保险",
            category = "健康险",
            tags = listOf("重疾", "健康")
        )

        assertEquals(1L, insurance.id)
        assertEquals("重疾险", insurance.name)
        assertEquals("中国人寿", insurance.company)
        assertEquals(InsuranceType.HEALTH, insurance.type)
        assertEquals(500000.0, insurance.coverageAmount, 0.001)
        assertEquals(5000.0, insurance.annualPremium, 0.001)
        assertEquals(20, insurance.paymentYears)
        assertEquals("张三", insurance.insuredPerson)
    }

    @Test
    fun `Insurance creation with default values`() {
        val insurance = Insurance(
            name = "意外险",
            company = "平安保险",
            type = InsuranceType.ACCIDENT,
            coverageAmount = 100000.0,
            annualPremium = 200.0
        )

        assertEquals(0L, insurance.id)
        assertEquals("意外险", insurance.name)
        assertEquals("平安保险", insurance.company)
        assertEquals(InsuranceType.ACCIDENT, insurance.type)
        assertEquals(100000.0, insurance.coverageAmount, 0.001)
        assertEquals(200.0, insurance.annualPremium, 0.001)
        assertEquals(0, insurance.paymentYears)
        assertEquals("", insurance.insuredPerson)
    }

    @Test
    fun `InsuranceType enum values`() {
        assertEquals(5, InsuranceType.entries.size)
        assertNotNull(InsuranceType.LIFE)
        assertNotNull(InsuranceType.HEALTH)
        assertNotNull(InsuranceType.ACCIDENT)
        assertNotNull(InsuranceType.INVESTMENT)
        assertNotNull(InsuranceType.OTHER)
    }

    @Test
    fun `Insurance premium to coverage ratio`() {
        val insurance = Insurance(
            name = "测试",
            company = "测试公司",
            type = InsuranceType.LIFE,
            coverageAmount = 1000000.0,
            annualPremium = 10000.0,
            paymentYears = 20
        )

        val totalPremium = insurance.annualPremium * insurance.paymentYears
        val ratio = insurance.coverageAmount / totalPremium

        assertEquals(200000.0, totalPremium, 0.001)
        assertEquals(5.0, ratio, 0.001)
    }
}
