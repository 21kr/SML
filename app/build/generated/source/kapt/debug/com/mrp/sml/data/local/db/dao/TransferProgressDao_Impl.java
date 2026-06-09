package com.mrp.sml.data.local.db.dao;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import com.mrp.sml.data.local.db.entities.TransferProgressEntity;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class TransferProgressDao_Impl implements TransferProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<TransferProgressEntity> __insertAdapterOfTransferProgressEntity;

  public TransferProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfTransferProgressEntity = new EntityInsertAdapter<TransferProgressEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transfer_progress` (`transfer_id`,`last_chunk_index`,`last_file_index`,`transferred_bytes`,`total_bytes`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final TransferProgressEntity entity) {
        if (entity.getTransferId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getTransferId());
        }
        statement.bindLong(2, entity.getLastChunkIndex());
        statement.bindLong(3, entity.getLastFileIndex());
        statement.bindLong(4, entity.getTransferredBytes());
        statement.bindLong(5, entity.getTotalBytes());
      }
    };
  }

  @Override
  public Object upsert(final TransferProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    if (progress == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfTransferProgressEntity.insert(_connection, progress);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object getProgress(final String transferId,
      final Continuation<? super TransferProgressEntity> $completion) {
    final String _sql = "SELECT * FROM transfer_progress WHERE transfer_id = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (transferId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, transferId);
        }
        final int _columnIndexOfTransferId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "transfer_id");
        final int _columnIndexOfLastChunkIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "last_chunk_index");
        final int _columnIndexOfLastFileIndex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "last_file_index");
        final int _columnIndexOfTransferredBytes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "transferred_bytes");
        final int _columnIndexOfTotalBytes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "total_bytes");
        final TransferProgressEntity _result;
        if (_stmt.step()) {
          final String _tmpTransferId;
          if (_stmt.isNull(_columnIndexOfTransferId)) {
            _tmpTransferId = null;
          } else {
            _tmpTransferId = _stmt.getText(_columnIndexOfTransferId);
          }
          final long _tmpLastChunkIndex;
          _tmpLastChunkIndex = _stmt.getLong(_columnIndexOfLastChunkIndex);
          final int _tmpLastFileIndex;
          _tmpLastFileIndex = (int) (_stmt.getLong(_columnIndexOfLastFileIndex));
          final long _tmpTransferredBytes;
          _tmpTransferredBytes = _stmt.getLong(_columnIndexOfTransferredBytes);
          final long _tmpTotalBytes;
          _tmpTotalBytes = _stmt.getLong(_columnIndexOfTotalBytes);
          _result = new TransferProgressEntity(_tmpTransferId,_tmpLastChunkIndex,_tmpLastFileIndex,_tmpTransferredBytes,_tmpTotalBytes);
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
  public Object getLastPausedTransferId(final Continuation<? super String> $completion) {
    final String _sql = "SELECT transfer_id FROM transfer_progress ORDER BY last_chunk_index DESC LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final String _result;
        if (_stmt.step()) {
          if (_stmt.isNull(0)) {
            _result = null;
          } else {
            _result = _stmt.getText(0);
          }
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
  public Object delete(final String transferId, final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM transfer_progress WHERE transfer_id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (transferId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, transferId);
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
    final String _sql = "DELETE FROM transfer_progress";
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
