package com.example.expensestracking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.expensestracking.domain.model.Transaction
import com.example.expensestracking.domain.usecase.GetTransactionsUseCase
import com.example.expensestracking.presentation.model.TransactionsListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase = GetTransactionsUseCase()
) : ViewModel() {
    // Private Events
    private val _eventAddTransaction = MutableLiveData<Unit>()
    private val _eventTransactionAdded = MutableLiveData<Unit>()

    // Private State
    private val _balance = mutableDoubleStateOf(2358.2)
    private val _exchangeRate = mutableDoubleStateOf(6312.2)

    // Public Events
    val eventAddTransaction = _eventAddTransaction as LiveData<Unit>
    val eventTransactionAdded = _eventTransactionAdded as LiveData<Unit>

    // Public State
    val balance = _balance as State<Double>
    val exchangeRate = _exchangeRate as State<Double>
    val transactionsData: Flow<PagingData<TransactionsListItem.TransactionItem>>

    init {
        transactionsData = getTransactionsUseCase.execute()
            .map { pagingData ->
                pagingData.map { transaction ->
                    TransactionsListItem.TransactionItem(
                        Color.Red,
                        transaction.category.toString(),
                        transaction.amount.toString(),
                        transaction.time.time.toString()
                    )
                }
            }
            .cachedIn(viewModelScope)
    }

    fun onAddTransaction() {
        _eventAddTransaction.postValue(Unit)
    }

    fun onTransactionAdded() {
        _eventTransactionAdded.postValue(Unit)
    }

    fun onTopUpBalance(amount: Double) {
        _balance.doubleValue += amount
    }
}
