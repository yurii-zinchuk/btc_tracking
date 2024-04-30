package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.BalanceRepository

class SubtractFromBalanceUseCase(
    private val repository: BalanceRepository = BalanceRepository()
) {
    suspend fun execute(amount: Double) {
        return repository.subtractFromBalance(amount)
    }
}
