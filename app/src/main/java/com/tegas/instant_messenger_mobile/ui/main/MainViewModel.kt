package com.tegas.instant_messenger_mobile.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.tegas.instant_messenger_mobile.data.ChatRepository
import com.tegas.instant_messenger_mobile.data.Result
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatListResponse

class MainViewModel(private val repository: ChatRepository) : ViewModel() {

    private val _mainViewModel = MediatorLiveData<Result<ChatListResponse>>()
    val mainViewModel: LiveData<Result<ChatListResponse>> = _mainViewModel

    fun getChatList(nim: JsonObject) {
        val liveData = repository.getChatList(nim)
        _mainViewModel.addSource(liveData) { result ->
            _mainViewModel.value = result
        }
    }

    class Factory(private val repository: ChatRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(repository) as T

    }
}