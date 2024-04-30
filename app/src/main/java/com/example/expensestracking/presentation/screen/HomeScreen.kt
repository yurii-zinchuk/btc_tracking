package com.example.expensestracking.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensestracking.TransactionsViewModel

@Composable
fun HomeScreen(
    vm: TransactionsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.align(Alignment.End),
            text = "USD/BTC: 63123.00",
        )
        Spacer(modifier = Modifier.height(230.dp))
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.wrapContentHeight(),
                fontSize = 22.sp,
                text = "Balance"
            )
            IconButton(
                onClick = { /*TODO: Open dialog with input*/ },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(28.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White
                )

            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { vm.onAddTransaction() }) {
            Text(text = "Add transaction")
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Recent transactions:",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        TransactionsList()

    }
}

@Composable
private fun TransactionsList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(10) {
            if (it % 2 == 0)
                DateItem()
            TransactionItem()
        }
    }
}

@Composable
private fun TransactionItem() {
    Row {
        Icon(Icons.Filled.Star, null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Category")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "323.1")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Time")
    }
}

@Composable
fun DateItem() {
    Text(text = "Date")
}
