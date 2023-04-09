package com.example.socialnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.adapter.MessageAdapter
import com.example.socialnetwork.adapter.MessageAdapter.Companion.THE_FIRST_VIEW
import com.example.socialnetwork.adapter.MessageAdapter.Companion.THE_SECOND_VIEW
import com.example.socialnetwork.databinding.ActivityListMessageFriendBinding
import com.example.socialnetwork.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ListMessageFriendActivity : AppCompatActivity() {

    private lateinit var chatRecycleView : RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton : ImageButton
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList : ArrayList<Message>
    private lateinit var mDbRef : DatabaseReference

    var receiverRoom : String ?= null
    var senderRoom : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_message_friend)

        var uri = intent.getStringExtra("uri")
        var image = intent.getStringExtra("image")
        var name = intent.getStringExtra("name")

        val senderUid = FirebaseAuth.getInstance().currentUser.uid
        mDbRef = FirebaseDatabase.getInstance().reference

        senderRoom = uri + senderUid
        receiverRoom = senderUid + uri

        var imageView = findViewById<ImageView>(R.id.imageFrom)
        findViewById<TextView>(R.id.nameFrom).text = name
        Picasso.get()
            .load(image)
            .fit()
            .into(imageView)

        chatRecycleView = findViewById(R.id.listMessage)
        messageBox = findViewById(R.id.editText2)
        sendButton = findViewById(R.id.imageButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chatRecycleView.layoutManager = LinearLayoutManager(this)
        chatRecycleView.adapter = messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(i in snapshot.children) {
                        val message = i.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message,senderUid)
            mDbRef.child("chats").child(senderRoom!!).child("message").push()
                .setValue(messageObject)
                .addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("message").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }

    }
}