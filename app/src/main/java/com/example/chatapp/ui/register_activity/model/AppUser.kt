package com.example.chatapp.ui.register_activity.model

data class AppUser(
    var id:String?="",
    var firstName:String?="",
    var lastName:String?="",
    var email:String?=""
){
    companion object{
       val COLLECTION_NAME="users"
    }
}
