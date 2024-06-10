package com.tegas.instant_messenger_mobile.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    fun fromAny(value: Any?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toAny(value: String): Any? {
        val type = object : TypeToken<Any>() {}.type
        return Gson().fromJson(value, type)
    }
}