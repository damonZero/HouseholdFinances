package com.household.finances.util

object FinancialCalculator {

    /**
     * 计算资产负债率
     * @return 资产负债率 (0-1)
     */
    fun calculateAssetLiabilityRatio(totalAssets: Double, totalLiabilities: Double): Double {
        return if (totalAssets > 0) totalLiabilities / totalAssets else 0.0
    }

    /**
     * 计算净资产
     */
    fun calculateNetWorth(totalAssets: Double, totalLiabilities: Double): Double {
        return totalAssets - totalLiabilities
    }

    /**
     * 计算储蓄率
     * @return 储蓄率 (0-1)
     */
    fun calculateSavingsRate(income: Double, expense: Double): Double {
        return if (income > 0) (income - expense) / income else 0.0
    }

    /**
     * 计算投资回报率
     * @return 投资回报率 (0-1)
     */
    fun calculateROI(currentValue: Double, initialValue: Double): Double {
        return if (initialValue > 0) (currentValue - initialValue) / initialValue else 0.0
    }

    /**
     * 计算月供（等额本息）
     * @param principal 贷款本金
     * @param annualRate 年利率
     * @param months 贷款月数
     * @return 月供金额
     */
    fun calculateMonthlyPayment(principal: Double, annualRate: Double, months: Int): Double {
        if (months <= 0) return 0.0
        val monthlyRate = annualRate / 12 / 100
        return if (monthlyRate == 0.0) {
            principal / months
        } else {
            principal * monthlyRate * Math.pow(1 + monthlyRate, months.toDouble()) /
            (Math.pow(1 + monthlyRate, months.toDouble()) - 1)
        }
    }

    /**
     * 计算总利息
     */
    fun calculateTotalInterest(principal: Double, monthlyPayment: Double, months: Int): Double {
        return monthlyPayment * months - principal
    }

    /**
     * 计算复利终值
     * @param principal 本金
     * @param rate 年利率
     * @param years 年数
     * @return 终值
     */
    fun calculateCompoundInterest(principal: Double, rate: Double, years: Int): Double {
        return principal * Math.pow(1 + rate / 100, years.toDouble())
    }

    /**
     * 计算财务健康评分
     * @return 评分 (0-100)
     */
    fun calculateFinancialHealthScore(
        assetLiabilityRatio: Double,
        savingsRate: Double,
        insuranceCoverage: Double,
        investmentDiversity: Double
    ): Int {
        val score1 = when {
            assetLiabilityRatio < 0.3 -> 20
            assetLiabilityRatio < 0.5 -> 15
            assetLiabilityRatio < 0.7 -> 10
            else -> 5
        }

        val score2 = when {
            savingsRate > 0.4 -> 20
            savingsRate > 0.3 -> 15
            savingsRate > 0.2 -> 10
            else -> 5
        }

        val score3 = when {
            insuranceCoverage > 0.8 -> 20
            insuranceCoverage > 0.6 -> 15
            insuranceCoverage > 0.4 -> 10
            else -> 5
        }

        val score4 = when {
            investmentDiversity > 0.8 -> 20
            investmentDiversity > 0.6 -> 15
            investmentDiversity > 0.4 -> 10
            else -> 5
        }

        return score1 + score2 + score3 + score4
    }
}
