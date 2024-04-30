package com.example.expensestracking.domain.usecase

import androidx.paging.PagingData
import com.example.expensestracking.data.repository.TransactionsRepository
import com.example.expensestracking.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(
    private val repository: TransactionsRepository = TransactionsRepository()
) {
    fun execute(): Flow<PagingData<Transaction>> {
        return repository.getTransactions()
    }
}
