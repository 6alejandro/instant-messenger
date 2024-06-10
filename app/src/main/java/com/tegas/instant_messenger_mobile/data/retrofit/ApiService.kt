package com.tegas.instant_messenger_mobile.data.retrofit

import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatDetailResponse
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("chatList")
    suspend fun getChatList(
        @Query("nim") nim: String
    ): ChatListResponse

    @GET("chatDetail")
    fun getChatDetail(
        @Query("chatId") chatId: String
    ): Call<ChatDetailResponse>
}