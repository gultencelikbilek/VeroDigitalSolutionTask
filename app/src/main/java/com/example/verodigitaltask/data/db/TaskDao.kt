package com.example.verodigitaltask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.verodigitaltask.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<TaskEntity>)

    @Query("SELECT * FROM tasks WHERE task LIKE :query OR title LIKE :query OR description LIKE :query")
    fun searchTasks(query: String): Flow<List<TaskEntity>>
}