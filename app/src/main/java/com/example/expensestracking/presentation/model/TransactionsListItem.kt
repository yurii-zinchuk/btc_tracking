package com.example.expensestracking.presentation.model

import androidx.compose.ui.graphics.Color

sealed class TransactionsListItem {
    data class DateItem(val date: String) : TransactionsListItem()

    data class TransactionItem(
        val iconColor: Color,
        val category: String,
        val amount: String,
        val time: String
    ) : TransactionsListItem()
}
