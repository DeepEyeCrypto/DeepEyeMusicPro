package com.deepeye.musicpro.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {
    @Query("SELECT * FROM dsp_presets ORDER BY builtIn DESC, name ASC")
    fun observePresets(): Flow<List<DspPresetEntity>>

    @Query("SELECT * FROM dsp_presets WHERE id = :id")
    suspend fun getById(id: String): DspPresetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(preset: DspPresetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(presets: List<DspPresetEntity>)

    @Query("DELETE FROM dsp_presets WHERE id = :id AND builtIn = 0")
    suspend fun deleteCustom(id: String)
}
