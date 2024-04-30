package com.example.expensestracking.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Text(text = "Balance placeholder")
            Button(onClick = { /*TODO*/ }) {
                // TODO: Top-up button
            }
        }

        Button(onClick = { /*TODO*/ }) {
            // TODO: Add transaction button
        }

        
    }
}
