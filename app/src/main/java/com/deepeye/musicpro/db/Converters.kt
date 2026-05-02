package com.deepeye.musicpro.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun floatsToString(values: List<Float>): String = values.joinToString(",")

    @TypeConverter
    fun stringToFloats(value: String): List<Float> {
        if (value.isBlank()) return emptyList()
        return value.split(',').mapNotNull { it.toFloatOrNull() }
    }
}
