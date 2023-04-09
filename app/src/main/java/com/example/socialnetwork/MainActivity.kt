package com.example.socialnetwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.socialnetwork.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    companion object {
        var KEY_USER= ""
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    var intent = Intent(this,ListFriendActivity::class.java)
                    KEY_USER = FirebaseAuth.getInstance().uid!!
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,"Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                }
            }
    }
}