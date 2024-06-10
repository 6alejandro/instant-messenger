package com.tegas.instant_messenger_mobile.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem
import com.tegas.instant_messenger_mobile.databinding.ItemContactBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ChatAdapter(
    private val data: MutableList<ChatsItem> = mutableListOf(),
    private val listener: (ChatsItem) -> Unit
) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

fun setData(data: MutableList<ChatsItem>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
}
    class ChatViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatsItem) {
            binding.tvName.text = item.name
            binding.tvMessage.text = item.lastMessage
            binding.tvTime.text = formatDateTime(item.lastMessageTime)
        }

        private fun formatDateTime(timestamp: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = inputFormat.parse(timestamp)
            return outputFormat.format(date)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item =data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }


}