package com.example.expensestracking.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensestracking.ApplicationContextProvider

@Database(entities = [TransactionDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        fun getInstance(): AppDatabase {
            return Room.databaseBuilder(
                ApplicationContextProvider.getAppContext(),
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }
}
