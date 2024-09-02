package com.example.verodigitaltask.domain.model

data class Oauth(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: Any,
    val token_type: String
)