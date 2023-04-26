package com.example.socialnetwork.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R

class ImageAdapter() : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    private var userList = ArrayList<Uri>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image,parent,false)
        return MyViewHolder(itemView)
    }

    fun setFilteredList(list : ArrayList<Uri>){
        this.userList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = userList[position]
        holder.imageView2.setImageURI(item)

    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class  MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var imageView2 : ImageView = itemView.findViewById(R.id.anh2)
    }
}