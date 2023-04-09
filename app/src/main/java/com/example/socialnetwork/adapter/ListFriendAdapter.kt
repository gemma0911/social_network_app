package com.example.socialnetwork.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.ListMessageFriendActivity
import com.example.socialnetwork.R
import com.example.socialnetwork.model.User
import com.squareup.picasso.Picasso

class ListFriendAdapter : RecyclerView.Adapter<ListFriendAdapter.MyViewHolder>() {

    private val userList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = userList[position]

        holder.uri = item.uri!!
        holder.urlImage = item.urlImage!!
        holder.user = item.user!!

        holder.textView.text = item.user
        val image = holder.image
        Picasso.get()
            .load(item.urlImage)
            .fit()
            .into(image)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(userList : List<User>){
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()

    }

    class  MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.findViewById(R.id.nameUser)
        val image : ImageView = itemView.findViewById(R.id.imageUser)
        var uri : String = ""
        var user : String = ""
        var urlImage : String = ""
        init {
            itemView.setOnClickListener {
                var intent : Intent = Intent(itemView.context,ListMessageFriendActivity::class.java)
                intent.putExtra("uri","$uri")
                intent.putExtra("name","$user")
                intent.putExtra("image","$urlImage")
                startActivity(itemView.context,intent,Bundle())
            }
        }
    }

}