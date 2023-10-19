package com.example.todos.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      As the Activity gets created the getData() will get the data from the API
        getData()

        binding.btnAddNewTodos.setOnClickListener {
            postData()
        }
    }
    private fun getData(){

    }

    private fun postData(){

    }
}