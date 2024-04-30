package com.example.expensestracking.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensestracking.TransactionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    vm: TransactionsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isDropdownVisible by rememberSaveable { mutableStateOf(false) }
        var selectedCategory by rememberSaveable { mutableStateOf("") }
        var transactionAmount by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(240.dp))

        Text(
            text = "Add new transaction",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        ExposedDropdownMenuBox(
            expanded = isDropdownVisible,
            onExpandedChange = { isDropdownVisible = isDropdownVisible.not() }
        ) {

            CompositionLocalProvider(
                LocalTextInputService provides null
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    modifier = Modifier
                        .menuAnchor()
                        .clickable { },
                    readOnly = true,
                    label = { Text(text = "Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownVisible)
                    }
                )
            }

            ExposedDropdownMenu(
                expanded = isDropdownVisible,
                onDismissRequest = {
                    isDropdownVisible = isDropdownVisible.not()
                }) {
                repeat(5) {
                    DropdownMenuItem(
                        text = { Text(text = "item: $it") },
                        onClick = {
                            selectedCategory = it.toString()
                            isDropdownVisible = false
                        })
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = transactionAmount,
            onValueChange = { transactionAmount = it },
            label = { Text(text = "Amount") },
            suffix = { Text(text = "BTC") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { vm.onTransactionAdded() },
            modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Add")
        }
    }
}
