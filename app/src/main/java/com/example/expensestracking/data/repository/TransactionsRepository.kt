package com.example.expensestracking.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.expensestracking.data.TransactionPagingSource
import com.example.expensestracking.data.room.AppDatabase
import com.example.expensestracking.data.room.TransactionDB
import com.example.expensestracking.domain.model.Transaction

class TransactionsRepository(
    private val database: AppDatabase = AppDatabase.getInstance()
) {
    fun getTransactions() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { TransactionPagingSource(database) }
    ).flow

    suspend fun addTransaction(transaction: Transaction) {
        database.transactionDao().insert(
            transactionToDB(transaction)
        )
    }
}

private fun transactionToDB(transaction: Transaction): TransactionDB {
    return TransactionDB(
        type = transaction.type,
        category = transaction.category,
        amount = transaction.amount,
        time = transaction.time
    )
}
