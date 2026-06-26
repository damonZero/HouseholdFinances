package com.household.finances.data.repository

import com.household.finances.data.local.dao.LiabilityDao
import com.household.finances.data.model.Liability
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiabilityRepository @Inject constructor(
    private val liabilityDao: LiabilityDao
) {
    fun getAllLiabilities(): Flow<List<Liability>> = liabilityDao.getAllLiabilities()

    fun getLiabilitiesByType(type: String): Flow<List<Liability>> = liabilityDao.getLiabilitiesByType(type)

    suspend fun getLiabilityById(id: Long): Liability? = liabilityDao.getLiabilityById(id)

    fun getTotalLiabilities(): Flow<Double?> = liabilityDao.getTotalLiabilities()

    fun getTotalLiabilitiesByType(type: String): Flow<Double?> = liabilityDao.getTotalLiabilitiesByType(type)

    suspend fun insertLiability(liability: Liability): Long = liabilityDao.insertLiability(liability)

    suspend fun updateLiability(liability: Liability) = liabilityDao.updateLiability(liability)

    suspend fun deleteLiability(liability: Liability) = liabilityDao.deleteLiability(liability)

    suspend fun deleteLiabilityById(id: Long) = liabilityDao.deleteLiabilityById(id)
}
