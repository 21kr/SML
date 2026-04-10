package com.mrp.sml.core.common;

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
public final class DefaultDispatchersProvider_Factory implements Factory<DefaultDispatchersProvider> {
  @Override
  public DefaultDispatchersProvider get() {
    return newInstance();
  }

  public static DefaultDispatchersProvider_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DefaultDispatchersProvider newInstance() {
    return new DefaultDispatchersProvider();
  }

  private static final class InstanceHolder {
    private static final DefaultDispatchersProvider_Factory INSTANCE = new DefaultDispatchersProvider_Factory();
  }
}
