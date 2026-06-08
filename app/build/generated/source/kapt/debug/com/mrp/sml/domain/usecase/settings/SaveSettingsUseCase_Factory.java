package com.mrp.sml.domain.usecase.settings;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
  @Override
  public SaveSettingsUseCase get() {
    return newInstance();
  }

  public static SaveSettingsUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SaveSettingsUseCase newInstance() {
    return new SaveSettingsUseCase();
  }

  private static final class InstanceHolder {
    private static final SaveSettingsUseCase_Factory INSTANCE = new SaveSettingsUseCase_Factory();
  }
}
