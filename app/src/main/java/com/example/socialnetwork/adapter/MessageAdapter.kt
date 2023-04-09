package com.example.socialnetwork.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import com.example.socialnetwork.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context : Context,private val list1: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val THE_FIRST_VIEW = 1
        const val THE_SECOND_VIEW = 2
    }

    class Receiver(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val receiver = itemView.findViewById<TextView>(R.id.receiver)!!
    }
    class Sender(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val sender = itemView.findViewById<TextView>(R.id.sender)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == THE_FIRST_VIEW) {
            val view : View = LayoutInflater.from(context).inflate(R.layout.receiver,parent,false)
            Receiver(view)
        } else {
            val view : View = LayoutInflater.from(context).inflate(R.layout.sender,parent,false)
            Sender(view)
        }
    }

    override fun getItemCount(): Int {
        return list1.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = list1[position]
        if(holder.javaClass == Receiver::class.java) {
            var viewHoder = holder as Receiver
            holder.receiver.text = currentMessage.messeage
        } else {
            var viewHoder = holder as Sender
            holder.sender.text = currentMessage.messeage
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currrentMessage = list1[position]
        return if(FirebaseAuth.getInstance().currentUser.uid?.equals(currrentMessage.senderId)!!){
            THE_FIRST_VIEW
        } else {
            THE_SECOND_VIEW
        }
    }
}
