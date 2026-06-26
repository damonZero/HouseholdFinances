package com.household.finances.data.repository

import com.household.finances.data.local.dao.InvestmentDao
import com.household.finances.data.model.Investment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvestmentRepository @Inject constructor(
    private val investmentDao: InvestmentDao
) {
    fun getAllInvestments(): Flow<List<Investment>> = investmentDao.getAllInvestments()

    fun getInvestmentsByType(type: String): Flow<List<Investment>> = investmentDao.getInvestmentsByType(type)

    suspend fun getInvestmentById(id: Long): Investment? = investmentDao.getInvestmentById(id)

    fun getTotalInvestmentValue(): Flow<Double?> = investmentDao.getTotalInvestmentValue()

    suspend fun insertInvestment(investment: Investment): Long = investmentDao.insertInvestment(investment)

    suspend fun updateInvestment(investment: Investment) = investmentDao.updateInvestment(investment)

    suspend fun deleteInvestment(investment: Investment) = investmentDao.deleteInvestment(investment)

    suspend fun deleteInvestmentById(id: Long) = investmentDao.deleteInvestmentById(id)
}
