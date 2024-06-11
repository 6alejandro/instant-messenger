package com.tegas.instant_messenger_mobile.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.tegas.instant_messenger_mobile.R
import com.tegas.instant_messenger_mobile.data.Result
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem
import com.tegas.instant_messenger_mobile.data.retrofit.response.MessagesItem
import com.tegas.instant_messenger_mobile.databinding.ActivityDetailSecondBinding
import com.tegas.instant_messenger_mobile.ui.ViewModelFactory
import com.tegas.instant_messenger_mobile.ui.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSecondBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var chat: MessagesItem

    //    private var chatId: String? = null
    private lateinit var adapter: MessageAdapter
    private val nim = "21106050048"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<ChatsItem>("item")
        val chatId = item?.chatId
        Log.d("CHAT ID", "CHAT ID: $chatId")
        binding.tvName.text = item?.name
        Glide
            .with(this)
            .load(
                R.drawable
                    .daniela_villarreal
            )
            .into(binding.ivImage)

        setRecyclerView(nim)
        viewModel.getChatDetails(chatId!!)
        fetchData()
        getSession(chatId)
        setupSend()

    }

    private fun getSession(chatId: String) {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            val senderId = user.nim
            val content = binding.editText.text

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
            val currentTimeString = dateFormat.format(Date())

            val jSon = JsonObject().apply {
                addProperty("chatId", chatId)
                addProperty("senderId", senderId)
                addProperty("content", content.toString())
                addProperty("sentAt", currentTimeString)
                addProperty("attachment", "null")
            }

            binding.iconSend.setOnClickListener {
                viewModel.sendMessage(jSon)
            }

        }
    }

    private fun setupSend(){
        viewModel.sendMessage.observe(this) {
            when(it) {
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.editText.text?.clear()
                    Toast.makeText(this, "$it.data.messages, status: ${it.data.status}", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun fetchData() {
        viewModel.detailViewModel.observe(this) {
            when (it) {
                is Result.Error -> {
                    Log.d("Result", "Error")
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error: ${it.error}", Toast.LENGTH_SHORT).show()
                    Log.d("ERROR ACTIVITY", "Error: ${it.error}")
                }

                is Result.Success -> {
                    Log.d("Result", "Success")
                    binding.progressBar.visibility = View.GONE
                    adapter.setData(it.data as MutableList<MessagesItem>)
                }

                else -> {
                    Log.d("Result", "Loading")
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        binding.backButton.setOnClickListener {
//            intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            onBackPressed()
        }
    }

    private fun setRecyclerView(nim: String) {
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.setHasFixedSize(true)
        adapter = MessageAdapter(nim)
        binding.rvChat.adapter = adapter
    }
}