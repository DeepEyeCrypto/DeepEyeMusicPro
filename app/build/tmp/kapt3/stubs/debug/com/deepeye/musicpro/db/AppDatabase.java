package com.deepeye.musicpro.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u000f"}, d2 = {"Lcom/deepeye/musicpro/db/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "favoriteDao", "Lcom/deepeye/musicpro/db/FavoriteDao;", "playlistDao", "Lcom/deepeye/musicpro/db/PlaylistDao;", "presetDao", "Lcom/deepeye/musicpro/db/PresetDao;", "searchHistoryDao", "Lcom/deepeye/musicpro/db/SearchHistoryDao;", "trackDao", "Lcom/deepeye/musicpro/db/TrackDao;", "v4aPresetDao", "Lcom/deepeye/musicpro/db/V4APresetDao;", "app_debug"})
@androidx.room.Database(entities = {com.deepeye.musicpro.db.CachedTrack.class, com.deepeye.musicpro.db.FavoriteTrack.class, com.deepeye.musicpro.db.SearchHistoryEntry.class, com.deepeye.musicpro.db.DspPresetEntity.class, com.deepeye.musicpro.db.V4APresetEntity.class, com.deepeye.musicpro.db.PlaylistEntity.class, com.deepeye.musicpro.db.PlaylistTrackCrossRef.class}, version = 3, exportSchema = true)
@androidx.room.TypeConverters(value = {com.deepeye.musicpro.db.Converters.class})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.deepeye.musicpro.db.TrackDao trackDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.deepeye.musicpro.db.FavoriteDao favoriteDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.deepeye.musicpro.db.SearchHistoryDao searchHistoryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.deepeye.musicpro.db.PresetDao presetDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.deepeye.musicpro.db.V4APresetDao v4aPresetDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.deepeye.musicpro.db.PlaylistDao playlistDao();
}