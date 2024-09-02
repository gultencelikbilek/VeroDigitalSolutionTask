package com.example.verodigitaltask.data.network

import com.example.verodigitaltask.data.Constants
import com.example.verodigitaltask.domain.model.LoginRequest
import com.example.verodigitaltask.domain.model.LoginResponse
import com.example.verodigitaltask.domain.model.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.END_POINT)
    @Headers(
        "Authorization: Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz",
        "Content-Type: application/json"
    )
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @GET(Constants.END_POINT_GET)
    suspend fun getTasks(
        @Header("Authorization") authHeader: String
    ): Response<List<Task>>
}