package com.mrp.sml.data.local.db.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.mrp.sml.data.local.db.entities.TransferEntity;
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
public final class TransferDao_Impl implements TransferDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<TransferEntity> __insertAdapterOfTransferEntity;

  public TransferDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfTransferEntity = new EntityInsertAdapter<TransferEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transfer_history` (`id`,`file_name`,`file_size_bytes`,`mime_type`,`direction`,`status`,`progress`,`session_token`,`timestamp_epoch_millis`,`completed_at_millis`,`error_message`,`peer_device_name`,`total_files`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final TransferEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getFileName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getFileName());
        }
        statement.bindLong(3, entity.getFileSizeBytes());
        if (entity.getMimeType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getMimeType());
        }
        if (entity.getDirection() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDirection());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getStatus());
        }
        statement.bindDouble(7, entity.getProgress());
        if (entity.getSessionToken() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getSessionToken());
        }
        statement.bindLong(9, entity.getTimestampEpochMillis());
        if (entity.getCompletedAtMillis() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getCompletedAtMillis());
        }
        if (entity.getErrorMessage() == null) {
          statement.bindNull(11);
        } else {
          statement.bindText(11, entity.getErrorMessage());
        }
        if (entity.getPeerDeviceName() == null) {
          statement.bindNull(12);
        } else {
          statement.bindText(12, entity.getPeerDeviceName());
        }
        statement.bindLong(13, entity.getTotalFiles());
      }
    };
  }

  @Override
  public Object insert(final TransferEntity transfer,
      final Continuation<? super Unit> $completion) {
    if (transfer == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfTransferEntity.insert(_connection, transfer);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<TransferEntity>> getTransferHistory() {
    final String _sql = "SELECT * FROM transfer_history ORDER BY timestamp_epoch_millis DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"transfer_history"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "file_name");
        final int _columnIndexOfFileSizeBytes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "file_size_bytes");
        final int _columnIndexOfMimeType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mime_type");
        final int _columnIndexOfDirection = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "direction");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final int _columnIndexOfProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progress");
        final int _columnIndexOfSessionToken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "session_token");
        final int _columnIndexOfTimestampEpochMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp_epoch_millis");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed_at_millis");
        final int _columnIndexOfErrorMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "error_message");
        final int _columnIndexOfPeerDeviceName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "peer_device_name");
        final int _columnIndexOfTotalFiles = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "total_files");
        final List<TransferEntity> _result = new ArrayList<TransferEntity>();
        while (_stmt.step()) {
          final TransferEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpFileName;
          if (_stmt.isNull(_columnIndexOfFileName)) {
            _tmpFileName = null;
          } else {
            _tmpFileName = _stmt.getText(_columnIndexOfFileName);
          }
          final long _tmpFileSizeBytes;
          _tmpFileSizeBytes = _stmt.getLong(_columnIndexOfFileSizeBytes);
          final String _tmpMimeType;
          if (_stmt.isNull(_columnIndexOfMimeType)) {
            _tmpMimeType = null;
          } else {
            _tmpMimeType = _stmt.getText(_columnIndexOfMimeType);
          }
          final String _tmpDirection;
          if (_stmt.isNull(_columnIndexOfDirection)) {
            _tmpDirection = null;
          } else {
            _tmpDirection = _stmt.getText(_columnIndexOfDirection);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          final float _tmpProgress;
          _tmpProgress = (float) (_stmt.getDouble(_columnIndexOfProgress));
          final String _tmpSessionToken;
          if (_stmt.isNull(_columnIndexOfSessionToken)) {
            _tmpSessionToken = null;
          } else {
            _tmpSessionToken = _stmt.getText(_columnIndexOfSessionToken);
          }
          final long _tmpTimestampEpochMillis;
          _tmpTimestampEpochMillis = _stmt.getLong(_columnIndexOfTimestampEpochMillis);
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final String _tmpErrorMessage;
          if (_stmt.isNull(_columnIndexOfErrorMessage)) {
            _tmpErrorMessage = null;
          } else {
            _tmpErrorMessage = _stmt.getText(_columnIndexOfErrorMessage);
          }
          final String _tmpPeerDeviceName;
          if (_stmt.isNull(_columnIndexOfPeerDeviceName)) {
            _tmpPeerDeviceName = null;
          } else {
            _tmpPeerDeviceName = _stmt.getText(_columnIndexOfPeerDeviceName);
          }
          final int _tmpTotalFiles;
          _tmpTotalFiles = (int) (_stmt.getLong(_columnIndexOfTotalFiles));
          _item = new TransferEntity(_tmpId,_tmpFileName,_tmpFileSizeBytes,_tmpMimeType,_tmpDirection,_tmpStatus,_tmpProgress,_tmpSessionToken,_tmpTimestampEpochMillis,_tmpCompletedAtMillis,_tmpErrorMessage,_tmpPeerDeviceName,_tmpTotalFiles);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getTransferById(final String id,
      final Continuation<? super TransferEntity> $completion) {
    final String _sql = "SELECT * FROM transfer_history WHERE id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "file_name");
        final int _columnIndexOfFileSizeBytes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "file_size_bytes");
        final int _columnIndexOfMimeType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mime_type");
        final int _columnIndexOfDirection = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "direction");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final int _columnIndexOfProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progress");
        final int _columnIndexOfSessionToken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "session_token");
        final int _columnIndexOfTimestampEpochMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp_epoch_millis");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed_at_millis");
        final int _columnIndexOfErrorMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "error_message");
        final int _columnIndexOfPeerDeviceName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "peer_device_name");
        final int _columnIndexOfTotalFiles = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "total_files");
        final TransferEntity _result;
        if (_stmt.step()) {
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpFileName;
          if (_stmt.isNull(_columnIndexOfFileName)) {
            _tmpFileName = null;
          } else {
            _tmpFileName = _stmt.getText(_columnIndexOfFileName);
          }
          final long _tmpFileSizeBytes;
          _tmpFileSizeBytes = _stmt.getLong(_columnIndexOfFileSizeBytes);
          final String _tmpMimeType;
          if (_stmt.isNull(_columnIndexOfMimeType)) {
            _tmpMimeType = null;
          } else {
            _tmpMimeType = _stmt.getText(_columnIndexOfMimeType);
          }
          final String _tmpDirection;
          if (_stmt.isNull(_columnIndexOfDirection)) {
            _tmpDirection = null;
          } else {
            _tmpDirection = _stmt.getText(_columnIndexOfDirection);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          final float _tmpProgress;
          _tmpProgress = (float) (_stmt.getDouble(_columnIndexOfProgress));
          final String _tmpSessionToken;
          if (_stmt.isNull(_columnIndexOfSessionToken)) {
            _tmpSessionToken = null;
          } else {
            _tmpSessionToken = _stmt.getText(_columnIndexOfSessionToken);
          }
          final long _tmpTimestampEpochMillis;
          _tmpTimestampEpochMillis = _stmt.getLong(_columnIndexOfTimestampEpochMillis);
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final String _tmpErrorMessage;
          if (_stmt.isNull(_columnIndexOfErrorMessage)) {
            _tmpErrorMessage = null;
          } else {
            _tmpErrorMessage = _stmt.getText(_columnIndexOfErrorMessage);
          }
          final String _tmpPeerDeviceName;
          if (_stmt.isNull(_columnIndexOfPeerDeviceName)) {
            _tmpPeerDeviceName = null;
          } else {
            _tmpPeerDeviceName = _stmt.getText(_columnIndexOfPeerDeviceName);
          }
          final int _tmpTotalFiles;
          _tmpTotalFiles = (int) (_stmt.getLong(_columnIndexOfTotalFiles));
          _result = new TransferEntity(_tmpId,_tmpFileName,_tmpFileSizeBytes,_tmpMimeType,_tmpDirection,_tmpStatus,_tmpProgress,_tmpSessionToken,_tmpTimestampEpochMillis,_tmpCompletedAtMillis,_tmpErrorMessage,_tmpPeerDeviceName,_tmpTotalFiles);
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
  public Object getTransferBySession(final String sessionToken,
      final Continuation<? super TransferEntity> $completion) {
    final String _sql = "SELECT * FROM transfer_history WHERE session_token = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (sessionToken == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, sessionToken);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfFileName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "file_name");
        final int _columnIndexOfFileSizeBytes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "file_size_bytes");
        final int _columnIndexOfMimeType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mime_type");
        final int _columnIndexOfDirection = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "direction");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final int _columnIndexOfProgress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "progress");
        final int _columnIndexOfSessionToken = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "session_token");
        final int _columnIndexOfTimestampEpochMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "timestamp_epoch_millis");
        final int _columnIndexOfCompletedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "completed_at_millis");
        final int _columnIndexOfErrorMessage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "error_message");
        final int _columnIndexOfPeerDeviceName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "peer_device_name");
        final int _columnIndexOfTotalFiles = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "total_files");
        final TransferEntity _result;
        if (_stmt.step()) {
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpFileName;
          if (_stmt.isNull(_columnIndexOfFileName)) {
            _tmpFileName = null;
          } else {
            _tmpFileName = _stmt.getText(_columnIndexOfFileName);
          }
          final long _tmpFileSizeBytes;
          _tmpFileSizeBytes = _stmt.getLong(_columnIndexOfFileSizeBytes);
          final String _tmpMimeType;
          if (_stmt.isNull(_columnIndexOfMimeType)) {
            _tmpMimeType = null;
          } else {
            _tmpMimeType = _stmt.getText(_columnIndexOfMimeType);
          }
          final String _tmpDirection;
          if (_stmt.isNull(_columnIndexOfDirection)) {
            _tmpDirection = null;
          } else {
            _tmpDirection = _stmt.getText(_columnIndexOfDirection);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          final float _tmpProgress;
          _tmpProgress = (float) (_stmt.getDouble(_columnIndexOfProgress));
          final String _tmpSessionToken;
          if (_stmt.isNull(_columnIndexOfSessionToken)) {
            _tmpSessionToken = null;
          } else {
            _tmpSessionToken = _stmt.getText(_columnIndexOfSessionToken);
          }
          final long _tmpTimestampEpochMillis;
          _tmpTimestampEpochMillis = _stmt.getLong(_columnIndexOfTimestampEpochMillis);
          final Long _tmpCompletedAtMillis;
          if (_stmt.isNull(_columnIndexOfCompletedAtMillis)) {
            _tmpCompletedAtMillis = null;
          } else {
            _tmpCompletedAtMillis = _stmt.getLong(_columnIndexOfCompletedAtMillis);
          }
          final String _tmpErrorMessage;
          if (_stmt.isNull(_columnIndexOfErrorMessage)) {
            _tmpErrorMessage = null;
          } else {
            _tmpErrorMessage = _stmt.getText(_columnIndexOfErrorMessage);
          }
          final String _tmpPeerDeviceName;
          if (_stmt.isNull(_columnIndexOfPeerDeviceName)) {
            _tmpPeerDeviceName = null;
          } else {
            _tmpPeerDeviceName = _stmt.getText(_columnIndexOfPeerDeviceName);
          }
          final int _tmpTotalFiles;
          _tmpTotalFiles = (int) (_stmt.getLong(_columnIndexOfTotalFiles));
          _result = new TransferEntity(_tmpId,_tmpFileName,_tmpFileSizeBytes,_tmpMimeType,_tmpDirection,_tmpStatus,_tmpProgress,_tmpSessionToken,_tmpTimestampEpochMillis,_tmpCompletedAtMillis,_tmpErrorMessage,_tmpPeerDeviceName,_tmpTotalFiles);
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
  public Object updateStatus(final String id, final String status, final String error,
      final Long completedAt, final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE transfer_history SET status = ?, error_message = ?, completed_at_millis = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, status);
        }
        _argIndex = 2;
        if (error == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, error);
        }
        _argIndex = 3;
        if (completedAt == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, completedAt);
        }
        _argIndex = 4;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object updateProgress(final String id, final float progress,
      final Continuation<? super Unit> $completion) {
    final String _sql = "UPDATE transfer_history SET progress = ? WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, progress);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object delete(final String id, final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM transfer_history WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
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
    final String _sql = "DELETE FROM transfer_history";
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
