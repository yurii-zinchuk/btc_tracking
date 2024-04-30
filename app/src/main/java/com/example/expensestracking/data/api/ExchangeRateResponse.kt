package com.example.expensestracking.data.api

data class ExchangeRateResponse(
    val time: Time,
    val bpi: Bpi
)

data class Time(
    val updated: String
)

data class Bpi(
    val USD: Currency
)

data class Currency(
    val rate: String,
    val rate_float: Double
)
