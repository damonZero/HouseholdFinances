package com.household.finances.data.local.dao

import androidx.room.*
import com.household.finances.data.model.FinancialGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface FinancialGoalDao {
    @Query("SELECT * FROM financial_goals ORDER BY updatedAt DESC")
    fun getAllGoals(): Flow<List<FinancialGoal>>

    @Query("SELECT * FROM financial_goals WHERE status = :status ORDER BY updatedAt DESC")
    fun getGoalsByStatus(status: String): Flow<List<FinancialGoal>>

    @Query("SELECT * FROM financial_goals WHERE id = :id")
    suspend fun getGoalById(id: Long): FinancialGoal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: FinancialGoal): Long

    @Update
    suspend fun updateGoal(goal: FinancialGoal)

    @Delete
    suspend fun deleteGoal(goal: FinancialGoal)

    @Query("DELETE FROM financial_goals WHERE id = :id")
    suspend fun deleteGoalById(id: Long)
}
