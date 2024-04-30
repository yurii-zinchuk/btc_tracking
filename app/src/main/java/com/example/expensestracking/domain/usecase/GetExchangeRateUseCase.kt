package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.ExchangeRateRepository

class GetExchangeRateUseCase(
    private val repository: ExchangeRateRepository = ExchangeRateRepository()
) {
    suspend fun execute(): Double {
        return repository.getExchangeRate()
    }
}
