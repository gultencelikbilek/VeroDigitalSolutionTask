package com.example.verodigitaltask.presentation.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.verodigitaltask.data.di.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) :ViewModel(){

    init {
        fetchAuthToken()
    }
    // ViewModel'de Repository üzerinden token alma işlemi
    fun fetchAuthToken() = liveData {
        val token = authRepository.getAuthToken()
        emit(token)
        Log.d("token:",token)

    }
}