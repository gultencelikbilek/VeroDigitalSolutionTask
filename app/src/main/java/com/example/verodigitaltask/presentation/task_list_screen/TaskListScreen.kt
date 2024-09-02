package com.example.verodigitaltask.presentation.task_list_screen

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.verodigitaltask.domain.model.Task
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.journeyapps.barcodescanner.CaptureActivity


@Composable
fun TaskListScreen(
    token: String,
    taskViewModel: TaskViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val taskList by taskViewModel.tasks.collectAsState()
    val searchQuery by taskViewModel.searchQuery.collectAsState()
    val isRefreshing by taskViewModel.isRefreshing.collectAsState()
    val qrScannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val qrCode = intent?.getStringExtra("SCAN_RESULT") ?: ""
            taskViewModel.updateSearchQuery(qrCode)
        }
    }
    val context = LocalContext.current
    // Ekran içeriği
    TaskListContent(
        taskList = taskViewModel.getFilteredTasks(),
        searchQuery = searchQuery,
        onSearchQueryChanged = { query ->
            taskViewModel.updateSearchQuery(query)
        },
        onRefresh = { taskViewModel.refreshTasks() },
        navHostController = navHostController,
        isRefreshing = isRefreshing,
        onScanQrCode = {
            val scanIntent = Intent(context, CaptureActivity::class.java)
            qrScannerLauncher.launch(scanIntent)
        }
    )

    LaunchedEffect(taskList) {
        Log.d("TaskListScreen", "Task list: $taskList")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListContent(
    taskList: List<Task>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onRefresh: () -> Unit,
    navHostController: NavHostController,
    isRefreshing: Boolean,
    onScanQrCode: () -> Unit

) {
    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChanged = onSearchQueryChanged
            )
        },
        floatingActionButton = {
            Button(onClick = { onScanQrCode() }) {
                Text("QR")
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        content = { padding ->
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = onRefresh
                ) {
                    if (taskList.isEmpty()) {
                        Text(
                            text = "No tasks available.",
                            modifier = Modifier.padding(16.dp),
                            style = TextStyle(color = Color.Gray)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(padding)
                                .padding(top = 6.dp)
                        ) {
                            items(taskList) { task ->
                                TaskItem(task)
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = { newValue ->
            onQueryChanged(newValue)
        },
        placeholder = {
            Text(
                text = "Search",
                color = Color.Gray
            )
        },
        trailingIcon = {
            IconButton(onClick = { keyboardController?.hide() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Black,
            focusedTrailingIconColor = Color.Black,
            unfocusedTrailingIconColor = Color.Gray,
            focusedLabelColor = Color.Black,
            containerColor = Color.White,
            focusedTextColor = Color.Black        ),
        textStyle = TextStyle(
            color = Color.Black
        )
    )
}

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 10.dp)
            .padding(top = 6.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(task.colorCode))
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)

        ) {
            Text(
                text = task.task,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = task.title,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = task.description,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
