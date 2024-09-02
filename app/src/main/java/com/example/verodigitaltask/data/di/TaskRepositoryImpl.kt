package com.example.verodigitaltask.data.di

import android.content.Context
import com.example.verodigitaltask.data.network.ApiService
import com.example.verodigitaltask.domain.model.TaskEntity
import com.example.verodigitaltask.domain.model.toTaskEntity
import com.example.verodigitaltask.domain.repo.TaskRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    @ApplicationContext context : Context,
    private val apiService: ApiService
) : TaskRepository {

    private val taskDao = AppModule.providesRoom(context).taskDao

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    override suspend fun insertAll(tasks: List<TaskEntity>) {
        taskDao.insertAll(tasks)
    }

    override fun searchTasks(query: String): Flow<List<TaskEntity>> {
        return taskDao.searchTasks(query)
    }

    suspend fun fetchAndStoreTasks(token: String) {
        val response = apiService.getTasks("Bearer $token")
        if (response.isSuccessful) {
            response.body()?.let { tasks ->
                val taskEntities = tasks.map { it.toTaskEntity() }
                taskDao.insertAll(taskEntities)
            }
        }
    }
}
