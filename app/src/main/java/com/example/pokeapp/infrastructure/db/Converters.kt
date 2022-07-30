package com.example.pokeapp.infrastructure.db

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromUriToString(uri: Uri): String {
        return uri.toString()
    }
    @TypeConverter
    fun fromStringToUri(string: String): Uri {
        return Uri.parse(string)
    }

    @TypeConverter
    fun fromStringListToString(list: List<String>): String {
        return list.joinToString()
    }

    @TypeConverter
    fun fromStringToStringList(string: String): List<String> {
        return string.split(", ")
    }
}