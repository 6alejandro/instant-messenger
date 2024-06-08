package com.tegas.instant_messenger_mobile.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tegas.instant_messenger_mobile.R
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem

class ChatAdapter(private val chatList: List<ChatsItem?>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatName: TextView = itemView.findViewById(R.id.tv_name)
        val lastMessage: TextView = itemView.findViewById(R.id.tv_message)
        val lastMessageTime: TextView = itemView.findViewById(R.id.tv_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.chatName.text = chat?.chatId ?: "No Name" // Display chatId or modify as needed
        holder.lastMessage.text = chat?.lastMessage ?: "No Message"
        holder.lastMessageTime.text = chat?.lastMessageTime ?: "No Time"
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}