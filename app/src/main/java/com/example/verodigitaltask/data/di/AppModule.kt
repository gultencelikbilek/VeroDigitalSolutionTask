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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val interceptor = Interceptor{chain ->
        val originalrequest = chain.request()
        val newrequest = originalrequest.newBuilder()
            .addHeader( "Authorization" ,"Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
            .addHeader("Content-Type", "application/json")
            .build()
        chain.proceed(newrequest)
    }
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(@ApplicationContext app : Context): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
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