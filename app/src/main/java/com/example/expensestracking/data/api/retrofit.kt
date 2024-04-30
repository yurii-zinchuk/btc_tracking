package com.example.expensestracking.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.coindesk.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val service: ExchangeRateService = retrofit.create(ExchangeRateService::class.java)

fun provideExchangeRateService() = service
suspend fun fetchExchangeRate(): Double? {
    return try {
        val response = service.getExchangeRate()
        response.bpi.USD.rate_float
    } catch (e: Exception) {
        null
    }
}
