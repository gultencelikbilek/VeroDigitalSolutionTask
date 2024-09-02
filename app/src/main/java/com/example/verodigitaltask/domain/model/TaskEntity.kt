package com.example.verodigitaltask.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val task: String,
    val title: String,
    val description: String,
    val colorCode: String
)

// Task -> TaskEntity dönüşüm fonksiyonu
fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        task = this.task,
        title = this.title,
        description = this.description,
        colorCode = this.colorCode
    )
}

// TaskEntity -> Task dönüşüm fonksiyonu
fun TaskEntity.toTask(): Task {
    return Task(
        task = this.task,
        title = this.title,
        description = this.description,
        colorCode = this.colorCode
    )
}

// List<TaskEntity> -> List<Task> dönüşüm fonksiyonu
fun List<TaskEntity>.toTaskList(): List<Task> {
    return this.map { it.toTask() }
}
