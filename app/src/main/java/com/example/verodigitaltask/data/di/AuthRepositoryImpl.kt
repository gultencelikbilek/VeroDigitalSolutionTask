package com.example.verodigitaltask.data.di

import android.content.Context
import com.example.verodigitaltask.domain.model.LoginRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext context : Context
) {
    val apiService = AppModule.providesRetrofit(context)
    // Token alma işlemi
    suspend fun getAuthToken(): String {
        val loginRequest = LoginRequest(username = "365", password = "1")
        val response = apiService.login(loginRequest)
        return response.oauth.access_token
    }
}