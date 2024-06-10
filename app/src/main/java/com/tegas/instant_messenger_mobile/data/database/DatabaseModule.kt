package com.tegas.instant_messenger_mobile.data.database

import android.content.Context
import androidx.room.Room

class DatabaseModule(private val ctx: Context) {
    private val db = Room.databaseBuilder(ctx, ChatDatabase::class.java, "chats.db")
        .allowMainThreadQueries()
        .build()

    val chatDao = db.chatDao()
}