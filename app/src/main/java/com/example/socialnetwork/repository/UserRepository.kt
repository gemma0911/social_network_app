package com.example.socialnetwork.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.socialnetwork.model.User
import com.google.firebase.database.*
import com.example.socialnetwork.MainActivity.Companion.KEY_USER
class UserRepository() {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference
    @Volatile private var INSTANCE : UserRepository?= null

    fun getInstance() : UserRepository {
        return INSTANCE ?: synchronized(this){
            val instance = UserRepository()
            INSTANCE = instance
            instance
        }
    }
    fun loadUsers(userList : MutableLiveData<List<User>>){
        var query = databaseReference.child("users")
//            .orderByChild("uri").equalTo(KEY_USER)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _userList : List<User> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(User::class.java)!!
                    }
                    userList.postValue(_userList)
                    Log.d(TAG,"$_userList")
                }catch (e : Exception){

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}