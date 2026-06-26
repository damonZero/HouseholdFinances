package com.household.finances.data.repository

import com.household.finances.data.local.dao.CashFlowDao
import com.household.finances.data.model.CashFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CashFlowRepository @Inject constructor(
    private val cashFlowDao: CashFlowDao
) {
    fun getAllCashFlows(): Flow<List<CashFlow>> = cashFlowDao.getAllCashFlows()

    fun getCashFlowsByType(type: String): Flow<List<CashFlow>> = cashFlowDao.getCashFlowsByType(type)

    fun getCashFlowsByDateRange(startDate: Long, endDate: Long): Flow<List<CashFlow>> =
        cashFlowDao.getCashFlowsByDateRange(startDate, endDate)

    suspend fun getCashFlowById(id: Long): CashFlow? = cashFlowDao.getCashFlowById(id)

    fun getTotalIncome(startDate: Long, endDate: Long): Flow<Double?> =
        cashFlowDao.getTotalIncome(startDate, endDate)

    fun getTotalExpense(startDate: Long, endDate: Long): Flow<Double?> =
        cashFlowDao.getTotalExpense(startDate, endDate)

    suspend fun insertCashFlow(cashFlow: CashFlow): Long = cashFlowDao.insertCashFlow(cashFlow)

    suspend fun updateCashFlow(cashFlow: CashFlow) = cashFlowDao.updateCashFlow(cashFlow)

    suspend fun deleteCashFlow(cashFlow: CashFlow) = cashFlowDao.deleteCashFlow(cashFlow)

    suspend fun deleteCashFlowById(id: Long) = cashFlowDao.deleteCashFlowById(id)
}
