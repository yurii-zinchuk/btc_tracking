package com.example.expensestracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expensestracking.presentation.screen.AddTransactionScreen
import com.example.expensestracking.presentation.theme.ExpensesTrackingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpensesTrackingTheme {
                AddTransactionScreen()
            }
        }
    }
}
