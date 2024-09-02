package com.example.verodigitaltask.navigation

sealed class Screen(val route : String) {

    object TaskListScreen : Screen("task_list_screen")
    object MainScreen : Screen("main_screen")
    object QrScannerScreen : Screen("qr_scanner_screen")
}