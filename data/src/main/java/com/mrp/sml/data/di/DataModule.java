package com.mrp.sml.data.di;

import android.content.Context;
import androidx.room.Room;
import com.mrp.sml.core.common.DispatchersProvider;
import com.mrp.sml.data.local.SmlDatabase;
import com.mrp.sml.data.local.TransferDao;
import com.mrp.sml.data.repository.DefaultDeviceConnectionRepository;
import com.mrp.sml.data.repository.DefaultFileTransferRepository;
import com.mrp.sml.data.repository.DefaultTransferHistoryRepository;
import com.mrp.sml.domain.repository.DeviceConnectionRepository;
import com.mrp.sml.domain.repository.FileTransferRepository;
import com.mrp.sml.domain.repository.TransferHistoryRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public final class DataModule {

    private DataModule() {
    }

    @Provides
    @Singleton
    public static SmlDatabase provideSmlDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, SmlDatabase.class, "sml.db").build();
    }

    @Provides
    public static TransferDao provideTransferDao(SmlDatabase database) {
        return database.transferDao();
    }

    @Provides
    @Singleton
    public static TransferHistoryRepository provideTransferHistoryRepository(TransferDao transferDao) {
        return new DefaultTransferHistoryRepository(transferDao);
    }

    @Provides
    @Singleton
    public static FileTransferRepository provideFileTransferRepository(
            DispatchersProvider dispatchersProvider,
            TransferHistoryRepository transferHistoryRepository) {
        return new DefaultFileTransferRepository(dispatchersProvider, transferHistoryRepository);
    }

    @Provides
    @Singleton
    public static DeviceConnectionRepository provideDeviceConnectionRepository(
            @ApplicationContext Context context,
            DispatchersProvider dispatchersProvider) {
        return new DefaultDeviceConnectionRepository(context, dispatchersProvider);
    }
}
