package com.example.verodigitaltask.presentation.task_list_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verodigitaltask.data.di.AuthRepositoryImpl
import com.example.verodigitaltask.data.di.TaskRepositoryImpl
import com.example.verodigitaltask.domain.model.Task
import com.example.verodigitaltask.domain.model.toTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepositoryImpl,
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            val token = authRepository.getAuthToken()
            Log.d("TaskViewModel", "Fetching tasks with token: $token")
            taskRepository.fetchAndStoreTasks(token)

            taskRepository.getAllTasks().collect { taskEntities ->
                Log.d("TaskViewModel", "Tasks fetched: ${taskEntities.size}")
                _tasks.value = taskEntities.map { it.toTask() }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredTasks(): List<Task> {
        val query = _searchQuery.value.lowercase()
        return _tasks.value.filter {
            it.title.lowercase().contains(query) ||
                    it.description.lowercase().contains(query)
        }
    }

    fun refreshTasks() {
        viewModelScope.launch {
            _isRefreshing.value = true
            Log.d("TaskViewModel", "Refreshing tasks started")
            try {
                loadTasks() // Veri yüklemesini yap
                Log.d("TaskViewModel", "Tasks refreshed successfully")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
