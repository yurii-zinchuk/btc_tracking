package com.example.expensestracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionsViewModel : ViewModel() {
    private val _eventAddTransaction = MutableLiveData<Unit>()
    private val _eventTransactionAdded = MutableLiveData<Unit>()

    val eventAddTransaction = _eventAddTransaction as LiveData<Unit>
    val eventTransactionAdded = _eventTransactionAdded as LiveData<Unit>

    fun onAddTransaction() {
        _eventAddTransaction.postValue(Unit)
    }

    fun onTopUpBalance() {

    }

    fun onTransactionAdded() {
        _eventTransactionAdded.postValue(Unit)
    }
}
