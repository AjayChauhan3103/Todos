package com.example.todos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.R
import com.example.todos.models.Data
import com.example.todos.models.TodoModelList
import com.example.todos.services.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoAdapter(private val context : Context,private val dataList: ArrayList<Data>) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val todos = itemView.findViewById<TextView>(R.id.txtDisplayTodo)
        val delete = itemView.findViewById<ImageButton>(R.id.imgBtnDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todos_custom_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.todos.text = "${dataList[position].todo}"


        holder.delete.setOnClickListener {

//            Toast.makeText(it.context,dataList[position].id.toString(),Toast.LENGTH_LONG).show()

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://editor.mobillor.net/")
                .build()
            val jsonPlaceHolder = retrofitBuilder.create(ApiInterface::class.java)

            val call = jsonPlaceHolder.deleteData(dataList[position].id)

            call.enqueue(object : Callback<Unit?> {
                override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                    Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<Unit?>, t: Throwable) {
                    Toast.makeText(context,t.localizedMessage,Toast.LENGTH_LONG).show()
                }
            })
//            Toast.makeText(context,"Deleting ${dataList[position].todo}",Toast.LENGTH_LONG).show()
            dataList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return  dataList.size
    }


}