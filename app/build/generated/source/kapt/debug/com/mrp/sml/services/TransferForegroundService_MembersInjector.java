package com.mrp.sml.services;

import com.mrp.sml.core.utils.NotificationUtils;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class TransferForegroundService_MembersInjector implements MembersInjector<TransferForegroundService> {
  private final Provider<NotificationUtils> notificationUtilsProvider;

  public TransferForegroundService_MembersInjector(
      Provider<NotificationUtils> notificationUtilsProvider) {
    this.notificationUtilsProvider = notificationUtilsProvider;
  }

  public static MembersInjector<TransferForegroundService> create(
      Provider<NotificationUtils> notificationUtilsProvider) {
    return new TransferForegroundService_MembersInjector(notificationUtilsProvider);
  }

  @Override
  public void injectMembers(TransferForegroundService instance) {
    injectNotificationUtils(instance, notificationUtilsProvider.get());
  }

  @InjectedFieldSignature("com.mrp.sml.services.TransferForegroundService.notificationUtils")
  public static void injectNotificationUtils(TransferForegroundService instance,
      NotificationUtils notificationUtils) {
    instance.notificationUtils = notificationUtils;
  }
}
