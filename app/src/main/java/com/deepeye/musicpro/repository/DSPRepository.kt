package com.deepeye.musicpro.repository

import com.deepeye.musicpro.db.AppDatabase
import com.deepeye.musicpro.dsp.DSPManager
import com.deepeye.musicpro.dsp.DSPPreset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DSPRepository(
    private val database: AppDatabase,
    val dspManager: DSPManager
) {
    fun observePresets(): Flow<List<DSPPreset>> {
        return database.presetDao().observePresets().map { rows ->
            if (rows.isEmpty()) DSPPreset.BuiltIns else rows.map(DSPPreset::fromEntity)
        }
    }

    suspend fun seedBuiltIns() {
        database.presetDao().upsertAll(DSPPreset.BuiltIns.map { it.toEntity() })
    }

    suspend fun saveCustom(preset: DSPPreset) {
        database.presetDao().upsert(preset.copy(builtIn = false).toEntity())
    }

    suspend fun applyPreset(id: String) {
        val entity = database.presetDao().getById(id)
        val preset = entity?.let(DSPPreset::fromEntity) ?: DSPPreset.BuiltIns.firstOrNull { it.id == id } ?: DSPPreset.Flat
        dspManager.applyPreset(preset)
    }
}
