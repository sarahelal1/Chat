package com.example.chatapp.ui.register_activity.chat_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.R
import com.example.chatapp.databinding.ItemMessageReceiveBinding
import com.example.chatapp.databinding.ItemMessageSentBinding
import com.example.chatapp.ui.register_activity.Data
import com.example.chatapp.ui.register_activity.model.Message

class MessageAdapter(var items:MutableList<Message>):RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        val message=items.get(position)
        if(message.senderId== Data.currentUser!!.id){
            return 0
        }else{
            return 1
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.MessageViewHolder {
       if(viewType==0){
           val binding:ItemMessageSentBinding=DataBindingUtil.inflate(
               LayoutInflater.from(parent.context),
               R.layout.item_message_sent,
               parent,false
           )
           return SentMessageViewHolder(binding)
       }else{
           val binding:ItemMessageReceiveBinding=DataBindingUtil.inflate(
               LayoutInflater.from(parent.context),
               R.layout.item_message_receive,
               parent,false
           )
           return ReceivedMessageViewHolder(binding)
       }
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        val message=items.get(position)
        holder.bind(message)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(message: Message)
    }

    class SentMessageViewHolder(val binding: ItemMessageSentBinding) :
        MessageViewHolder(binding.root) {
        override fun bind(message: Message) {
            binding.message = message

        }
    }

        class ReceivedMessageViewHolder(val binding: ItemMessageReceiveBinding) :
            MessageViewHolder(binding.root) {
            override fun bind(message: Message) {
                binding.message = message

            }


        }
    }



