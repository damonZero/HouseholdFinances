package com.household.finances.data.repository

import com.household.finances.data.local.dao.FinancialGoalDao
import com.household.finances.data.model.FinancialGoal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinancialGoalRepository @Inject constructor(
    private val goalDao: FinancialGoalDao
) {
    fun getAllGoals(): Flow<List<FinancialGoal>> = goalDao.getAllGoals()

    fun getGoalsByStatus(status: String): Flow<List<FinancialGoal>> = goalDao.getGoalsByStatus(status)

    suspend fun getGoalById(id: Long): FinancialGoal? = goalDao.getGoalById(id)

    suspend fun insertGoal(goal: FinancialGoal): Long = goalDao.insertGoal(goal)

    suspend fun updateGoal(goal: FinancialGoal) = goalDao.updateGoal(goal)

    suspend fun deleteGoal(goal: FinancialGoal) = goalDao.deleteGoal(goal)

    suspend fun deleteGoalById(id: Long) = goalDao.deleteGoalById(id)
}
