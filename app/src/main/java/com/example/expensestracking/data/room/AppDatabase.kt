package com.example.expensestracking.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensestracking.ApplicationContextProvider

@Database(entities = [TransactionDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ApplicationContextProvider.getAppContext(),
                    AppDatabase::class.java,
                    "app_database"
                ).build()
            }

            return instance as AppDatabase
        }
    }
}
