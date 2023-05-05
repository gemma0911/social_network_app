package com.example.socialnetwork

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.socialnetwork.fragment.Friend
import com.example.socialnetwork.fragment.Home
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class IndexActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        replaceFragment(Home())
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener(
            NavigationBarView.OnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.friend -> replaceFragment(Friend())
            }
                true
        })
    }
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout,fragment)
        fragmentTransaction.commit()
    }
}