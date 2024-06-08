package com.tegas.instant_messenger_mobile.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.JsonObject
import com.tegas.instant_messenger_mobile.data.retrofit.ApiService
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatListResponse
import kotlinx.coroutines.Dispatchers

class ChatRepository(
    private val apiService: ApiService
) {
    fun getChatList(nim: JsonObject): LiveData<Result<ChatListResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getChatList(nim)
                val chat = response.chats
                val error = response.error
                Log.d("Response", "Response: $chat")
                Log.d("Error", "Error: $error")
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        @Volatile
        private var instance: ChatRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ChatRepository =
            instance ?: synchronized(this) {
                instance ?: ChatRepository(apiService)
            }.also { instance = it }
    }
}