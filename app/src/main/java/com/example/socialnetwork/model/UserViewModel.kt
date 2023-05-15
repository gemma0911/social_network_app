package com.example.socialnetwork.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialnetwork.repository.UserRepository
class UserViewModel() : ViewModel() {

    private val repository : UserRepository = UserRepository().getInstance()
    private val _allUsers = MutableLiveData<List<User>>()
    val allUsers : LiveData<List<User>> = _allUsers


    private val _allUsers1 = MutableLiveData<List<User>>()
    val allUsers1 : LiveData<List<User>> = _allUsers1
    init {
        repository.loadUsers(_allUsers)
        repository.loadUsers(_allUsers1)
    }
}