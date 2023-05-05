package com.example.socialnetwork.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialnetwork.repository.FriendRepository
class FriendRequest() : ViewModel() {

    private val repository : FriendRepository = FriendRepository().getInstance()
    private val _allFriend = MutableLiveData<List<FriendModel>>()
    val allFriend : LiveData<List<FriendModel>> = _allFriend

    init {
        repository.loadFriend1(_allFriend)
    }
}