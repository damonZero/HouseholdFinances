package com.household.finances.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.household.finances.data.local.dao.*
import com.household.finances.data.model.*

@Database(
    entities = [
        Asset::class,
        Liability::class,
        CashFlow::class,
        Investment::class,
        Insurance::class,
        FinancialGoal::class,
        AssetSnapshot::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FinancesDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun liabilityDao(): LiabilityDao
    abstract fun cashFlowDao(): CashFlowDao
    abstract fun investmentDao(): InvestmentDao
    abstract fun insuranceDao(): InsuranceDao
    abstract fun financialGoalDao(): FinancialGoalDao
    abstract fun assetSnapshotDao(): AssetSnapshotDao
}
