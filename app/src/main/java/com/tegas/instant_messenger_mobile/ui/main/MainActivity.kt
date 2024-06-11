package com.tegas.instant_messenger_mobile.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tegas.instant_messenger_mobile.data.Result
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem
import com.tegas.instant_messenger_mobile.databinding.ActivityMainSecondBinding
import com.tegas.instant_messenger_mobile.ui.ViewModelFactory
import com.tegas.instant_messenger_mobile.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainSecondBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val adapter by lazy {
        ChatAdapter {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", it)
                startActivity(this)
            }
        }
    }
    private val nim = "21106050048"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        viewModel.getChatList(nim)
        fetchData()
    }

    private fun setRecyclerView() {
        binding.rvUser.layoutManager =LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
    }

    private fun fetchData() {
        viewModel.mainViewModel.observe(this) {
            when (it) {
                is Result.Loading -> {
                    Log.d("Result", "Loading")
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    Log.d("Result", "Error")
                    binding.progressBar.visibility = View.GONE
                }

                is Result.Success -> {
                    Log.d("Result", "Success")
                    binding.progressBar.visibility = View.GONE
                    adapter.setData(it.data as MutableList<ChatsItem>)
                }
            }
        }
    }
}