package com.example.socialnetwork.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import com.example.socialnetwork.model.Friend
import com.example.socialnetwork.model.User
import com.squareup.picasso.Picasso

class FriendAdapter() : RecyclerView.Adapter<FriendAdapter.MyViewHolder>() {

    private var friendList = ArrayList<Friend>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend,parent,false)
        return MyViewHolder(itemView)
    }

    fun updateFriendList(userList : List<Friend>){
        this.friendList.clear()
        this.friendList.addAll(userList)
        notifyDataSetChanged()

    }
    fun setFilteredList(list : ArrayList<Friend>){
        this.friendList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = friendList[position]
        val image = holder.image
        holder.name.text = item.user
        Picasso.get()
            .load(item.urlImage)
            .fit()
            .into(image)
    }
    override fun getItemCount(): Int {
        return friendList.size
    }

    class  MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var name : TextView = itemView.findViewById(R.id.nameUser1)
        var image : ImageView = itemView.findViewById(R.id.imageUser1)
    }
}