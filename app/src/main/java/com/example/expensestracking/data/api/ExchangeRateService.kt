package com.example.expensestracking.data.api

import retrofit2.http.GET


interface ExchangeRateService {
    @GET("v1/bpi/currentprice.json")
    suspend fun getExchangeRate(): ExchangeRateResponse
}
