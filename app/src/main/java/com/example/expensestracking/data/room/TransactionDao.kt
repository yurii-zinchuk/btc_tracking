package com.example.expensestracking.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY time DESC LIMIT :limit OFFSET :offset")
    suspend fun getTransactionsPerPage(limit: Int, offset: Int): List<TransactionDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionDB)
}
