package com.deepeye.musicpro.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tracks")
data class FavoriteTrack(
    @PrimaryKey val id: String,
    val title: String,
    val artist: String,
    val thumbnailUrl: String?,
    val webUrl: String?,
    val addedAt: Long = System.currentTimeMillis()
)
