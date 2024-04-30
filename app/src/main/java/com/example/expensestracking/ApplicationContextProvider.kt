package com.example.expensestracking

import android.content.Context

object ApplicationContextProvider {
    private lateinit var appContext: Context

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun getAppContext(): Context {
        return appContext
    }
}
