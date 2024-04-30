package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.ExchangeRateRepository

class SetExchangeRateUseCase(
    private val repository: ExchangeRateRepository = ExchangeRateRepository()
) {
    suspend fun execute(rate: Double) {
        return repository.setExchangeRate(rate)
    }
}
