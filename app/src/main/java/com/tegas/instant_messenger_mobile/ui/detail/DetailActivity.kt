package com.tegas.instant_messenger_mobile.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tegas.instant_messenger_mobile.data.database.DatabaseModule
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem
import com.tegas.instant_messenger_mobile.data.retrofit.response.MessagesItem
import com.tegas.instant_messenger_mobile.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(DatabaseModule(this))
    }

    private lateinit var chat: MessagesItem
//    private var chatId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<ChatsItem>("item")
        Log.d("CHAT ID", "CHAT ID: ${item?.chatId}")
        binding.tvName.text = item?.name

        viewModel.getChatDetail(item?.chatId)

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvChat.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvChat.visibility = View.VISIBLE
            }
        }

        viewModel.chatDetail.observe(this) { detailResponse ->

        }
    }
}