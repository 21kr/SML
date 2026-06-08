package com.mrp.sml.domain.usecase.settings

import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor() {
    operator fun invoke(key: String, value: String) {
        // Delegated to SettingsManager in data layer
    }
}
