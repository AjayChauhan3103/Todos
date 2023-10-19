package com.example.todos.services

import com.example.todos.models.Data
import com.example.todos.models.TodoModelList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("api/todo")
    fun getData(): Call<TodoModelList>

    @POST("api/todo")
    fun postData(@Body addNewTodo: Data): Call<Data>

    @DELETE("api/todo/{id}")
    fun deleteData(@Path("id")  id :Int) :Call<Unit>
}