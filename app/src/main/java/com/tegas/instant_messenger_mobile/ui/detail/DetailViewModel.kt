package com.tegas.instant_messenger_mobile.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tegas.instant_messenger_mobile.data.database.DatabaseModule
import com.tegas.instant_messenger_mobile.data.retrofit.ApiConfig
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val db: DatabaseModule) : ViewModel() {

    private val _chatDetail = MutableLiveData<ChatDetailResponse>()
    val chatDetail: LiveData<ChatDetailResponse> = _chatDetail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getChatDetail(chatId: String?) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val client = ApiConfig.apiService().getChatDetail(chatId!!)
            client.enqueue(object : Callback<ChatDetailResponse> {
                override fun onResponse(
                    call: Call<ChatDetailResponse>,
                    response: Response<ChatDetailResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _chatDetail.value = response.body()
                    } else {
                        val error = "Error: ${response.message()}"
                        _errorMessage.postValue(error)
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ChatDetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(ContentValues.TAG, "onFailuer: ${t.message.toString()}")
                    val error = "Failure: ${t.message.toString()}"
                    _errorMessage.postValue(error)
                }
            })
        }
    }

    class Factory(private val db: DatabaseModule): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}