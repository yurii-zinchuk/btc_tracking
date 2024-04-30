package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.ExchangeRateRepository

class FetchExchangeRateUseCase(
    private val exchangeRateRepository: ExchangeRateRepository = ExchangeRateRepository()
) {
    suspend fun execute() {
        val newExchangeRate = exchangeRateRepository.fetchExchangeRateFromNetwork()
        exchangeRateRepository.setExchangeRate(newExchangeRate)
    }
}
