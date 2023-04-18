package com.example.socialnetwork

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.socialnetwork.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    companion object {
        var KEY_USER= ""
        var EMAIL = "email"
        var PASS = "pass"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    var emailE = ""
    var passE = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var sharePrefs : SharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharePrefs.edit()

        emailE = sharePrefs.getString(EMAIL,"").toString()
        passE = sharePrefs.getString(PASS,"").toString()

        Log.d(TAG,"$emailE")
        Log.d(TAG,"$passE")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email : String = binding.editTextEmail.text.toString()
            val pass : String = binding.editTextPass.text.toString()
            signInWithEmailPassword(email,pass)
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this) {
                if(it.isSuccessful) {
                    editor.putString(EMAIL,email)
                    editor.putString(PASS, pass)
                    editor.apply()
                }
            }
        }

    }
    private fun signInWithEmailPassword(email : String,pass : String) {

        if( email.isEmpty() || pass.isEmpty() ) {
            Toast.makeText(this, "Chưa nhập tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show()
            return
        }

        if( pass.length < 6 ){
            Toast.makeText(this, "Mật khẩu phải có 6 chữ số", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener (this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this,IndexActivity::class.java)
                    KEY_USER = FirebaseAuth.getInstance().uid!!
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,"Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if(emailE != "" && passE != "") {
            signInWithEmailPassword(emailE,passE)
        }
    }
}