package com.household.finances.data.repository

import com.household.finances.data.local.dao.InsuranceDao
import com.household.finances.data.model.Insurance
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsuranceRepository @Inject constructor(
    private val insuranceDao: InsuranceDao
) {
    fun getAllInsurances(): Flow<List<Insurance>> = insuranceDao.getAllInsurances()

    fun getInsurancesByType(type: String): Flow<List<Insurance>> = insuranceDao.getInsurancesByType(type)

    suspend fun getInsuranceById(id: Long): Insurance? = insuranceDao.getInsuranceById(id)

    fun getTotalAnnualPremium(): Flow<Double?> = insuranceDao.getTotalAnnualPremium()

    suspend fun insertInsurance(insurance: Insurance): Long = insuranceDao.insertInsurance(insurance)

    suspend fun updateInsurance(insurance: Insurance) = insuranceDao.updateInsurance(insurance)

    suspend fun deleteInsurance(insurance: Insurance) = insuranceDao.deleteInsurance(insurance)

    suspend fun deleteInsuranceById(id: Long) = insuranceDao.deleteInsuranceById(id)
}
