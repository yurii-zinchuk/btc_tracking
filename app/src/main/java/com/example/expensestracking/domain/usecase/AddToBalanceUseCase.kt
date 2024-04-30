package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.BalanceRepository

class AddToBalanceUseCase(
    private val repository: BalanceRepository = BalanceRepository()
) {
    suspend fun execute(amount: Double) {
        return repository.addToBalance(amount)
    }
}
