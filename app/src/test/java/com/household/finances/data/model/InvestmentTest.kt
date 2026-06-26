package com.household.finances.data.model

import org.junit.Assert.*
import org.junit.Test

class InvestmentTest {

    @Test
    fun `Investment creation with all fields`() {
        val investment = Investment(
            id = 1,
            name = "沪深300ETF",
            type = InvestmentType.ETF,
            symbol = "510300",
            shares = 10000.0,
            purchasePrice = 4.5,
            currentPrice = 4.8,
            purchaseDate = System.currentTimeMillis(),
            description = "沪深300ETF",
            category = "宽基指数",
            tags = listOf("ETF", "A股")
        )

        assertEquals(1L, investment.id)
        assertEquals("沪深300ETF", investment.name)
        assertEquals(InvestmentType.ETF, investment.type)
        assertEquals("510300", investment.symbol)
        assertEquals(10000.0, investment.shares, 0.001)
        assertEquals(4.5, investment.purchasePrice, 0.001)
        assertEquals(4.8, investment.currentPrice, 0.001)
    }

    @Test
    fun `Investment creation with default values`() {
        val investment = Investment(
            name = "测试基金",
            type = InvestmentType.FUND,
            symbol = "000001",
            shares = 1000.0,
            purchasePrice = 1.0,
            currentPrice = 1.1
        )

        assertEquals(0L, investment.id)
        assertEquals("测试基金", investment.name)
        assertEquals(InvestmentType.FUND, investment.type)
        assertEquals("000001", investment.symbol)
        assertEquals(1000.0, investment.shares, 0.001)
        assertEquals(1.0, investment.purchasePrice, 0.001)
        assertEquals(1.1, investment.currentPrice, 0.001)
    }

    @Test
    fun `InvestmentType enum values`() {
        assertEquals(5, InvestmentType.entries.size)
        assertNotNull(InvestmentType.ETF)
        assertNotNull(InvestmentType.FUND)
        assertNotNull(InvestmentType.STOCK)
        assertNotNull(InvestmentType.BOND)
        assertNotNull(InvestmentType.OTHER)
    }

    @Test
    fun `Investment profit calculation`() {
        val investment = Investment(
            name = "测试",
            type = InvestmentType.ETF,
            shares = 10000.0,
            purchasePrice = 4.5,
            currentPrice = 4.8
        )

        val totalValue = investment.currentPrice * investment.shares
        val profit = (investment.currentPrice - investment.purchasePrice) * investment.shares
        val profitRate = (investment.currentPrice - investment.purchasePrice) / investment.purchasePrice

        assertEquals(48000.0, totalValue, 0.001)
        assertEquals(3000.0, profit, 0.001)
        assertTrue(profitRate > 0)
    }

    @Test
    fun `Investment loss calculation`() {
        val investment = Investment(
            name = "测试",
            type = InvestmentType.STOCK,
            shares = 1000.0,
            purchasePrice = 100.0,
            currentPrice = 80.0
        )

        val profit = (investment.currentPrice - investment.purchasePrice) * investment.shares
        assertTrue(profit < 0)
        assertEquals(-20000.0, profit, 0.001)
    }
}
