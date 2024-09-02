package com.example.verodigitaltask.domain.model

data class UserInfo(
    val active: Boolean,
    val businessUnit: String,
    val displayName: String,
    val firstName: String,
    val lastName: String,
    val personalNo: Int
)