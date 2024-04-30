package com.example.expensestracking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.expensestracking.domain.model.Transaction
import com.example.expensestracking.domain.model.TransactionCategory
import com.example.expensestracking.domain.model.TransactionType
import com.example.expensestracking.domain.usecase.AddTransactionUseCase
import com.example.expensestracking.domain.usecase.GetTransactionsUseCase
import com.example.expensestracking.presentation.model.TransactionsListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.util.Locale

class MainViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase = GetTransactionsUseCase(),
    private val addTransactionUseCase: AddTransactionUseCase = AddTransactionUseCase()
) : ViewModel() {
    // Private Events
    private val _eventAddTransaction = MutableLiveData<Unit>()
    private val _eventTransactionAdded = MutableLiveData<Unit>()

    // Private State
    private val _balance = mutableDoubleStateOf(2358.2)
    private val _exchangeRate = mutableDoubleStateOf(6312.2)
    private val _selectedTransactionCategory = mutableStateOf<TransactionCategory?>(null)
    private val _selectedTransactionAmount = mutableStateOf<String?>(null)

    // Public Events
    val eventAddTransaction = _eventAddTransaction as LiveData<Unit>
    val eventTransactionAdded = _eventTransactionAdded as LiveData<Unit>

    // Public State
    val balance = _balance as State<Double>
    val exchangeRate = _exchangeRate as State<Double>
    val selectedTransactionCategory = _selectedTransactionCategory as State<TransactionCategory?>
    val selectedTransactionAmount = _selectedTransactionAmount as State<String?>
    val transactionCategories = mutableStateOf(TransactionCategory.entries)

    lateinit var transactionsData: Flow<PagingData<TransactionsListItem.TransactionItem>>

    fun loadTransactions() {
        transactionsData = getTransactionsUseCase.execute()
            .map { pagingData ->
                pagingData.map { transaction ->
                    TransactionsListItem.TransactionItem(
                        if (transaction.type == TransactionType.EXPENSE) Color.Red else Color.Green,
                        transaction.category.toString().lowercase().capitalize(Locale.ROOT),
                        transaction.amount.toString(),
                        transaction.time.run { "$hours:$minutes" }
                    )
                }
            }
            .cachedIn(viewModelScope)
    }

    fun onAddTransaction() {
        _eventAddTransaction.postValue(Unit)
    }

    fun onSelectNewTransactionCategory(type: TransactionCategory) {
        _selectedTransactionCategory.value = type
    }

    fun onSelectNewTransactionAmount(amount: String) {
        _selectedTransactionAmount.value = amount
    }

    fun onTransactionAdded() = viewModelScope.launch(Dispatchers.IO) {
        addTransactionUseCase.execute(
            Transaction(
                type = if (_selectedTransactionAmount.value!!.toDouble() < 0) TransactionType.EXPENSE else TransactionType.INCOME,
                category = _selectedTransactionCategory.value!!,
                amount = _selectedTransactionAmount.value!!.toDouble(),
                time = Date.from(Instant.now())
            )
        )
        _eventTransactionAdded.postValue(Unit)
    }

    fun onTopUpBalance(amount: Double) {
        _balance.doubleValue += amount
    }
}
