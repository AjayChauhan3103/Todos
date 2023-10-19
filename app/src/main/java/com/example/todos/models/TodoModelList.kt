package com.example.todos.models

data class TodoModelList(
    val `data`: List<Data>,
    val msg: String,
    val status: Boolean,
    val statusCode: Int
)