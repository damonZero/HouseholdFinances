package com.household.finances.data.local.dao

import androidx.room.*
import com.household.finances.data.model.CashFlow
import kotlinx.coroutines.flow.Flow

@Dao
interface CashFlowDao {
    @Query("SELECT * FROM cash_flows ORDER BY date DESC")
    fun getAllCashFlows(): Flow<List<CashFlow>>

    @Query("SELECT * FROM cash_flows WHERE type = :type ORDER BY date DESC")
    fun getCashFlowsByType(type: String): Flow<List<CashFlow>>

    @Query("SELECT * FROM cash_flows WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getCashFlowsByDateRange(startDate: Long, endDate: Long): Flow<List<CashFlow>>

    @Query("SELECT * FROM cash_flows WHERE id = :id")
    suspend fun getCashFlowById(id: Long): CashFlow?

    @Query("SELECT SUM(amount) FROM cash_flows WHERE type = 'INCOME' AND date BETWEEN :startDate AND :endDate")
    fun getTotalIncome(startDate: Long, endDate: Long): Flow<Double?>

    @Query("SELECT SUM(amount) FROM cash_flows WHERE type = 'EXPENSE' AND date BETWEEN :startDate AND :endDate")
    fun getTotalExpense(startDate: Long, endDate: Long): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashFlow(cashFlow: CashFlow): Long

    @Update
    suspend fun updateCashFlow(cashFlow: CashFlow)

    @Delete
    suspend fun deleteCashFlow(cashFlow: CashFlow)

    @Query("DELETE FROM cash_flows WHERE id = :id")
    suspend fun deleteCashFlowById(id: Long)
}
