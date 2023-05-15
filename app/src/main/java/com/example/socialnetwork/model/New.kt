package com.example.socialnetwork.model

data class New(
    var uri : String ?= null,
    var user : String ?= null,
    var imageUrl : String ?= null,
    var status : String ?= null,
    var newMess : String ?= null,
    var newTime : String ?= null
)
