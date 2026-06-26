package com.household.finances.util

import org.junit.Assert.*
import org.junit.Test

class FinancialCalculatorTest {

    @Test
    fun `calculateAssetLiabilityRatio returns correct ratio`() {
        val ratio = FinancialCalculator.calculateAssetLiabilityRatio(
            totalAssets = 1000000.0,
            totalLiabilities = 300000.0
        )
        assertEquals(0.3, ratio, 0.001)
    }

    @Test
    fun `calculateAssetLiabilityRatio returns zero when assets are zero`() {
        val ratio = FinancialCalculator.calculateAssetLiabilityRatio(
            totalAssets = 0.0,
            totalLiabilities = 300000.0
        )
        assertEquals(0.0, ratio, 0.001)
    }

    @Test
    fun `calculateNetWorth returns correct value`() {
        val netWorth = FinancialCalculator.calculateNetWorth(
            totalAssets = 1000000.0,
            totalLiabilities = 300000.0
        )
        assertEquals(700000.0, netWorth, 0.001)
    }

    @Test
    fun `calculateNetWorth handles negative net worth`() {
        val netWorth = FinancialCalculator.calculateNetWorth(
            totalAssets = 200000.0,
            totalLiabilities = 500000.0
        )
        assertEquals(-300000.0, netWorth, 0.001)
    }

    @Test
    fun `calculateSavingsRate returns correct rate`() {
        val rate = FinancialCalculator.calculateSavingsRate(
            income = 10000.0,
            expense = 7000.0
        )
        assertEquals(0.3, rate, 0.001)
    }

    @Test
    fun `calculateSavingsRate returns zero when income is zero`() {
        val rate = FinancialCalculator.calculateSavingsRate(
            income = 0.0,
            expense = 7000.0
        )
        assertEquals(0.0, rate, 0.001)
    }

    @Test
    fun `calculateSavingsRate handles negative savings`() {
        val rate = FinancialCalculator.calculateSavingsRate(
            income = 5000.0,
            expense = 7000.0
        )
        assertEquals(-0.4, rate, 0.001)
    }

    @Test
    fun `calculateROI returns correct value`() {
        val roi = FinancialCalculator.calculateROI(
            currentValue = 120000.0,
            initialValue = 100000.0
        )
        assertEquals(0.2, roi, 0.001)
    }

    @Test
    fun `calculateROI returns zero when initial value is zero`() {
        val roi = FinancialCalculator.calculateROI(
            currentValue = 120000.0,
            initialValue = 0.0
        )
        assertEquals(0.0, roi, 0.001)
    }

    @Test
    fun `calculateROI handles negative return`() {
        val roi = FinancialCalculator.calculateROI(
            currentValue = 80000.0,
            initialValue = 100000.0
        )
        assertEquals(-0.2, roi, 0.001)
    }

    @Test
    fun `calculateMonthlyPayment returns correct amount`() {
        // 100万贷款，年利率4.5%，30年（360个月）
        val payment = FinancialCalculator.calculateMonthlyPayment(
            principal = 1000000.0,
            annualRate = 4.5,
            months = 360
        )
        // 月供应该约为5066.85元
        assertTrue("Monthly payment should be positive", payment > 0)
        assertTrue("Monthly payment should be reasonable", payment in 4000.0..6000.0)
    }

    @Test
    fun `calculateMonthlyPayment returns zero when months is zero`() {
        val payment = FinancialCalculator.calculateMonthlyPayment(
            principal = 1000000.0,
            annualRate = 4.5,
            months = 0
        )
        assertEquals(0.0, payment, 0.001)
    }

    @Test
    fun `calculateMonthlyPayment handles zero interest rate`() {
        val payment = FinancialCalculator.calculateMonthlyPayment(
            principal = 120000.0,
            annualRate = 0.0,
            months = 120
        )
        assertEquals(1000.0, payment, 0.001)
    }

    @Test
    fun `calculateTotalInterest returns correct value`() {
        val interest = FinancialCalculator.calculateTotalInterest(
            principal = 1000000.0,
            monthlyPayment = 5066.85,
            months = 360
        )
        assertTrue("Total interest should be positive", interest > 0)
    }

    @Test
    fun `calculateCompoundInterest returns correct value`() {
        val result = FinancialCalculator.calculateCompoundInterest(
            principal = 100000.0,
            rate = 5.0,
            years = 10
        )
        // 100000 * (1.05)^10 ≈ 162889.46
        assertTrue("Compound interest should be greater than principal", result > 100000.0)
        assertTrue("Result should be reasonable", result in 160000.0..165000.0)
    }

    @Test
    fun `calculateFinancialHealthScore returns score between 0 and 100`() {
        val score = FinancialCalculator.calculateFinancialHealthScore(
            assetLiabilityRatio = 0.3,
            savingsRate = 0.35,
            insuranceCoverage = 0.7,
            investmentDiversity = 0.6
        )
        assertTrue("Score should be between 0 and 100", score in 0..100)
    }

    @Test
    fun `calculateFinancialHealthScore returns high score for healthy finances`() {
        val score = FinancialCalculator.calculateFinancialHealthScore(
            assetLiabilityRatio = 0.2,
            savingsRate = 0.45,
            insuranceCoverage = 0.9,
            investmentDiversity = 0.85
        )
        assertTrue("Healthy finances should score high", score >= 80)
    }

    @Test
    fun `calculateFinancialHealthScore returns low score for unhealthy finances`() {
        val score = FinancialCalculator.calculateFinancialHealthScore(
            assetLiabilityRatio = 0.8,
            savingsRate = 0.1,
            insuranceCoverage = 0.3,
            investmentDiversity = 0.3
        )
        assertTrue("Unhealthy finances should score low", score <= 40)
    }
}
