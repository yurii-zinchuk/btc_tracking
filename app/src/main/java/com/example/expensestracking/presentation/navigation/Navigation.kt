package com.example.expensestracking.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensestracking.MainViewModel
import com.example.expensestracking.presentation.screen.AddTransactionScreen
import com.example.expensestracking.presentation.screen.HomeScreen

@Composable
fun Navigation(
    controller: NavHostController = rememberNavController(),
    vm: MainViewModel
) {
    NavHost(
        navController = controller,
        startDestination = Route.HOME_SCREEN.route,
    ) {

        composable(
            route = Route.HOME_SCREEN.route
        ) {
            HomeScreen(vm)
        }

        composable(
            route = Route.ADD_TRANSACTION_SCREEN.route
        ) {
            AddTransactionScreen(vm)
        }

    }
}
