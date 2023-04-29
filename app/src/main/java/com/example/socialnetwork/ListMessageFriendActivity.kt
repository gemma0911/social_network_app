package com.example.socialnetwork
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.adapter.MessageAdapter
import com.example.socialnetwork.model.Message
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.time.LocalTime
import java.util.Calendar


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


        mDbRef.child("users").child(uri!!).child("status")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.value == "online") {
                        findViewById<TextView>(R.id.status).setText(R.string.online)
                    } else {
                        findViewById<TextView>(R.id.status).setText(R.string.offline)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        senderRoom = uri + senderUid
        receiverRoom = senderUid + uri

        Log.d(TAG,"$senderRoom")
        Log.d(TAG,"$receiverRoom")



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
            if(message!=""){
                val messageObject = Message(message,senderUid)
                mDbRef.child("chats").child(senderRoom!!).child("message").push()
                    .setValue(messageObject)
                    .addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom!!).child("message").push()
                            .setValue(messageObject)
                    }
                val currentTime = LocalTime.now()
                val hour = currentTime.hour
                val minute = currentTime.minute
                val time = "$hour:$minute"
                val new = mapOf(
                    "newMess" to message,
                    "newTime" to time
                )
                mDbRef.child("action").child(senderRoom!!).child("new")
                    .updateChildren(new)
                    .addOnSuccessListener {
                        mDbRef.child("action").child(receiverRoom!!).child("new")
                            .updateChildren(new)
                    }
            }
            chatRecycleView.scrollToPosition(messageList.size -1)
            messageBox.setText("")
        }

        findViewById<ImageButton>(R.id.sendImage).setOnClickListener {
            addTask()
        }

        messageBox.setOnClickListener {
            chatRecycleView.scrollToPosition(messageList.size - 1)
        }

        findViewById<BottomAppBar>(R.id.view).setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.delete -> {
                    mDbRef.child("action").child(senderRoom!!).child("new").removeValue()
                    mDbRef.child("chats").child(senderRoom!!).child("message").removeValue()
                }

            }
            true
        })

        messageBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                findViewById<ImageButton>(R.id.sendImage).setBackgroundResource(R.drawable.baseline_camera_alt_24)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                findViewById<ImageButton>(R.id.sendImage).setBackgroundResource(R.drawable.bgr)

            }

            override fun afterTextChanged(s: Editable?) {
                if(messageBox.text.toString().isEmpty()){
                    findViewById<ImageButton>(R.id.sendImage).setBackgroundResource(R.drawable.baseline_camera_alt_24)
                } else {
                    findViewById<ImageButton>(R.id.sendImage).setBackgroundResource(R.drawable.bgr)
                }
            }
        })
    }

    @SuppressLint("Range")
    private fun getAllImageUris(): ArrayList<Uri>? {
        val imageUris: ArrayList<Uri> = ArrayList()
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID),
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                imageUris.add(uri)
                Log.d(TAG,"11$uri")
            }
            cursor.close()
        }
        return imageUris
    }


    private fun addTask() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.activity_demo, null)
        dialog.setContentView(view)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.show()
//        var bottomSheet = getAllImageUris()?.let { BottomSheet(it) }
//        bottomSheet?.show(supportFragmentManager,bottomSheet.tag)
    }

    private fun status(str : String) {
        val ref = FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser.uid)
        val data = mapOf(
            "status" to str
        )
        ref.updateChildren(data)
    }

    override fun onResume() {
        super.onResume()
        status("online")
    }

    override fun onPause() {
        super.onPause()
        status("offline")
    }
}