package com.tegas.instant_messenger_mobile.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tegas.instant_messenger_mobile.data.retrofit.ApiService
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem
import kotlinx.coroutines.Dispatchers

class ChatRepository(
    private val apiService: ApiService
) {
    fun getChatList(nim: String): LiveData<Result<List<ChatsItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getChatList(nim)
                val teachers = response.chats
                emit(Result.Success(teachers))
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