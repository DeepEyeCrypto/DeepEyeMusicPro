package com.deepeye.musicpro.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CachedTrack::class,
        FavoriteTrack::class,
        SearchHistoryEntry::class,
        DspPresetEntity::class,
        V4APresetEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun presetDao(): PresetDao
    abstract fun v4aPresetDao(): V4APresetDao
    abstract fun playlistDao(): PlaylistDao
}
