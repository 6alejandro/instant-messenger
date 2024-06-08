package com.tegas.instant_messenger_mobile.ui

import android.content.Context
import com.tegas.instant_messenger_mobile.data.ChatRepository
import com.tegas.instant_messenger_mobile.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): ChatRepository {
        val apiService = ApiConfig.apiService()
        return ChatRepository.getInstance(apiService)
    }
}