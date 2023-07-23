package com.example.chatapp.ui.register_activity

import com.google.firebase.Timestamp

fun Timestamp.format():String{
    return "@{this.}"
}