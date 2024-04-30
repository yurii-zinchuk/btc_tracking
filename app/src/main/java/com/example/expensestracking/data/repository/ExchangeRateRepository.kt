package com.example.expensestracking.data.repository

import com.example.expensestracking.data.api.ExchangeRateService
import com.example.expensestracking.data.api.provideExchangeRateService
import com.example.expensestracking.data.datastore.DataStoreManager

class ExchangeRateRepository(
    private val dataStore: DataStoreManager = DataStoreManager,
    private val service: ExchangeRateService = provideExchangeRateService()
) {
    suspend fun getExchangeRate(): Double {
        return dataStore.getExchangeRate()
    }

    suspend fun setExchangeRate(rate: Double) {
        dataStore.setExchangeRate(rate)
    }

    suspend fun getLastFetchedExchangeRateHours(): Int {
        return dataStore.getExchangeRateLastUpdated()
    }

    suspend fun fetchExchangeRateFromNetwork(): Double {
        return service.getExchangeRate().bpi.USD.rate_float
    }
}
