package com.example.verodigitaltask.domain.repo

import com.example.verodigitaltask.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>

    suspend fun insertAll(tasks: List<TaskEntity>)

    fun searchTasks(query: String): Flow<List<TaskEntity>>
}