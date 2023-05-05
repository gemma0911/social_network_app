package com.example.socialnetwork.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.R
import com.example.socialnetwork.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom


class MessageAdapter(private val context : Context,private val list1: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val THE_FIRST_VIEW = 1
        const val THE_SECOND_VIEW = 2
        const val THE_3 = 3
        const val THE_4 = 4
    }

    class Receiver(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val receiver = itemView.findViewById<TextView>(R.id.receiver)!!
    }
    class Sender(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val sender = itemView.findViewById<TextView>(R.id.sender)!!
    }

    class ReceiverImage(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val receiverImage = itemView.findViewById<ImageView>(R.id.rc)!!
    }

    class SenderImage(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val senderImage = itemView.findViewById<ImageView>(R.id.sd)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == THE_FIRST_VIEW) {
            val view : View = LayoutInflater.from(context).inflate(R.layout.receiver,parent,false)
            Receiver(view)
        } else if(viewType == THE_SECOND_VIEW) {
            val view : View = LayoutInflater.from(context).inflate(R.layout.sender,parent,false)
            Sender(view)
        } else if(viewType == THE_3) {
            val view : View = LayoutInflater.from(context).inflate(R.layout.image_reciver,parent,false)
            ReceiverImage(view)
        } else {
            val view : View = LayoutInflater.from(context).inflate(R.layout.image_sender,parent,false)
            SenderImage(view)
        }
    }


    override fun getItemCount(): Int {
        return list1.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = list1[position]
        if(holder.javaClass == Receiver::class.java) {
            if(currentMessage.imageUrl==null) {
                var viewHoder = holder as Receiver
                holder.receiver.text = currentMessage.messeage
            }
        } else if(holder.javaClass == ReceiverImage::class.java) {
            if(currentMessage.imageUrl!=null){
                var viewHoder = holder as ReceiverImage
                Picasso
                    .get()
                    .load(currentMessage.imageUrl)
                    .fit()
                    .into(viewHoder.receiverImage)
            }
        } else if(holder.javaClass == Sender::class.java) {
            if(currentMessage.imageUrl==null){
                var viewHoder = holder as Sender
                holder.sender.text = currentMessage.messeage
            }
        } else {
            if(currentMessage.imageUrl!=null){
                var viewHoder = holder as SenderImage
                Picasso
                    .get()
                    .load(currentMessage.imageUrl)
                    .fit()
                    .into(viewHoder.senderImage)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currrentMessage = list1[position]
        return if(FirebaseAuth.getInstance().currentUser.uid?.equals(currrentMessage.senderId)!!){
            if(currrentMessage.imageUrl==null){
                THE_FIRST_VIEW
            } else {
                THE_3
            }
        } else {
            if(currrentMessage.imageUrl==null){
                THE_SECOND_VIEW
            } else {
                THE_4
            }
        }
    }
}
