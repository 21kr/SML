package com.mrp.sml.domain.usecase.settings;

import com.mrp.sml.data.local.preferences.SettingsManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class SaveSettingsUseCase_Factory implements Factory<SaveSettingsUseCase> {
  private final Provider<SettingsManager> settingsManagerProvider;

  private SaveSettingsUseCase_Factory(Provider<SettingsManager> settingsManagerProvider) {
    this.settingsManagerProvider = settingsManagerProvider;
  }

  @Override
  public SaveSettingsUseCase get() {
    return newInstance(settingsManagerProvider.get());
  }

  public static SaveSettingsUseCase_Factory create(
      Provider<SettingsManager> settingsManagerProvider) {
    return new SaveSettingsUseCase_Factory(settingsManagerProvider);
  }

  public static SaveSettingsUseCase newInstance(SettingsManager settingsManager) {
    return new SaveSettingsUseCase(settingsManager);
  }
}
