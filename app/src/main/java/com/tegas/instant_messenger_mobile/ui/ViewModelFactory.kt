package com.tegas.instant_messenger_mobile.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tegas.instant_messenger_mobile.data.ChatRepository
import com.tegas.instant_messenger_mobile.ui.Injection.provideRepository
import com.tegas.instant_messenger_mobile.ui.main.MainViewModel

class ViewModelFactory(private val repository: ChatRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}