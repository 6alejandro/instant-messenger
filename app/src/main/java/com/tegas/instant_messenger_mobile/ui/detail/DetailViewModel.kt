package com.tegas.instant_messenger_mobile.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.gson.JsonObject
import com.tegas.instant_messenger_mobile.data.ChatRepository
import com.tegas.instant_messenger_mobile.data.Result
import com.tegas.instant_messenger_mobile.data.UserModel
import com.tegas.instant_messenger_mobile.data.retrofit.response.MessagesItem
import com.tegas.instant_messenger_mobile.data.retrofit.response.SendResponse

class DetailViewModel(private val repository: ChatRepository) : ViewModel() {

//    private val _chatDetail = MutableLiveData<ChatDetailResponse>()
//    val chatDetail: LiveData<ChatDetailResponse> = _chatDetail
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> get() = _errorMessage
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    fun getChatDetail(chatId: String?) {
//        _isLoading.value = true
//        viewModelScope.launch(Dispatchers.IO) {
//            val client = ApiConfig.apiService().getChatDetail(chatId!!)
//            client.enqueue(object : Callback<ChatDetailResponse> {
//                override fun onResponse(
//                    call: Call<ChatDetailResponse>,
//                    response: Response<ChatDetailResponse>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _chatDetail.value = response.body()
//                    } else {
//                        val error = "Error: ${response.message()}"
//                        _errorMessage.postValue(error)
//                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<ChatDetailResponse>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.e(ContentValues.TAG, "onFailuer: ${t.message.toString()}")
//                    val error = "Failure: ${t.message.toString()}"
//                    _errorMessage.postValue(error)
//                }
//            })
//        }
//    }

    private val _detailViewModel = MediatorLiveData<Result<List<MessagesItem>>>()
    val detailViewModel: LiveData<Result<List<MessagesItem>>> = _detailViewModel

    private val _sendMessage = MediatorLiveData<Result<SendResponse>>()
    val sendMessage: LiveData<Result<SendResponse>> = _sendMessage

    fun getChatDetails(chatId: String) {
        Log.d("DETAIL VIEW MODEL","DetailViewModel Chat ID: $chatId")
        val liveData = repository.getChatDetails(chatId)
        _detailViewModel.addSource(liveData) { result ->
            _detailViewModel.value = result
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun sendMessage(message: JsonObject) {
        val liveData = repository.sendMessage(message)
        _sendMessage.addSource(liveData) { result ->
            _sendMessage.value = result
        }
    }

    class Factory(private val repository: ChatRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            DetailViewModel(repository) as T
    }
}