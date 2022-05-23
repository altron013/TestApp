package com.example.project1.data.models


data class Todo(
    val id: Int,
    val userId: Int,
    val title: String?,
    val completed: Boolean = false
)
