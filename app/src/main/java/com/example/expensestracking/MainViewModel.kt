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
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.expensestracking.domain.model.Transaction
import com.example.expensestracking.domain.model.TransactionCategory
import com.example.expensestracking.domain.model.TransactionType
import com.example.expensestracking.domain.usecase.AddToBalanceUseCase
import com.example.expensestracking.domain.usecase.AddTransactionUseCase
import com.example.expensestracking.domain.usecase.FetchExchangeRateUseCase
import com.example.expensestracking.domain.usecase.GetBalanceUseCase
import com.example.expensestracking.domain.usecase.GetExchangeRateUseCase
import com.example.expensestracking.domain.usecase.GetLastFetchedExchangeRateHoursUseCase
import com.example.expensestracking.domain.usecase.GetTransactionsUseCase
import com.example.expensestracking.domain.usecase.SubtractFromBalanceUseCase
import com.example.expensestracking.presentation.model.TransactionsListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class MainViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase = GetTransactionsUseCase(),
    private val addTransactionUseCase: AddTransactionUseCase = AddTransactionUseCase(),
    private val addToBalanceUseCase: AddToBalanceUseCase = AddToBalanceUseCase(),
    private val subtractFromBalanceUseCase: SubtractFromBalanceUseCase = SubtractFromBalanceUseCase(),
    private val getBalanceUseCase: GetBalanceUseCase = GetBalanceUseCase(),
    private val getExchangeRateUseCase: GetExchangeRateUseCase = GetExchangeRateUseCase(),
    private val getLastFetchedExchangeRateHoursUseCase: GetLastFetchedExchangeRateHoursUseCase = GetLastFetchedExchangeRateHoursUseCase(),
    private val fetchExchangeRateUseCase: FetchExchangeRateUseCase = FetchExchangeRateUseCase()
) : ViewModel() {
    // Private Events
    private val _eventAddTransaction = MutableLiveData<Unit>()
    private val _eventTransactionAdded = MutableLiveData<Unit>()

    // Private State
    private val _balance = mutableDoubleStateOf(0.0)
    private val _exchangeRate = mutableDoubleStateOf(0.0)
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

    lateinit var transactionsData: Flow<PagingData<TransactionsListItem>>

    // Yes, it needs refactoring :/
    fun loadTransactions() {
        transactionsData = getTransactionsUseCase.execute()
            .map { pagingData ->

                pagingData.insertSeparators { before: Transaction?, after: Transaction? ->
                    if (before == null && after != null) {
                        // This is the first or last item date
                        TransactionsListItem.DateItem(
                            "${after.time.date}.${after.time.month + 1}.${after.time.year + 1900}"
                        )
                    } else if (before == null || after == null) {
                        null
                    } else if (
                        before.time.date != after.time.date ||
                        before.time.month != after.time.month ||
                        before.time.year != after.time.year
                    ) {
                        // Transactions span different dates, so add a DateItem separator
                        TransactionsListItem.DateItem(
                            "${after.time.date}.${after.time.month}.${after.time.year}"
                        )
                    } else {
                        // Transactions are on the same date, so no separator needed
                        null
                    }
                }.map { item ->
                    if (item is Transaction) {
                        TransactionsListItem.TransactionItem(
                            if (item.type == TransactionType.EXPENSE) Color.Red else Color.Green,
                            item.category.toString().lowercase().capitalize(Locale.ROOT),
                            item.amount.toString(),
                            item.time.run { "$hours:$minutes" }
                        )
                    } else item
                }

            }
            .cachedIn(viewModelScope) as Flow<PagingData<TransactionsListItem>>

        viewModelScope.launch {
            _balance.doubleValue = getBalanceUseCase.execute()
            _exchangeRate.doubleValue = getExchangeRateUseCase.execute()
        }
    }

    fun tryFetchExchangeRate() = viewModelScope.launch(Dispatchers.IO) {
        val currentHour = Date.from(Instant.now()).hours
        val lastFetchedHour = getLastFetchedExchangeRateHoursUseCase.execute()
        if ((currentHour - lastFetchedHour).absoluteValue >= 1) {
            fetchExchangeRateUseCase.execute()
        }
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
                type = TransactionType.EXPENSE,
                category = _selectedTransactionCategory.value!!,
                amount = _selectedTransactionAmount.value!!.toDouble(),
                time = Date.from(Instant.now())
            )
        )
        subtractFromBalanceUseCase.execute(_selectedTransactionAmount.value!!.toDouble())
        _eventTransactionAdded.postValue(Unit)
    }

    fun onTopUpBalance(amount: Double) = viewModelScope.launch(Dispatchers.IO) {
        _balance.doubleValue += amount
        addToBalanceUseCase.execute(amount)
        addTransactionUseCase.execute(
            Transaction(
                type = TransactionType.INCOME,
                category = TransactionCategory.OTHER,
                amount = amount,
                time = Date.from(Instant.now())
            )
        )
    }
}
