package com.example.hotspot.model

class ChatMessage(val toFrom: String?, val text: String, val fromId: String?, val toId: String?, val timestamp: Long) {
    constructor() : this("","","","",-1)
}