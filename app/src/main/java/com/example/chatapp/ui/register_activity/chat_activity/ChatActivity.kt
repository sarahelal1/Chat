package com.example.chatapp.ui.register_activity.chat_activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.ui.register_activity.BaseActivity
import com.example.chatapp.ui.register_activity.add_room.AddRoomActivity
import com.example.chatapp.ui.register_activity.firebase.getAllRooms
import com.example.chatapp.ui.register_activity.home_activity.RoomsAdapter
import com.example.chatapp.ui.register_activity.model.Message
import com.example.chatapp.ui.register_activity.model.Room
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ChatActivity : BaseActivity<ChatViewModel, ActivityChatBinding>() {
    var messagesList = mutableListOf<Message>()
    val adapter: MessageAdapter = MessageAdapter(messagesList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        val roomId = intent.getStringExtra("roomId")
        viewModel.roomId = roomId ?: ""
        initViews()
        subscribeToLiveData()
        subscribeToMessagesCollection()
    }

    private fun subscribeToLiveData() {
        viewModel.isLoadingLiveData.observe(this) {
            if (it) {
                showLoading()

            } else {
                hideLoading()
            }
        }
        viewModel.messageLiveData.observe(this) {
            showError(title = "Error", message = it)
        }


    }

    fun subscribeToMessagesCollection() {
        val firestore = Firebase.firestore
        firestore.collection(Room.COLLECTION_NAME).document(viewModel.roomId)
            .collection(Message.COLLECTION_NAME).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("subscribeToMessages", "Listen failed.", e)
                    showError("Error", "Please try again later")
                    return@addSnapshotListener

                }

                if (snapshot != null) {
                    for (docChange in snapshot.documentChanges) {
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                val message = docChange.document.toObject(Message::class.java)
                                Log.e("subscribeToMessages", "adding message to list with content  ${message.content}"

                                )
                                messagesList.add(message)
                            }

                            else -> {

                            }
                        }
                    }

                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("subscribeToMessages", "Current data: null")
                }
            }

    }

    fun initViews() {
        viewDataBinding.messagesRecyclerview.layoutManager=LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)
        viewDataBinding.messagesRecyclerview.adapter=adapter

    }





    override fun getLayoutFile(): Int {
        return R.layout.activity_chat
    }

    override fun initViewModel(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }




}