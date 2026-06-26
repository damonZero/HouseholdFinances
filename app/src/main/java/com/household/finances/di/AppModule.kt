package com.household.finances.di

import android.content.Context
import androidx.room.Room
import com.household.finances.data.local.FinancesDatabase
import com.household.finances.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFinancesDatabase(@ApplicationContext context: Context): FinancesDatabase {
        return Room.databaseBuilder(
            context,
            FinancesDatabase::class.java,
            "finances_database"
        ).build()
    }

    @Provides
    fun provideAssetDao(database: FinancesDatabase): AssetDao = database.assetDao()

    @Provides
    fun provideLiabilityDao(database: FinancesDatabase): LiabilityDao = database.liabilityDao()

    @Provides
    fun provideCashFlowDao(database: FinancesDatabase): CashFlowDao = database.cashFlowDao()

    @Provides
    fun provideInvestmentDao(database: FinancesDatabase): InvestmentDao = database.investmentDao()

    @Provides
    fun provideInsuranceDao(database: FinancesDatabase): InsuranceDao = database.insuranceDao()

    @Provides
    fun provideFinancialGoalDao(database: FinancesDatabase): FinancialGoalDao = database.financialGoalDao()

    @Provides
    fun provideAssetSnapshotDao(database: FinancesDatabase): AssetSnapshotDao = database.assetSnapshotDao()
}
