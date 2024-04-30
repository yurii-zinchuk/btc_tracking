package com.example.expensestracking.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.expensestracking.data.room.TransactionPagingSource
import com.example.expensestracking.data.room.AppDatabase
import com.example.expensestracking.data.room.TransactionDB
import com.example.expensestracking.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionsRepository(
    private val database: AppDatabase = AppDatabase.getInstance()
) {
    fun getTransactions(): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { TransactionPagingSource(database) }
        ).flow.map {
            it.map { transactionDB ->
                transactionFromDB(transactionDB)
            }
        }
    }

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

private fun transactionFromDB(transaction: TransactionDB): Transaction {
    return Transaction(
        type = transaction.type,
        category = transaction.category,
        amount = transaction.amount,
        time = transaction.time
    )
}
