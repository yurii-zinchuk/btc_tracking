package com.example.expensestracking.domain.model

import java.util.Date

data class Transaction(
    val type: TransactionType,
    val category: TransactionCategory,
    val amount: Double,
    val time: Date
)
