package com.household.finances.data.repository

import com.household.finances.data.local.dao.AssetDao
import com.household.finances.data.model.Asset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetRepository @Inject constructor(
    private val assetDao: AssetDao
) {
    fun getAllAssets(): Flow<List<Asset>> = assetDao.getAllAssets()

    fun getAssetsByType(type: String): Flow<List<Asset>> = assetDao.getAssetsByType(type)

    suspend fun getAssetById(id: Long): Asset? = assetDao.getAssetById(id)

    fun getTotalAssets(): Flow<Double?> = assetDao.getTotalAssets()

    fun getTotalAssetsByType(type: String): Flow<Double?> = assetDao.getTotalAssetsByType(type)

    suspend fun insertAsset(asset: Asset): Long = assetDao.insertAsset(asset)

    suspend fun updateAsset(asset: Asset) = assetDao.updateAsset(asset)

    suspend fun deleteAsset(asset: Asset) = assetDao.deleteAsset(asset)

    suspend fun deleteAssetById(id: Long) = assetDao.deleteAssetById(id)
}
