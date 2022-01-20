package com.example.hotspot.model

import java.sql.Timestamp

data class Message(
    private val userProfile: UserProfile,
    private val fromId: String? = null,
    private val toId: String? = null,
    private val ID: String? = null,
    private val timestamp: Timestamp? = null
)
