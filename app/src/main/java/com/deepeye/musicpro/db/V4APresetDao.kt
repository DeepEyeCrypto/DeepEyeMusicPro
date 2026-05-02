package com.deepeye.musicpro.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface V4APresetDao {
    @Query("SELECT * FROM v4a_presets ORDER BY isBuiltIn DESC, name ASC")
    fun observePresets(): Flow<List<V4APresetEntity>>

    @Query("SELECT * FROM v4a_presets WHERE id = :id")
    suspend fun getById(id: String): V4APresetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(preset: V4APresetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(presets: List<V4APresetEntity>)
}
