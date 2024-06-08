package com.tegas.instant_messenger_mobile.data.retrofit

import com.google.gson.JsonObject
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatListResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("chatList")
    suspend fun getChatList(
    @Body nim: JsonObject
    ): ChatListResponse
}