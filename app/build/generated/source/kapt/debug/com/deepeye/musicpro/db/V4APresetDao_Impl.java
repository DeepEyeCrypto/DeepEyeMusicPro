package com.deepeye.musicpro.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
public final class V4APresetDao_Impl implements V4APresetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<V4APresetEntity> __insertionAdapterOfV4APresetEntity;

  private final Converters __converters = new Converters();

  public V4APresetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfV4APresetEntity = new EntityInsertionAdapter<V4APresetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `v4a_presets` (`id`,`name`,`isBuiltIn`,`eqGains`,`convolverIR`,`ddcProfile`,`fetAttack`,`fetRelease`,`fetRatio`,`fetThreshold`,`fetKnee`,`tubeWarmth`,`reverbRoom`,`masterEnabled`,`enabledEffectsCsv`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final V4APresetEntity entity) {
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
        final int _tmp = entity.isBuiltIn() ? 1 : 0;
        statement.bindLong(3, _tmp);
        final String _tmp_1 = __converters.floatsToString(entity.getEqGains());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        if (entity.getConvolverIR() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getConvolverIR());
        }
        if (entity.getDdcProfile() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDdcProfile());
        }
        statement.bindDouble(7, entity.getFetAttack());
        statement.bindDouble(8, entity.getFetRelease());
        statement.bindDouble(9, entity.getFetRatio());
        statement.bindDouble(10, entity.getFetThreshold());
        statement.bindDouble(11, entity.getFetKnee());
        statement.bindDouble(12, entity.getTubeWarmth());
        statement.bindDouble(13, entity.getReverbRoom());
        final int _tmp_2 = entity.getMasterEnabled() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        if (entity.getEnabledEffectsCsv() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getEnabledEffectsCsv());
        }
        statement.bindLong(16, entity.getUpdatedAt());
      }
    };
  }

  @Override
  public Object upsert(final V4APresetEntity preset, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfV4APresetEntity.insert(preset);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertAll(final List<V4APresetEntity> presets,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfV4APresetEntity.insert(presets);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<V4APresetEntity>> observePresets() {
    final String _sql = "SELECT `v4a_presets`.`id` AS `id`, `v4a_presets`.`name` AS `name`, `v4a_presets`.`isBuiltIn` AS `isBuiltIn`, `v4a_presets`.`eqGains` AS `eqGains`, `v4a_presets`.`convolverIR` AS `convolverIR`, `v4a_presets`.`ddcProfile` AS `ddcProfile`, `v4a_presets`.`fetAttack` AS `fetAttack`, `v4a_presets`.`fetRelease` AS `fetRelease`, `v4a_presets`.`fetRatio` AS `fetRatio`, `v4a_presets`.`fetThreshold` AS `fetThreshold`, `v4a_presets`.`fetKnee` AS `fetKnee`, `v4a_presets`.`tubeWarmth` AS `tubeWarmth`, `v4a_presets`.`reverbRoom` AS `reverbRoom`, `v4a_presets`.`masterEnabled` AS `masterEnabled`, `v4a_presets`.`enabledEffectsCsv` AS `enabledEffectsCsv`, `v4a_presets`.`updatedAt` AS `updatedAt` FROM v4a_presets ORDER BY isBuiltIn DESC, name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"v4a_presets"}, new Callable<List<V4APresetEntity>>() {
      @Override
      @NonNull
      public List<V4APresetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfIsBuiltIn = 2;
          final int _cursorIndexOfEqGains = 3;
          final int _cursorIndexOfConvolverIR = 4;
          final int _cursorIndexOfDdcProfile = 5;
          final int _cursorIndexOfFetAttack = 6;
          final int _cursorIndexOfFetRelease = 7;
          final int _cursorIndexOfFetRatio = 8;
          final int _cursorIndexOfFetThreshold = 9;
          final int _cursorIndexOfFetKnee = 10;
          final int _cursorIndexOfTubeWarmth = 11;
          final int _cursorIndexOfReverbRoom = 12;
          final int _cursorIndexOfMasterEnabled = 13;
          final int _cursorIndexOfEnabledEffectsCsv = 14;
          final int _cursorIndexOfUpdatedAt = 15;
          final List<V4APresetEntity> _result = new ArrayList<V4APresetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final V4APresetEntity _item;
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
            final boolean _tmpIsBuiltIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBuiltIn);
            _tmpIsBuiltIn = _tmp != 0;
            final List<Float> _tmpEqGains;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEqGains)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEqGains);
            }
            _tmpEqGains = __converters.stringToFloats(_tmp_1);
            final String _tmpConvolverIR;
            if (_cursor.isNull(_cursorIndexOfConvolverIR)) {
              _tmpConvolverIR = null;
            } else {
              _tmpConvolverIR = _cursor.getString(_cursorIndexOfConvolverIR);
            }
            final String _tmpDdcProfile;
            if (_cursor.isNull(_cursorIndexOfDdcProfile)) {
              _tmpDdcProfile = null;
            } else {
              _tmpDdcProfile = _cursor.getString(_cursorIndexOfDdcProfile);
            }
            final float _tmpFetAttack;
            _tmpFetAttack = _cursor.getFloat(_cursorIndexOfFetAttack);
            final float _tmpFetRelease;
            _tmpFetRelease = _cursor.getFloat(_cursorIndexOfFetRelease);
            final float _tmpFetRatio;
            _tmpFetRatio = _cursor.getFloat(_cursorIndexOfFetRatio);
            final float _tmpFetThreshold;
            _tmpFetThreshold = _cursor.getFloat(_cursorIndexOfFetThreshold);
            final float _tmpFetKnee;
            _tmpFetKnee = _cursor.getFloat(_cursorIndexOfFetKnee);
            final float _tmpTubeWarmth;
            _tmpTubeWarmth = _cursor.getFloat(_cursorIndexOfTubeWarmth);
            final float _tmpReverbRoom;
            _tmpReverbRoom = _cursor.getFloat(_cursorIndexOfReverbRoom);
            final boolean _tmpMasterEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMasterEnabled);
            _tmpMasterEnabled = _tmp_2 != 0;
            final String _tmpEnabledEffectsCsv;
            if (_cursor.isNull(_cursorIndexOfEnabledEffectsCsv)) {
              _tmpEnabledEffectsCsv = null;
            } else {
              _tmpEnabledEffectsCsv = _cursor.getString(_cursorIndexOfEnabledEffectsCsv);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new V4APresetEntity(_tmpId,_tmpName,_tmpIsBuiltIn,_tmpEqGains,_tmpConvolverIR,_tmpDdcProfile,_tmpFetAttack,_tmpFetRelease,_tmpFetRatio,_tmpFetThreshold,_tmpFetKnee,_tmpTubeWarmth,_tmpReverbRoom,_tmpMasterEnabled,_tmpEnabledEffectsCsv,_tmpUpdatedAt);
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
  public Object getById(final String id, final Continuation<? super V4APresetEntity> $completion) {
    final String _sql = "SELECT * FROM v4a_presets WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<V4APresetEntity>() {
      @Override
      @Nullable
      public V4APresetEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIsBuiltIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isBuiltIn");
          final int _cursorIndexOfEqGains = CursorUtil.getColumnIndexOrThrow(_cursor, "eqGains");
          final int _cursorIndexOfConvolverIR = CursorUtil.getColumnIndexOrThrow(_cursor, "convolverIR");
          final int _cursorIndexOfDdcProfile = CursorUtil.getColumnIndexOrThrow(_cursor, "ddcProfile");
          final int _cursorIndexOfFetAttack = CursorUtil.getColumnIndexOrThrow(_cursor, "fetAttack");
          final int _cursorIndexOfFetRelease = CursorUtil.getColumnIndexOrThrow(_cursor, "fetRelease");
          final int _cursorIndexOfFetRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "fetRatio");
          final int _cursorIndexOfFetThreshold = CursorUtil.getColumnIndexOrThrow(_cursor, "fetThreshold");
          final int _cursorIndexOfFetKnee = CursorUtil.getColumnIndexOrThrow(_cursor, "fetKnee");
          final int _cursorIndexOfTubeWarmth = CursorUtil.getColumnIndexOrThrow(_cursor, "tubeWarmth");
          final int _cursorIndexOfReverbRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "reverbRoom");
          final int _cursorIndexOfMasterEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "masterEnabled");
          final int _cursorIndexOfEnabledEffectsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "enabledEffectsCsv");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final V4APresetEntity _result;
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
            final boolean _tmpIsBuiltIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBuiltIn);
            _tmpIsBuiltIn = _tmp != 0;
            final List<Float> _tmpEqGains;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEqGains)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEqGains);
            }
            _tmpEqGains = __converters.stringToFloats(_tmp_1);
            final String _tmpConvolverIR;
            if (_cursor.isNull(_cursorIndexOfConvolverIR)) {
              _tmpConvolverIR = null;
            } else {
              _tmpConvolverIR = _cursor.getString(_cursorIndexOfConvolverIR);
            }
            final String _tmpDdcProfile;
            if (_cursor.isNull(_cursorIndexOfDdcProfile)) {
              _tmpDdcProfile = null;
            } else {
              _tmpDdcProfile = _cursor.getString(_cursorIndexOfDdcProfile);
            }
            final float _tmpFetAttack;
            _tmpFetAttack = _cursor.getFloat(_cursorIndexOfFetAttack);
            final float _tmpFetRelease;
            _tmpFetRelease = _cursor.getFloat(_cursorIndexOfFetRelease);
            final float _tmpFetRatio;
            _tmpFetRatio = _cursor.getFloat(_cursorIndexOfFetRatio);
            final float _tmpFetThreshold;
            _tmpFetThreshold = _cursor.getFloat(_cursorIndexOfFetThreshold);
            final float _tmpFetKnee;
            _tmpFetKnee = _cursor.getFloat(_cursorIndexOfFetKnee);
            final float _tmpTubeWarmth;
            _tmpTubeWarmth = _cursor.getFloat(_cursorIndexOfTubeWarmth);
            final float _tmpReverbRoom;
            _tmpReverbRoom = _cursor.getFloat(_cursorIndexOfReverbRoom);
            final boolean _tmpMasterEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMasterEnabled);
            _tmpMasterEnabled = _tmp_2 != 0;
            final String _tmpEnabledEffectsCsv;
            if (_cursor.isNull(_cursorIndexOfEnabledEffectsCsv)) {
              _tmpEnabledEffectsCsv = null;
            } else {
              _tmpEnabledEffectsCsv = _cursor.getString(_cursorIndexOfEnabledEffectsCsv);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new V4APresetEntity(_tmpId,_tmpName,_tmpIsBuiltIn,_tmpEqGains,_tmpConvolverIR,_tmpDdcProfile,_tmpFetAttack,_tmpFetRelease,_tmpFetRatio,_tmpFetThreshold,_tmpFetKnee,_tmpTubeWarmth,_tmpReverbRoom,_tmpMasterEnabled,_tmpEnabledEffectsCsv,_tmpUpdatedAt);
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
