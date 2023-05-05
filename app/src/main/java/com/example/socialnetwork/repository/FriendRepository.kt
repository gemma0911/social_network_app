package com.example.socialnetwork.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.socialnetwork.model.FriendModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FriendRepository() {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference
    @Volatile private var INSTANCE : FriendRepository?= null

    fun getInstance() : FriendRepository {
        return INSTANCE ?: synchronized(this){
            val instance = FriendRepository()
            INSTANCE = instance
            instance
        }
    }
    fun loadFriend(friendList : MutableLiveData<List<FriendModel>>){
        var query = databaseReference.child("friend")
            .child(FirebaseAuth.getInstance().currentUser.uid)
            .orderByChild("statusFriend")
            .equalTo("2")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _Friend : List<FriendModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(FriendModel::class.java)!!
                    }
                    friendList.postValue(_Friend)
                    Log.d(TAG,"-----------------$_Friend")
                }catch (e : Exception){

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun loadFriend1(friendList : MutableLiveData<List<FriendModel>>){
        var query = databaseReference.child("friend")
            .child(FirebaseAuth.getInstance().currentUser.uid)
            .orderByChild("statusFriend")
            .equalTo("1")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _Friend : List<FriendModel> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(FriendModel::class.java)!!
                    }
                    friendList.postValue(_Friend)
                    Log.d(TAG,"-----------------$_Friend")
                }catch (e : Exception){

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}