package com.example.socialnetwork.model

data class FriendModel(
    var uri : String ?= null,
    var urlImage : String ?= null,
    var user : String ?= null,
    var status : String ?= null,
    var statusFriend : String ?= null
)
