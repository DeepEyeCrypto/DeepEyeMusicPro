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
import java.lang.Float;
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
public final class PresetDao_Impl implements PresetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DspPresetEntity> __insertionAdapterOfDspPresetEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfDeleteCustom;

  public PresetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDspPresetEntity = new EntityInsertionAdapter<DspPresetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `dsp_presets` (`id`,`name`,`eqGains`,`bassBoost`,`stereoWidth`,`compressorThresholdDb`,`compressorRatio`,`compressorAttackMs`,`compressorReleaseMs`,`reverbRoom`,`reverbDamping`,`reverbWidth`,`reverbWet`,`limiterCeilingDb`,`builtIn`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DspPresetEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final String _tmp = __converters.floatsToString(entity.getEqGains());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindDouble(4, entity.getBassBoost());
        statement.bindDouble(5, entity.getStereoWidth());
        statement.bindDouble(6, entity.getCompressorThresholdDb());
        statement.bindDouble(7, entity.getCompressorRatio());
        statement.bindDouble(8, entity.getCompressorAttackMs());
        statement.bindDouble(9, entity.getCompressorReleaseMs());
        statement.bindDouble(10, entity.getReverbRoom());
        statement.bindDouble(11, entity.getReverbDamping());
        statement.bindDouble(12, entity.getReverbWidth());
        statement.bindDouble(13, entity.getReverbWet());
        statement.bindDouble(14, entity.getLimiterCeilingDb());
        final int _tmp_1 = entity.getBuiltIn() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        statement.bindLong(16, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfDeleteCustom = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM dsp_presets WHERE id = ? AND builtIn = 0";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final DspPresetEntity preset, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDspPresetEntity.insert(preset);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertAll(final List<DspPresetEntity> presets,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDspPresetEntity.insert(presets);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCustom(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCustom.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
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
          __preparedStmtOfDeleteCustom.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DspPresetEntity>> observePresets() {
    final String _sql = "SELECT `dsp_presets`.`id` AS `id`, `dsp_presets`.`name` AS `name`, `dsp_presets`.`eqGains` AS `eqGains`, `dsp_presets`.`bassBoost` AS `bassBoost`, `dsp_presets`.`stereoWidth` AS `stereoWidth`, `dsp_presets`.`compressorThresholdDb` AS `compressorThresholdDb`, `dsp_presets`.`compressorRatio` AS `compressorRatio`, `dsp_presets`.`compressorAttackMs` AS `compressorAttackMs`, `dsp_presets`.`compressorReleaseMs` AS `compressorReleaseMs`, `dsp_presets`.`reverbRoom` AS `reverbRoom`, `dsp_presets`.`reverbDamping` AS `reverbDamping`, `dsp_presets`.`reverbWidth` AS `reverbWidth`, `dsp_presets`.`reverbWet` AS `reverbWet`, `dsp_presets`.`limiterCeilingDb` AS `limiterCeilingDb`, `dsp_presets`.`builtIn` AS `builtIn`, `dsp_presets`.`updatedAt` AS `updatedAt` FROM dsp_presets ORDER BY builtIn DESC, name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"dsp_presets"}, new Callable<List<DspPresetEntity>>() {
      @Override
      @NonNull
      public List<DspPresetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfEqGains = 2;
          final int _cursorIndexOfBassBoost = 3;
          final int _cursorIndexOfStereoWidth = 4;
          final int _cursorIndexOfCompressorThresholdDb = 5;
          final int _cursorIndexOfCompressorRatio = 6;
          final int _cursorIndexOfCompressorAttackMs = 7;
          final int _cursorIndexOfCompressorReleaseMs = 8;
          final int _cursorIndexOfReverbRoom = 9;
          final int _cursorIndexOfReverbDamping = 10;
          final int _cursorIndexOfReverbWidth = 11;
          final int _cursorIndexOfReverbWet = 12;
          final int _cursorIndexOfLimiterCeilingDb = 13;
          final int _cursorIndexOfBuiltIn = 14;
          final int _cursorIndexOfUpdatedAt = 15;
          final List<DspPresetEntity> _result = new ArrayList<DspPresetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DspPresetEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<Float> _tmpEqGains;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEqGains)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEqGains);
            }
            _tmpEqGains = __converters.stringToFloats(_tmp);
            final float _tmpBassBoost;
            _tmpBassBoost = _cursor.getFloat(_cursorIndexOfBassBoost);
            final float _tmpStereoWidth;
            _tmpStereoWidth = _cursor.getFloat(_cursorIndexOfStereoWidth);
            final float _tmpCompressorThresholdDb;
            _tmpCompressorThresholdDb = _cursor.getFloat(_cursorIndexOfCompressorThresholdDb);
            final float _tmpCompressorRatio;
            _tmpCompressorRatio = _cursor.getFloat(_cursorIndexOfCompressorRatio);
            final float _tmpCompressorAttackMs;
            _tmpCompressorAttackMs = _cursor.getFloat(_cursorIndexOfCompressorAttackMs);
            final float _tmpCompressorReleaseMs;
            _tmpCompressorReleaseMs = _cursor.getFloat(_cursorIndexOfCompressorReleaseMs);
            final float _tmpReverbRoom;
            _tmpReverbRoom = _cursor.getFloat(_cursorIndexOfReverbRoom);
            final float _tmpReverbDamping;
            _tmpReverbDamping = _cursor.getFloat(_cursorIndexOfReverbDamping);
            final float _tmpReverbWidth;
            _tmpReverbWidth = _cursor.getFloat(_cursorIndexOfReverbWidth);
            final float _tmpReverbWet;
            _tmpReverbWet = _cursor.getFloat(_cursorIndexOfReverbWet);
            final float _tmpLimiterCeilingDb;
            _tmpLimiterCeilingDb = _cursor.getFloat(_cursorIndexOfLimiterCeilingDb);
            final boolean _tmpBuiltIn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfBuiltIn);
            _tmpBuiltIn = _tmp_1 != 0;
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new DspPresetEntity(_tmpId,_tmpName,_tmpEqGains,_tmpBassBoost,_tmpStereoWidth,_tmpCompressorThresholdDb,_tmpCompressorRatio,_tmpCompressorAttackMs,_tmpCompressorReleaseMs,_tmpReverbRoom,_tmpReverbDamping,_tmpReverbWidth,_tmpReverbWet,_tmpLimiterCeilingDb,_tmpBuiltIn,_tmpUpdatedAt);
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
  public Object getById(final String id, final Continuation<? super DspPresetEntity> $completion) {
    final String _sql = "SELECT * FROM dsp_presets WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DspPresetEntity>() {
      @Override
      @Nullable
      public DspPresetEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEqGains = CursorUtil.getColumnIndexOrThrow(_cursor, "eqGains");
          final int _cursorIndexOfBassBoost = CursorUtil.getColumnIndexOrThrow(_cursor, "bassBoost");
          final int _cursorIndexOfStereoWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "stereoWidth");
          final int _cursorIndexOfCompressorThresholdDb = CursorUtil.getColumnIndexOrThrow(_cursor, "compressorThresholdDb");
          final int _cursorIndexOfCompressorRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "compressorRatio");
          final int _cursorIndexOfCompressorAttackMs = CursorUtil.getColumnIndexOrThrow(_cursor, "compressorAttackMs");
          final int _cursorIndexOfCompressorReleaseMs = CursorUtil.getColumnIndexOrThrow(_cursor, "compressorReleaseMs");
          final int _cursorIndexOfReverbRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "reverbRoom");
          final int _cursorIndexOfReverbDamping = CursorUtil.getColumnIndexOrThrow(_cursor, "reverbDamping");
          final int _cursorIndexOfReverbWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "reverbWidth");
          final int _cursorIndexOfReverbWet = CursorUtil.getColumnIndexOrThrow(_cursor, "reverbWet");
          final int _cursorIndexOfLimiterCeilingDb = CursorUtil.getColumnIndexOrThrow(_cursor, "limiterCeilingDb");
          final int _cursorIndexOfBuiltIn = CursorUtil.getColumnIndexOrThrow(_cursor, "builtIn");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final DspPresetEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<Float> _tmpEqGains;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEqGains)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEqGains);
            }
            _tmpEqGains = __converters.stringToFloats(_tmp);
            final float _tmpBassBoost;
            _tmpBassBoost = _cursor.getFloat(_cursorIndexOfBassBoost);
            final float _tmpStereoWidth;
            _tmpStereoWidth = _cursor.getFloat(_cursorIndexOfStereoWidth);
            final float _tmpCompressorThresholdDb;
            _tmpCompressorThresholdDb = _cursor.getFloat(_cursorIndexOfCompressorThresholdDb);
            final float _tmpCompressorRatio;
            _tmpCompressorRatio = _cursor.getFloat(_cursorIndexOfCompressorRatio);
            final float _tmpCompressorAttackMs;
            _tmpCompressorAttackMs = _cursor.getFloat(_cursorIndexOfCompressorAttackMs);
            final float _tmpCompressorReleaseMs;
            _tmpCompressorReleaseMs = _cursor.getFloat(_cursorIndexOfCompressorReleaseMs);
            final float _tmpReverbRoom;
            _tmpReverbRoom = _cursor.getFloat(_cursorIndexOfReverbRoom);
            final float _tmpReverbDamping;
            _tmpReverbDamping = _cursor.getFloat(_cursorIndexOfReverbDamping);
            final float _tmpReverbWidth;
            _tmpReverbWidth = _cursor.getFloat(_cursorIndexOfReverbWidth);
            final float _tmpReverbWet;
            _tmpReverbWet = _cursor.getFloat(_cursorIndexOfReverbWet);
            final float _tmpLimiterCeilingDb;
            _tmpLimiterCeilingDb = _cursor.getFloat(_cursorIndexOfLimiterCeilingDb);
            final boolean _tmpBuiltIn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfBuiltIn);
            _tmpBuiltIn = _tmp_1 != 0;
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new DspPresetEntity(_tmpId,_tmpName,_tmpEqGains,_tmpBassBoost,_tmpStereoWidth,_tmpCompressorThresholdDb,_tmpCompressorRatio,_tmpCompressorAttackMs,_tmpCompressorReleaseMs,_tmpReverbRoom,_tmpReverbDamping,_tmpReverbWidth,_tmpReverbWet,_tmpLimiterCeilingDb,_tmpBuiltIn,_tmpUpdatedAt);
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
