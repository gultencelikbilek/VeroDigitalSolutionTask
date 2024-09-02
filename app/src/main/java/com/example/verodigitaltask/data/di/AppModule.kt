package com.example.verodigitaltask.data.di

import android.content.Context
import androidx.room.Room
import com.example.verodigitaltask.data.Constants
import com.example.verodigitaltask.data.db.TaskDatabase
import com.example.verodigitaltask.data.network.ApiService
import com.example.verodigitaltask.domain.repo.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(@ApplicationContext app : Context): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }

    @Provides
    @Singleton
    fun providesRoom(@ApplicationContext context : Context):TaskDatabase =
         Room.databaseBuilder(
             context,
             TaskDatabase::class.java,
             "task_db"
         ).build()

    @Provides
    @Singleton
    fun providesRepoTaskImpl(
        @ApplicationContext context: Context,
        apiService : ApiService
    ) : TaskRepository = TaskRepositoryImpl(context,apiService)


}