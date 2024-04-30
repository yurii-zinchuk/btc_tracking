package com.example.expensestracking.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.expensestracking.data.room.AppDatabase
import com.example.expensestracking.data.room.TransactionDB
import com.example.expensestracking.domain.model.Transaction

class TransactionPagingSource(private val database: AppDatabase) : PagingSource<Int, TransactionDB>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionDB> {
        return try {
            val nextPageNumber = params.key ?: 0
            val transactions = database.transactionDao().getTransactionsPerPage(
                limit = params.loadSize,
                offset = nextPageNumber * params.loadSize
            )
            LoadResult.Page(
                data = transactions,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (transactions.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TransactionDB>): Int? {
        return state.anchorPosition
    }
}
