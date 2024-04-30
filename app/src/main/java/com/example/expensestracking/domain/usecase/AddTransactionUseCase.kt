package com.example.expensestracking.domain.usecase

import com.example.expensestracking.data.repository.TransactionsRepository
import com.example.expensestracking.data.room.TransactionDB
import com.example.expensestracking.domain.model.Transaction

class AddTransactionUseCase(
    private val transactionsRepository: TransactionsRepository = TransactionsRepository()
) {
    suspend fun execute(transaction: Transaction) {
        transactionsRepository.addTransaction(transaction)
    }
}
