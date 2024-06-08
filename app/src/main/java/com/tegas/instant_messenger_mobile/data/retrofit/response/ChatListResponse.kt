package com.tegas.instant_messenger_mobile.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class ChatListResponse(

	@field:SerializedName("chats")
	val chats: List<ChatsItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class ChatsItem(

	@field:SerializedName("chatId")
	val chatId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("lastMessage")
	val lastMessage: String? = null,

	@field:SerializedName("lastMessageTime")
	val lastMessageTime: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("participants")
	val participants: List<String?>? = null,

	@field:SerializedName("chatType")
	val chatType: String? = null
)
