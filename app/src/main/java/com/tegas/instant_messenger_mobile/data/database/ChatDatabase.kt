package com.tegas.instant_messenger_mobile.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem

@Database(entities = [ChatsItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao

    companion object {
        private const val DATABASE_NAME = "chats.db"

        @Volatile
        private var instance: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ChatDatabase {
            return Room.databaseBuilder(context, ChatDatabase::class.java, DATABASE_NAME)
                .addMigrations(MigrationDb())
                .build()

        }
    }
}