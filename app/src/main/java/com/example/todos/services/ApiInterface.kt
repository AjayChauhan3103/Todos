package com.example.todos.services

import com.example.todos.models.Data
import com.example.todos.models.TodoModelList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("api/todo")
    fun getData(): Call<TodoModelList>

    @POST("api/todo")
    fun postData(@Body addNewTodo: Data): Call<Data>

    @DELETE("api/todo")
    fun deleteData(@Query("todo_id")  id :Int) :Call<Data>

}