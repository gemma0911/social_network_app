package com.example.socialnetwork.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.ListMessageFriendActivity
import com.example.socialnetwork.R
import com.example.socialnetwork.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ListFriendAdapter() : RecyclerView.Adapter<ListFriendAdapter.MyViewHolder>() {

    private lateinit var mDbRef : DatabaseReference

    private var userList = ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user,parent,false)
        return MyViewHolder(itemView)
    }

    fun updateUserList(userList : List<User>){
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()

    }
    fun setFilteredList(list : ArrayList<User>){
        this.userList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = userList[position]

        holder.uri = item.uri!!
        holder.urlImage = item.urlImage!!
        holder.user = item.user!!

        val senderUid = FirebaseAuth.getInstance().currentUser.uid
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("action").child(senderUid+item.uri).child("new").child("newMess")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    holder.newMess.text = snapshot.value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        mDbRef.child("action").child(senderUid+item.uri).child("new").child("newTime")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    holder.newTime.text = snapshot.value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



        if(item.status=="online"){
            holder.status.setBackgroundResource(R.drawable.baseline_lens_24)
        } else {
            holder.status.setBackgroundResource(R.drawable.bgr)
        }

        val image = holder.image
        holder.textView.text = item.user
        Picasso.get()
            .load(item.urlImage)
            .fit()
            .into(image)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class  MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.findViewById(R.id.nameUser)
        val image : ImageView = itemView.findViewById(R.id.imageUser)
        val status : ImageView = itemView.findViewById(R.id.status_user)
        var newTime : TextView = itemView.findViewById(R.id.newTime)
        var newMess : TextView = itemView.findViewById(R.id.newMess)
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