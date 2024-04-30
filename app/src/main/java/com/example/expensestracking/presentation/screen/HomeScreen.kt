package com.example.expensestracking.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.expensestracking.MainViewModel
import com.example.expensestracking.presentation.model.TransactionsListItem

@Composable
fun HomeScreen(
    vm: MainViewModel
) {
    vm.loadTransactions()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isTopUpDialogVisible by remember { mutableStateOf(false) }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.align(Alignment.End),
            text = "USD/BTC: ${vm.exchangeRate.value}",
        )
        Spacer(modifier = Modifier.height(230.dp))
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.wrapContentHeight(),
                fontSize = 22.sp,
                text = "${vm.balance.value}"
            )
            IconButton(
                onClick = { isTopUpDialogVisible = true },
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
        TransactionsList(vm.transactionsData.collectAsLazyPagingItems())

        TopUpDialog(
            isVisible = isTopUpDialogVisible,
            onDismissed = { isTopUpDialogVisible = false },
            onAdd = { amount ->
                vm.onTopUpBalance(amount)
            }
        )
    }

}

@Composable
private fun TransactionsList(transactions: LazyPagingItems<TransactionsListItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(transactions.itemSnapshotList) {
            if (it is TransactionsListItem.TransactionItem)
                TransactionItem(it)
            else
                DateItem(it as TransactionsListItem.DateItem)
        }
    }
}

@Composable
private fun TransactionItem(data: TransactionsListItem.TransactionItem) {
    Row {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = data.iconColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = data.category)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = data.amount)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = data.time)
    }
}

@Composable
fun DateItem(data: TransactionsListItem.DateItem) {
    Text(text = data.date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopUpDialog(isVisible: Boolean, onDismissed: () -> Unit, onAdd: (Double) -> Unit) {
    if (isVisible) {
        AlertDialog(onDismissRequest = onDismissed) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(text = "Enter amount to add")

                    var topUpAmount by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = topUpAmount,
                        onValueChange = { topUpAmount = it },
                        label = { Text(text = "Amount") },
                        suffix = { Text(text = "BTC") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = onDismissed) {
                            Text(text = "Cancel")
                        }
                        TextButton(onClick = {
                            onAdd.invoke(topUpAmount.toDouble())
                            onDismissed.invoke()
                        }) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
    }
}
