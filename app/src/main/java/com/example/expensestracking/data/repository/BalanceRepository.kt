package com.example.expensestracking.data.repository

import com.example.expensestracking.data.datastore.DataStoreManager

class BalanceRepository(
    private val dataStore: DataStoreManager = DataStoreManager
) {
    suspend fun getBalance(): Double {
        return dataStore.getCurrentBalance()
    }

    suspend fun addToBalance(amount: Double) {
        dataStore.addToCurrentBalance(amount)
    }

    suspend fun subtractFromBalance(amount: Double) {
        dataStore.addToCurrentBalance(amount.times(-1))
    }
}
