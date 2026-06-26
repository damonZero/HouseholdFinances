package com.household.finances.data.local.dao

import androidx.room.*
import com.household.finances.data.model.Insurance
import kotlinx.coroutines.flow.Flow

@Dao
interface InsuranceDao {
    @Query("SELECT * FROM insurances ORDER BY updatedAt DESC")
    fun getAllInsurances(): Flow<List<Insurance>>

    @Query("SELECT * FROM insurances WHERE type = :type ORDER BY updatedAt DESC")
    fun getInsurancesByType(type: String): Flow<List<Insurance>>

    @Query("SELECT * FROM insurances WHERE id = :id")
    suspend fun getInsuranceById(id: Long): Insurance?

    @Query("SELECT SUM(annualPremium) FROM insurances")
    fun getTotalAnnualPremium(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsurance(insurance: Insurance): Long

    @Update
    suspend fun updateInsurance(insurance: Insurance)

    @Delete
    suspend fun deleteInsurance(insurance: Insurance)

    @Query("DELETE FROM insurances WHERE id = :id")
    suspend fun deleteInsuranceById(id: Long)
}
