package com.example.chatapp.ui.register_activity.chat_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.register_activity.Data
import com.example.chatapp.ui.register_activity.firebase.addMessageToFireStore
import com.example.chatapp.ui.register_activity.model.AppUser
import com.example.chatapp.ui.register_activity.model.Message
import com.google.firebase.Timestamp

class ChatViewModel:ViewModel() {
    val messageLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val messageContent=MutableLiveData<String>()
    var roomId:String=""
fun sendMessage(){
    if(messageContent.value.isNullOrBlank()) return
val message=Message(
     senderId = Data.currentUser!!.id!!,
     content=messageContent.value!!,
    senderName = Data.currentUser!!.firstName!!,
    time = Timestamp.now()
)
    addMessageToFireStore(
        roomId = roomId,
        message,{
          messageContent.value=""
        },
        {
            messageLiveData.value=it.message
        }
    )
}

    }
