package com.example.chatapp.ui.register_activity.home_activity

import android.database.Observable
import android.util.Log
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel:ViewModel() {
    var navigator:Navigator?=null
fun ToAddRoomScreen(){
navigator!!.navigateToAddRoom()
}

    }
