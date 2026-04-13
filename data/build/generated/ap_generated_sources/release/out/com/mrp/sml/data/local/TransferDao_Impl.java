package com.mrp.sml.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TransferDao_Impl implements TransferDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransferEntity> __insertionAdapterOfTransferEntity;

  public TransferDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransferEntity = new EntityInsertionAdapter<TransferEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transfer_history` (`id`,`fileName`,`fileSizeBytes`,`mimeType`,`direction`,`status`,`timestampEpochMillis`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TransferEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.fileName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.fileName);
        }
        statement.bindLong(3, entity.fileSizeBytes);
        if (entity.mimeType == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.mimeType);
        }
        if (entity.direction == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.direction);
        }
        if (entity.status == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.status);
        }
        statement.bindLong(7, entity.timestampEpochMillis);
      }
    };
  }

  @Override
  public void insert(final TransferEntity record) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTransferEntity.insert(record);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TransferEntity> getTransferHistory() {
    final String _sql = "SELECT * FROM transfer_history ORDER BY timestampEpochMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
      final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
      final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
      final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfTimestampEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampEpochMillis");
      final List<TransferEntity> _result = new ArrayList<TransferEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TransferEntity _item;
        final String _tmpFileName;
        if (_cursor.isNull(_cursorIndexOfFileName)) {
          _tmpFileName = null;
        } else {
          _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        }
        final long _tmpFileSizeBytes;
        _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
        final String _tmpMimeType;
        if (_cursor.isNull(_cursorIndexOfMimeType)) {
          _tmpMimeType = null;
        } else {
          _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
        }
        final String _tmpDirection;
        if (_cursor.isNull(_cursorIndexOfDirection)) {
          _tmpDirection = null;
        } else {
          _tmpDirection = _cursor.getString(_cursorIndexOfDirection);
        }
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final long _tmpTimestampEpochMillis;
        _tmpTimestampEpochMillis = _cursor.getLong(_cursorIndexOfTimestampEpochMillis);
        _item = new TransferEntity(_tmpFileName,_tmpFileSizeBytes,_tmpMimeType,_tmpDirection,_tmpStatus,_tmpTimestampEpochMillis);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
