package com.example.expensestracking.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.expensestracking.domain.model.TransactionCategory
import com.example.expensestracking.domain.model.TransactionType
import java.util.Date

@Entity(tableName = "transactions")
@TypeConverters(Converters::class)
data class TransactionDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TransactionType,
    val category: TransactionCategory,
    val amount: Double,
    val time: Date
)
