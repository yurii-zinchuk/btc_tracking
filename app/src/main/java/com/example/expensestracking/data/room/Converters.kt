package com.example.expensestracking.data.room

import androidx.room.TypeConverter
import com.example.expensestracking.domain.model.TransactionCategory
import com.example.expensestracking.domain.model.TransactionType
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTransactionType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return enumValueOf(value)
    }

    @TypeConverter
    fun fromTransactionCategory(value: TransactionCategory): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionCategory(value: String): TransactionCategory {
        return enumValueOf(value)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
