package com.example.hotspot.model

data class User(
    var name: String,
    var age: Int,
    var emailAddress: String,
    var userName: String,
    var password: String,
    var bio: String? = null,
    val gender: String,


     // TO-DO ....
    )
