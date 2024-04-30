package com.example.expensestracking.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.expensestracking.data.TransactionPagingSource
import com.example.expensestracking.data.room.AppDatabase

class TransactionsRepository(
    private val database: AppDatabase = AppDatabase.getInstance()
) {
    fun getTransactions() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { TransactionPagingSource(database) }
    ).flow
}
