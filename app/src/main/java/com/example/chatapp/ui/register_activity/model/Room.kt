package com.example.chatapp.ui.register_activity.model

data class Room(
    var id:String?="",
    val title:String="",
    val description:String="",
    val categoryId:String=""
){
    companion object{
       val COLLECTION_NAME="rooms"
    }
}
