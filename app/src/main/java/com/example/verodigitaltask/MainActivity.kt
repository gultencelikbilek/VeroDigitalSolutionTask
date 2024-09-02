package com.example.verodigitaltask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.verodigitaltask.navigation.NavHost
import com.example.verodigitaltask.presentation.main_screen.MainScreen
import com.example.verodigitaltask.ui.theme.VeroDigitalTaskTheme
import com.example.verodigitaltask.worker.TaskWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //worker başlatma
        val taskWorker = PeriodicWorkRequestBuilder<TaskWorker>(60,TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(taskWorker)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VeroDigitalTaskTheme {
                val navController = rememberNavController()
                NavHost(navHostController = navController)

            }
        }
    }
}

