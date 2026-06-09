package com.mrp.sml.domain.usecase.settings

import com.mrp.sml.data.local.preferences.SettingsManager
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsManager: SettingsManager
) {
    suspend fun saveDeviceName(name: String) {
        settingsManager.setDeviceName(name)
    }

    suspend fun saveSaveHistory(enabled: Boolean) {
        settingsManager.setSaveHistory(enabled)
    }

    suspend fun saveDarkMode(enabled: Boolean) {
        settingsManager.setDarkMode(enabled)
    }

    suspend fun saveChunkSize(size: Int) {
        settingsManager.setChunkSize(size)
    }

    suspend fun saveThemeColor(color: String) {
        settingsManager.setThemeColor(color)
    }
}
