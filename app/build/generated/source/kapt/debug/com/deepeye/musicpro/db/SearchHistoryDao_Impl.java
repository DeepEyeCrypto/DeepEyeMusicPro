package com.deepeye.musicpro.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SearchHistoryDao_Impl implements SearchHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SearchHistoryEntry> __insertionAdapterOfSearchHistoryEntry;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public SearchHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSearchHistoryEntry = new EntityInsertionAdapter<SearchHistoryEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `search_history` (`query`,`lastSearchedAt`,`hitCount`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SearchHistoryEntry entity) {
        if (entity.getQuery() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getQuery());
        }
        statement.bindLong(2, entity.getLastSearchedAt());
        statement.bindLong(3, entity.getHitCount());
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM search_history";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final SearchHistoryEntry entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSearchHistoryEntry.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clear(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClear.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SearchHistoryEntry>> observeRecent(final int limit) {
    final String _sql = "SELECT * FROM search_history ORDER BY lastSearchedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"search_history"}, new Callable<List<SearchHistoryEntry>>() {
      @Override
      @NonNull
      public List<SearchHistoryEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfLastSearchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSearchedAt");
          final int _cursorIndexOfHitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hitCount");
          final List<SearchHistoryEntry> _result = new ArrayList<SearchHistoryEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SearchHistoryEntry _item;
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final long _tmpLastSearchedAt;
            _tmpLastSearchedAt = _cursor.getLong(_cursorIndexOfLastSearchedAt);
            final int _tmpHitCount;
            _tmpHitCount = _cursor.getInt(_cursorIndexOfHitCount);
            _item = new SearchHistoryEntry(_tmpQuery,_tmpLastSearchedAt,_tmpHitCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object get(final String query,
      final Continuation<? super SearchHistoryEntry> $completion) {
    final String _sql = "SELECT * FROM search_history WHERE query = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SearchHistoryEntry>() {
      @Override
      @Nullable
      public SearchHistoryEntry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfLastSearchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSearchedAt");
          final int _cursorIndexOfHitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hitCount");
          final SearchHistoryEntry _result;
          if (_cursor.moveToFirst()) {
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final long _tmpLastSearchedAt;
            _tmpLastSearchedAt = _cursor.getLong(_cursorIndexOfLastSearchedAt);
            final int _tmpHitCount;
            _tmpHitCount = _cursor.getInt(_cursorIndexOfHitCount);
            _result = new SearchHistoryEntry(_tmpQuery,_tmpLastSearchedAt,_tmpHitCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
