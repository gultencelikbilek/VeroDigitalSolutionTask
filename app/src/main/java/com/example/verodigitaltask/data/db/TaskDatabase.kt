package com.example.verodigitaltask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.verodigitaltask.domain.model.TaskEntity


@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase :RoomDatabase() {
    abstract val taskDao : TaskDao

}