package com.tegas.instant_messenger_mobile.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonParser
import com.tegas.instant_messenger_mobile.data.Result
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem
import com.tegas.instant_messenger_mobile.databinding.ActivityMainBinding
import com.tegas.instant_messenger_mobile.ui.ViewModelFactory
import com.tegas.instant_messenger_mobile.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        MainAdapter {
            Intent(this, DetailActivity::class.java).apply {
//                putExtra("chat", it)
                startActivity(this)
            }
        }
    }
//    private lateinit var chatAdapter: ChatAdapter
    private val chatList = mutableListOf<ChatsItem?>()
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var originalChatList: List<ChatsItem?>

    private val nim = """{"nim": "21106050048"}"""
    val nimJson = JsonParser.parseString(nim).asJsonObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(false)
        binding.rvUser.adapter = adapter


        viewModel.getChatList(nimJson)

        getChatList()


    }

    private fun getChatList() {
        viewModel.mainViewModel.observe(this) {
            when (it) {
                is Result.Loading -> {
                    Log.d("Result", "Loading")
                }

                is Result.Error -> {
                    Log.d("Result", "Error")
                    Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
                }

                is Result.Success -> {
                    Log.d("Result", "Success")
//                    it.data.let { chatListResponse ->
//                        originalChatList = chatListResponse.chats ?: emptyList()
//                        adapter.setData(originalChatList)
//                        chatList.clear()
//                        chatList.addAll(chatListResponse)
//                        chatAdapter.notifyDataSetChanged()
//                    }
//                    chatList.clear()
//                    chatList.ad
                }
            }
        }
    }
}