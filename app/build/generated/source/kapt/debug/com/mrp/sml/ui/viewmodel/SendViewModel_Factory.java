package com.mrp.sml.ui.viewmodel;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SendViewModel_Factory implements Factory<SendViewModel> {
  private final Provider<Context> contextProvider;

  private SendViewModel_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SendViewModel get() {
    return newInstance(contextProvider.get());
  }

  public static SendViewModel_Factory create(Provider<Context> contextProvider) {
    return new SendViewModel_Factory(contextProvider);
  }

  public static SendViewModel newInstance(Context context) {
    return new SendViewModel(context);
  }
}
