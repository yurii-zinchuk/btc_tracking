package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.TransactionsRepository

class GetTransactionsUseCase(
    private val repository: TransactionsRepository = TransactionsRepository()
) {
    fun execute() = repository.getTransactions()
}
