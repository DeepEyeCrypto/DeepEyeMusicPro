package com.deepeye.musicpro.repository

import com.deepeye.musicpro.db.AppDatabase

class AppRepository(
    val database: AppDatabase,
    val settingsRepository: SettingsRepository,
    val playerRepository: PlayerRepository,
    val searchRepository: SearchRepository,
    val downloadRepository: DownloadRepository,
    val dspRepository: DSPRepository
) {
    suspend fun warmUp() {
        dspRepository.seedBuiltIns()
    }
}
