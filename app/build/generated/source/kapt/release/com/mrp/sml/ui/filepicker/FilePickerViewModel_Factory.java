package com.mrp.sml.ui.filepicker;

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
    "KotlinInternalInJava"
})
public final class FilePickerViewModel_Factory implements Factory<FilePickerViewModel> {
  @Override
  public FilePickerViewModel get() {
    return newInstance();
  }

  public static FilePickerViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FilePickerViewModel newInstance() {
    return new FilePickerViewModel();
  }

  private static final class InstanceHolder {
    private static final FilePickerViewModel_Factory INSTANCE = new FilePickerViewModel_Factory();
  }
}
