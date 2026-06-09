package com.mrp.sml.data.local.db.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.mrp.sml.data.local.db.entities.DeviceEntity;
import java.lang.Class;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class DeviceDao_Impl implements DeviceDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<DeviceEntity> __insertAdapterOfDeviceEntity;

  public DeviceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfDeviceEntity = new EntityInsertAdapter<DeviceEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `paired_devices` (`id`,`device_id`,`device_name`,`last_connected_at`,`ip_address`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final DeviceEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDeviceId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getDeviceId());
        }
        if (entity.getDeviceName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDeviceName());
        }
        statement.bindLong(4, entity.getLastConnectedAt());
        if (entity.getIpAddress() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getIpAddress());
        }
      }
    };
  }

  @Override
  public Object insert(final DeviceEntity device, final Continuation<? super Long> $completion) {
    if (device == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      return __insertAdapterOfDeviceEntity.insertAndReturnId(_connection, device);
    }, $completion);
  }

  @Override
  public Flow<List<DeviceEntity>> getPairedDevices() {
    final String _sql = "SELECT * FROM paired_devices ORDER BY last_connected_at DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"paired_devices"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfDeviceId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "device_id");
        final int _columnIndexOfDeviceName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "device_name");
        final int _columnIndexOfLastConnectedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "last_connected_at");
        final int _columnIndexOfIpAddress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ip_address");
        final List<DeviceEntity> _result = new ArrayList<DeviceEntity>();
        while (_stmt.step()) {
          final DeviceEntity _item;
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpDeviceId;
          if (_stmt.isNull(_columnIndexOfDeviceId)) {
            _tmpDeviceId = null;
          } else {
            _tmpDeviceId = _stmt.getText(_columnIndexOfDeviceId);
          }
          final String _tmpDeviceName;
          if (_stmt.isNull(_columnIndexOfDeviceName)) {
            _tmpDeviceName = null;
          } else {
            _tmpDeviceName = _stmt.getText(_columnIndexOfDeviceName);
          }
          final long _tmpLastConnectedAt;
          _tmpLastConnectedAt = _stmt.getLong(_columnIndexOfLastConnectedAt);
          final String _tmpIpAddress;
          if (_stmt.isNull(_columnIndexOfIpAddress)) {
            _tmpIpAddress = null;
          } else {
            _tmpIpAddress = _stmt.getText(_columnIndexOfIpAddress);
          }
          _item = new DeviceEntity(_tmpId,_tmpDeviceId,_tmpDeviceName,_tmpLastConnectedAt,_tmpIpAddress);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getDeviceById(final String deviceId,
      final Continuation<? super DeviceEntity> $completion) {
    final String _sql = "SELECT * FROM paired_devices WHERE device_id = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (deviceId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, deviceId);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfDeviceId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "device_id");
        final int _columnIndexOfDeviceName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "device_name");
        final int _columnIndexOfLastConnectedAt = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "last_connected_at");
        final int _columnIndexOfIpAddress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ip_address");
        final DeviceEntity _result;
        if (_stmt.step()) {
          final long _tmpId;
          _tmpId = _stmt.getLong(_columnIndexOfId);
          final String _tmpDeviceId;
          if (_stmt.isNull(_columnIndexOfDeviceId)) {
            _tmpDeviceId = null;
          } else {
            _tmpDeviceId = _stmt.getText(_columnIndexOfDeviceId);
          }
          final String _tmpDeviceName;
          if (_stmt.isNull(_columnIndexOfDeviceName)) {
            _tmpDeviceName = null;
          } else {
            _tmpDeviceName = _stmt.getText(_columnIndexOfDeviceName);
          }
          final long _tmpLastConnectedAt;
          _tmpLastConnectedAt = _stmt.getLong(_columnIndexOfLastConnectedAt);
          final String _tmpIpAddress;
          if (_stmt.isNull(_columnIndexOfIpAddress)) {
            _tmpIpAddress = null;
          } else {
            _tmpIpAddress = _stmt.getText(_columnIndexOfIpAddress);
          }
          _result = new DeviceEntity(_tmpId,_tmpDeviceId,_tmpDeviceName,_tmpLastConnectedAt,_tmpIpAddress);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object delete(final String deviceId, final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM paired_devices WHERE device_id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (deviceId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, deviceId);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM paired_devices";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
