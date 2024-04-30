package com.example.expensestracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.expensestracking.presentation.navigation.Navigation
import com.example.expensestracking.presentation.navigation.Route
import com.example.expensestracking.presentation.theme.ExpensesTrackingTheme

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by lazy { MainViewModel() }
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationContextProvider.initialize(applicationContext)

        initObservers()
        initContent()
    }

    override fun onResume() {
        super.onResume()

        vm.tryFetchExchangeRate()
    }

    private fun initContent() {
        setContent {
            navController = rememberNavController()
            ExpensesTrackingTheme {
                Column(
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            Color.White.toArgb(),
                            Color.White.toArgb()
                        )
                    )
                    Navigation(
                        controller = navController,
                        vm = vm
                    )
                }
            }
        }
    }

    private fun initObservers() {
        vm.eventAddTransaction.observe(this) {
            if (it != null)
                navController.navigate(Route.ADD_TRANSACTION_SCREEN.route)
        }

        vm.eventTransactionAdded.observe(this) {
            if (it != null)
                navController.popBackStack()
        }
    }
}
