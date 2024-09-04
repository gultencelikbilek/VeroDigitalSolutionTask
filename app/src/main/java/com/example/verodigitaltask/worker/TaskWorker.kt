package com.example.verodigitaltask.worker


import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.verodigitaltask.data.db.TaskDatabase
import com.example.verodigitaltask.data.di.AuthRepositoryImpl
import com.example.verodigitaltask.data.network.ApiService
import com.example.verodigitaltask.domain.model.toTaskEntity
import retrofit2.HttpException
import java.io.IOException
class TaskWorker (
    val context: Context,
    workerParameters: WorkerParameters,
    private val apiService: ApiService,
    private val taskDatabase: TaskDatabase,
    private val authRepository: AuthRepositoryImpl
) : CoroutineWorker(context,workerParameters){
    override suspend fun doWork(): Result {
        return try {
            // Token al
            val token = authRepository.getAuthToken()

            val response = apiService.getTasks("Bearer $token")

            if (response.isSuccessful) {
                val tasks = response.body() ?: emptyList()

                taskDatabase.taskDao.insertAll(tasks.map { it.toTaskEntity() })

                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: IOException) {
            Log.d("IOException:",e.message.toString())
            Result.retry()
        } catch (e: HttpException) {
            Log.d("HttpException:",e.message.toString())
            Result.retry()
        }
    }

}


