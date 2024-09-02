package com.example.verodigitaltask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.verodigitaltask.presentation.main_screen.MainScreen
import com.example.verodigitaltask.QrScannerScreen
import com.example.verodigitaltask.presentation.task_list_screen.TaskListScreen

@Composable
fun NavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination =Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navHostController)
        }
        composable(
            route = Screen.TaskListScreen.route+"{token}",
            arguments = listOf(
                navArgument("token") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token")
            TaskListScreen(
                token = token ?: "",
                navHostController = navHostController
            )
        }
        composable(Screen.QrScannerScreen.route) {
            QrScannerScreen(navHostController = navHostController) { qrCode ->
                navHostController.popBackStack()
            }
        }
    }
}

