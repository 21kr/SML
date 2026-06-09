package com.mrp.sml.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import com.mrp.sml.data.local.db.dao.DeviceDao;
import com.mrp.sml.data.local.db.dao.DeviceDao_Impl;
import com.mrp.sml.data.local.db.dao.TransferDao;
import com.mrp.sml.data.local.db.dao.TransferDao_Impl;
import com.mrp.sml.data.local.db.dao.TransferProgressDao;
import com.mrp.sml.data.local.db.dao.TransferProgressDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile TransferDao _transferDao;

  private volatile DeviceDao _deviceDao;

  private volatile TransferProgressDao _transferProgressDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(3, "ae666deab5c98668fe4a46b8634e482f", "dbcf3ffeee8432db27c5d54d6da3b1d9") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `transfer_history` (`id` TEXT NOT NULL, `file_name` TEXT NOT NULL, `file_size_bytes` INTEGER NOT NULL, `mime_type` TEXT NOT NULL, `direction` TEXT NOT NULL, `status` TEXT NOT NULL, `progress` REAL NOT NULL, `session_token` TEXT NOT NULL, `timestamp_epoch_millis` INTEGER NOT NULL, `completed_at_millis` INTEGER, `error_message` TEXT, `peer_device_name` TEXT NOT NULL, `total_files` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_transfer_history_session_token` ON `transfer_history` (`session_token`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `paired_devices` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `device_id` TEXT NOT NULL, `device_name` TEXT NOT NULL, `last_connected_at` INTEGER NOT NULL, `ip_address` TEXT NOT NULL)");
        SQLite.execSQL(connection, "CREATE UNIQUE INDEX IF NOT EXISTS `index_paired_devices_device_id` ON `paired_devices` (`device_id`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `transfer_progress` (`transfer_id` TEXT NOT NULL, `last_chunk_index` INTEGER NOT NULL, `last_file_index` INTEGER NOT NULL, `transferred_bytes` INTEGER NOT NULL, `total_bytes` INTEGER NOT NULL, PRIMARY KEY(`transfer_id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ae666deab5c98668fe4a46b8634e482f')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `transfer_history`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `paired_devices`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `transfer_progress`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsTransferHistory = new HashMap<String, TableInfo.Column>(13);
        _columnsTransferHistory.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("file_name", new TableInfo.Column("file_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("file_size_bytes", new TableInfo.Column("file_size_bytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("mime_type", new TableInfo.Column("mime_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("direction", new TableInfo.Column("direction", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("progress", new TableInfo.Column("progress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("session_token", new TableInfo.Column("session_token", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("timestamp_epoch_millis", new TableInfo.Column("timestamp_epoch_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("completed_at_millis", new TableInfo.Column("completed_at_millis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("error_message", new TableInfo.Column("error_message", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("peer_device_name", new TableInfo.Column("peer_device_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferHistory.put("total_files", new TableInfo.Column("total_files", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysTransferHistory = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesTransferHistory = new HashSet<TableInfo.Index>(1);
        _indicesTransferHistory.add(new TableInfo.Index("index_transfer_history_session_token", false, Arrays.asList("session_token"), Arrays.asList("ASC")));
        final TableInfo _infoTransferHistory = new TableInfo("transfer_history", _columnsTransferHistory, _foreignKeysTransferHistory, _indicesTransferHistory);
        final TableInfo _existingTransferHistory = TableInfo.read(connection, "transfer_history");
        if (!_infoTransferHistory.equals(_existingTransferHistory)) {
          return new RoomOpenDelegate.ValidationResult(false, "transfer_history(com.mrp.sml.data.local.db.entities.TransferEntity).\n"
                  + " Expected:\n" + _infoTransferHistory + "\n"
                  + " Found:\n" + _existingTransferHistory);
        }
        final Map<String, TableInfo.Column> _columnsPairedDevices = new HashMap<String, TableInfo.Column>(5);
        _columnsPairedDevices.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPairedDevices.put("device_id", new TableInfo.Column("device_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPairedDevices.put("device_name", new TableInfo.Column("device_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPairedDevices.put("last_connected_at", new TableInfo.Column("last_connected_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPairedDevices.put("ip_address", new TableInfo.Column("ip_address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysPairedDevices = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesPairedDevices = new HashSet<TableInfo.Index>(1);
        _indicesPairedDevices.add(new TableInfo.Index("index_paired_devices_device_id", true, Arrays.asList("device_id"), Arrays.asList("ASC")));
        final TableInfo _infoPairedDevices = new TableInfo("paired_devices", _columnsPairedDevices, _foreignKeysPairedDevices, _indicesPairedDevices);
        final TableInfo _existingPairedDevices = TableInfo.read(connection, "paired_devices");
        if (!_infoPairedDevices.equals(_existingPairedDevices)) {
          return new RoomOpenDelegate.ValidationResult(false, "paired_devices(com.mrp.sml.data.local.db.entities.DeviceEntity).\n"
                  + " Expected:\n" + _infoPairedDevices + "\n"
                  + " Found:\n" + _existingPairedDevices);
        }
        final Map<String, TableInfo.Column> _columnsTransferProgress = new HashMap<String, TableInfo.Column>(5);
        _columnsTransferProgress.put("transfer_id", new TableInfo.Column("transfer_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferProgress.put("last_chunk_index", new TableInfo.Column("last_chunk_index", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferProgress.put("last_file_index", new TableInfo.Column("last_file_index", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferProgress.put("transferred_bytes", new TableInfo.Column("transferred_bytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransferProgress.put("total_bytes", new TableInfo.Column("total_bytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysTransferProgress = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesTransferProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTransferProgress = new TableInfo("transfer_progress", _columnsTransferProgress, _foreignKeysTransferProgress, _indicesTransferProgress);
        final TableInfo _existingTransferProgress = TableInfo.read(connection, "transfer_progress");
        if (!_infoTransferProgress.equals(_existingTransferProgress)) {
          return new RoomOpenDelegate.ValidationResult(false, "transfer_progress(com.mrp.sml.data.local.db.entities.TransferProgressEntity).\n"
                  + " Expected:\n" + _infoTransferProgress + "\n"
                  + " Found:\n" + _existingTransferProgress);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "transfer_history", "paired_devices", "transfer_progress");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "transfer_history", "paired_devices", "transfer_progress");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TransferDao.class, TransferDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DeviceDao.class, DeviceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TransferProgressDao.class, TransferProgressDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TransferDao transferDao() {
    if (_transferDao != null) {
      return _transferDao;
    } else {
      synchronized(this) {
        if(_transferDao == null) {
          _transferDao = new TransferDao_Impl(this);
        }
        return _transferDao;
      }
    }
  }

  @Override
  public DeviceDao deviceDao() {
    if (_deviceDao != null) {
      return _deviceDao;
    } else {
      synchronized(this) {
        if(_deviceDao == null) {
          _deviceDao = new DeviceDao_Impl(this);
        }
        return _deviceDao;
      }
    }
  }

  @Override
  public TransferProgressDao transferProgressDao() {
    if (_transferProgressDao != null) {
      return _transferProgressDao;
    } else {
      synchronized(this) {
        if(_transferProgressDao == null) {
          _transferProgressDao = new TransferProgressDao_Impl(this);
        }
        return _transferProgressDao;
      }
    }
  }
}
