package com.example.socialnetwork.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import com.example.socialnetwork.model.FriendModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class FriendRequestAdapter() : RecyclerView.Adapter<FriendRequestAdapter.MyViewHolder>() {

    private lateinit var mDbRef : DatabaseReference
    private var friendList = ArrayList<FriendModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_request,parent,false)
        return MyViewHolder(itemView)
    }

    fun updateFriendList(userList : List<FriendModel>){
        this.friendList.clear()
        this.friendList.addAll(userList)
        notifyDataSetChanged()

    }
    fun setFilteredList(list : ArrayList<FriendModel>){
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