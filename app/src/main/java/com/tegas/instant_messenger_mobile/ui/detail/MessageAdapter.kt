package com.tegas.instant_messenger_mobile.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tegas.instant_messenger_mobile.data.retrofit.response.MessagesItem
import com.tegas.instant_messenger_mobile.databinding.ItemChatBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(
    private val nim: String,
    private val data: MutableList<MessagesItem> = mutableListOf(),
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    fun setData(data: MutableList<MessagesItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessagesItem) {
            if (item.senderId != nim) {
                binding.layoutSent.itemSent.visibility = View.GONE
                binding.layoutReceived.chatReceived.text = item.content
                binding.layoutReceived.timeReceived.text = formatDateTime(item.sentAt)
            } else {
                binding.layoutReceived.itemReceive.visibility = View.GONE
                binding.layoutSent.chatSent.text = item.content
                binding.layoutSent.timeSent.text = formatDateTime(item.sentAt)
            }
        }

        private fun formatDateTime(timestamp: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = inputFormat.parse(timestamp)
            return outputFormat.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            (ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}