package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.datastore.DataStoreManager
import com.example.expensestracking.data.repository.ExchangeRateRepository

class GetLastFetchedExchangeRateHoursUseCase(
    private val repository: ExchangeRateRepository = ExchangeRateRepository()
) {
    suspend fun execute(): Int {
        return repository.getLastFetchedExchangeRateHours()
    }
}
