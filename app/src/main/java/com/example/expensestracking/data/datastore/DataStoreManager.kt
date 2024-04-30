package com.example.expensestracking.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.expensestracking.ApplicationContextProvider
import kotlinx.coroutines.flow.first
import java.time.Instant
import java.util.Date

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DataStoreManager {
    suspend fun getCurrentBalance(): Double {
        val key = doublePreferencesKey("balance")
        return ApplicationContextProvider.getAppContext().dataStore.data.first()[key] ?: 0.0
    }

    suspend fun addToCurrentBalance(amount: Double) {
        val key = doublePreferencesKey("balance")
        val dataStore = ApplicationContextProvider.getAppContext().dataStore
        val currentAmount = dataStore.data.first()[key] ?: 0.0
        dataStore.edit {
            it[key] = currentAmount + amount
        }
    }

    suspend fun getExchangeRate(): Double {
        val key = doublePreferencesKey("rate")
        return ApplicationContextProvider.getAppContext().dataStore.data.first()[key] ?: 0.0
    }

    suspend fun setExchangeRate(rate: Double) {
        val key = doublePreferencesKey("rate")
        val timeKey = intPreferencesKey("last_updated")
        val dataStore = ApplicationContextProvider.getAppContext().dataStore
        dataStore.edit {
            it[key] = rate
            it[timeKey] = Date.from(Instant.now()).hours
        }
    }

    suspend fun getExchangeRateLastUpdated(): Int {
        val timeKey = intPreferencesKey("last_updated")
        return ApplicationContextProvider.getAppContext().dataStore.data.first()[timeKey]
            ?: Date.from(
                Instant.now()
            ).hours
    }
}
