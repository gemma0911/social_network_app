package com.example.socialnetwork

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.socialnetwork.databinding.ActivityRegisterBinding
import com.example.socialnetwork.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.imageAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

    }

    private var imageUrl : Uri?= null

    private fun register() {
        val emailAddress = binding.editTextEmailR.text.toString().trim()
        val passWord = binding.editTextPassR.text.toString()
        Log.d("RegisterActivity", "Email is$emailAddress")
        Log.d("RegisterActivity", "Pass is$passWord")
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailAddress,passWord)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener
                upLoadImageFirebase()
            }
        Log.d("MainActivity","$imageUrl")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            imageUrl = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageUrl)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageAvatar.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun upLoadImageFirebase () {
        if(imageUrl==null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(imageUrl!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity","$it")
                    saveUser(it.toString())
                    Toast.makeText(this,"Tạo tài khoản thành công",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUser(imageUrl : String) {
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid,imageUrl,binding.editTextUser.text.toString())
        ref.setValue(user)
            .addOnCompleteListener{
            }
    }
}