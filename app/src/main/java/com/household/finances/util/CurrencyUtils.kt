package com.household.finances.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object CurrencyUtils {
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA)
    private val decimalFormat = DecimalFormat("#,##0.00")

    fun formatCurrency(amount: Double): String {
        return currencyFormat.format(amount)
    }

    fun formatDecimal(amount: Double): String {
        return decimalFormat.format(amount)
    }

    fun formatPercentage(value: Double): String {
        return String.format("%.2f%%", value * 100)
    }

    fun formatCompact(amount: Double): String {
        return when {
            amount >= 100_000_000 -> String.format("%.2f亿", amount / 100_000_000)
            amount >= 10_000 -> String.format("%.2f万", amount / 10_000)
            else -> formatCurrency(amount)
        }
    }

    fun parseCurrency(text: String): Double? {
        return try {
            text.replace(",", "")
                .replace("¥", "")
                .replace("￥", "")
                .trim()
                .toDoubleOrNull()
        } catch (e: Exception) {
            null
        }
    }
}
