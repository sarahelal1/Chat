package com.example.chatapp.ui.register_activity.home_activity

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.ui.register_activity.BaseActivity
import com.example.chatapp.ui.register_activity.RegisterActivity
import com.example.chatapp.ui.register_activity.RegisterViewModel
import com.example.chatapp.ui.register_activity.add_room.AddRoomActivity
import com.example.chatapp.ui.register_activity.chat_activity.ChatActivity
import com.example.chatapp.ui.register_activity.firebase.getAllRooms
import com.example.chatapp.ui.register_activity.model.Room

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(), Navigator {
    var roomList = mutableListOf<Room>()
    lateinit var adapter: RoomsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        viewDataBinding.vm = viewModel
        initViews()


    }

    fun initViews() {
        adapter = RoomsAdapter(roomList)
        adapter.onRoomClick=object :RoomsAdapter.OnRoomClick{
            override fun onItemClick(pos: Int, room: Room) {
                val intent=Intent(baseContext,ChatActivity::class.java)
                intent.putExtra("roomId",room.id)
                startActivity(intent)
            }

        }
        viewDataBinding.roomsRecyclerView.adapter = adapter
        viewDataBinding.roomsRecyclerView.layoutManager = GridLayoutManager(
            this, 2,
            RecyclerView.VERTICAL, false
        )
    }

    fun getRooms() {
        showLoading()
        getAllRooms({
            hideLoading()
            it.documents.forEach { doc ->
                val room = doc.toObject(Room::class.java)
                roomList.add(room!!)
            }
            adapter.notifyDataSetChanged()
        }, {
            hideLoading()
            showError(title = "Error", message = it.message ?: "Error while loading data")
        })
    }

    override fun onResume() {
        super.onResume()
        getRooms()
    }

    override fun getLayoutFile(): Int {
        return R.layout.activity_home
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }


}