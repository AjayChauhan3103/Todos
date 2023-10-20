package com.example.todos.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.todos.R
import com.example.todos.models.Data
import com.example.todos.models.TodoModelList
import com.example.todos.services.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class TodoAdapter(private val context : Context,private val dataList: ArrayList<Data>) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val todos = itemView.findViewById<EditText>(R.id.edtDisplayTodo)
        val delete = itemView.findViewById<ImageButton>(R.id.imgBtnDelete)
        val edit = itemView.findViewById<ImageButton>(R.id.imgBtnEdit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todos_custom_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position : Int) {
        holder.todos.setText(dataList[position].todo)


        holder.edit.setOnClickListener {
            updateData(holder.todos.text.toString(),dataList[position].id,context)
        }


        holder.delete.setOnClickListener {

//            Toast.makeText(it.context,dataList[position].id.toString(),Toast.LENGTH_LONG).show()

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://editor.mobillor.net/")
                .build()
            val jsonPlaceHolder = retrofitBuilder.create(ApiInterface::class.java)

//                        Toast.makeText(context,"${dataList[position].id}",Toast.LENGTH_LONG).show()


            val call = jsonPlaceHolder.deleteData(dataList[position].id)

            call.enqueue(object : Callback<Data?> {
                override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
//                    Toast.makeText(context,response.message(),Toast.LENGTH_LONG).show()

                   if( response.isSuccessful) {

                       dataList.removeAt(position)
                       notifyDataSetChanged()
                   }
                }

                override fun onFailure(call: Call<Data?>, t: Throwable) {
                    Toast.makeText(context,t.localizedMessage,Toast.LENGTH_LONG).show()
                }
            })
//            Toast.makeText(context,"Deleting ${dataList[position].todo}",Toast.LENGTH_LONG).show()

        }
    }

    override fun getItemCount(): Int {
        return  dataList.size
    }
}

private fun updateData(editTodo : String,id:Int,context: Context){

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl( "https://editor.mobillor.net/")
        .build()
    val jsonPlaceHolder = retrofitBuilder.create(ApiInterface::class.java)

    val editTodo = Data(id,0,"$editTodo")

    val call = jsonPlaceHolder.updateData(editTodo)


    call.enqueue(object : Callback<Data?> {
        override fun onResponse(
            call: Call<Data?>,
            response: Response<Data?>
        ) {
            Toast.makeText(context,"Todo Updated",Toast.LENGTH_LONG).show()

        }
        override fun onFailure(call: Call<Data?>, t: Throwable) {
            Log.d("error","${t.localizedMessage}")
        }
    })
}

