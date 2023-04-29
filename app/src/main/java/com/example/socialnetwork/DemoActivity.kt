package com.example.socialnetwork

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_demo)
//        val recyclerView = findViewById<RecyclerView>(R.id.listImageView)
//        val imageAdapter = ImageAdapter()
//        getAllImageUris()?.let { imageAdapter.setFilteredList(it) }
//        recyclerView.adapter = imageAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    

    @SuppressLint("Range")
    private fun getAllImageUris(): ArrayList<Uri>? {
        val imageUris: ArrayList<Uri> = ArrayList()
        // Lấy các Uri của ảnh từ bộ nhớ ngoài
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
}