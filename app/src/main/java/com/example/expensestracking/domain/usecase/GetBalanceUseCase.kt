package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.BalanceRepository

class GetBalanceUseCase(
    private val repository: BalanceRepository = BalanceRepository()
) {
    suspend fun execute(): Double {
        return repository.getBalance()
    }
}
