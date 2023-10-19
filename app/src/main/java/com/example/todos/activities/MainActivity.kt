package com.example.todos.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.ActivityMainBinding
import com.example.todos.models.Data
import com.example.todos.models.TodoModelList
import com.example.todos.services.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var BASE_URL = "https://editor.mobillor.net/"
    private var todoModel = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      As the Activity gets created the getData() will get the data from the API that will displayed the RecyclerView
        getData()

        binding.btnAddNewTodos.setOnClickListener {

            val newTodo :String = binding.editNewTodos.editText?.text.toString()

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            val jsonPlaceHolder = retrofitBuilder.create(ApiInterface::class.java)

            val addNewTodo = Data(0,0,"$newTodo")

            val call = jsonPlaceHolder.postData(addNewTodo)

            call.enqueue(object : Callback<Data?> {
                override fun onResponse(
                    call: Call<Data?>,
                    response: Response<Data?>
                ) {
                    todoModel.clear()
                    Toast.makeText(it.context , "Todo Created Successfully", Toast.LENGTH_SHORT).show()
                    binding.editNewTodos.editText?.text?.clear()
                    binding.todosLoadingProgressBar.visibility = View.VISIBLE
                    getData()
                }
                override fun onFailure(call: Call<Data?>, t: Throwable) {
                    Log.d("error","${t.localizedMessage}")
                }
            })
        }
    }
    private fun getData(){

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<TodoModelList?> {
            override fun onResponse(
                call: Call<TodoModelList?>,
                response: Response<TodoModelList?>
            ) {
                val responseBody = response.body()
                for (newTodo in  responseBody!!.data){
                    todoModel.add(newTodo)
                    todoModel.sortByDescending { it.id }
                }
                val adapter = TodoAdapter(this@MainActivity , todoModel)
                binding.mainRecyclerView.adapter = adapter
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.todosLoadingProgressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<TodoModelList?>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage,Toast.LENGTH_LONG).show()
                Log.d("error",t.localizedMessage)
            }
        })

    }

}