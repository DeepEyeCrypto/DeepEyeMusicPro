package com.deepeye.musicpro.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile TrackDao _trackDao;

  private volatile FavoriteDao _favoriteDao;

  private volatile SearchHistoryDao _searchHistoryDao;

  private volatile PresetDao _presetDao;

  private volatile V4APresetDao _v4APresetDao;

  private volatile PlaylistDao _playlistDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_tracks` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, `durationMs` INTEGER NOT NULL, `thumbnailUrl` TEXT, `streamUrl` TEXT, `webUrl` TEXT, `localUri` TEXT, `mimeType` TEXT, `bitrate` INTEGER NOT NULL, `source` TEXT NOT NULL, `isDownloaded` INTEGER NOT NULL, `downloadedAt` INTEGER NOT NULL, `lastPlayedAt` INTEGER NOT NULL, `playCount` INTEGER NOT NULL, `fileSizeBytes` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorite_tracks` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, `thumbnailUrl` TEXT, `webUrl` TEXT, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `search_history` (`query` TEXT NOT NULL, `lastSearchedAt` INTEGER NOT NULL, `hitCount` INTEGER NOT NULL, PRIMARY KEY(`query`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dsp_presets` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `eqGains` TEXT NOT NULL, `bassBoost` REAL NOT NULL, `stereoWidth` REAL NOT NULL, `compressorThresholdDb` REAL NOT NULL, `compressorRatio` REAL NOT NULL, `compressorAttackMs` REAL NOT NULL, `compressorReleaseMs` REAL NOT NULL, `reverbRoom` REAL NOT NULL, `reverbDamping` REAL NOT NULL, `reverbWidth` REAL NOT NULL, `reverbWet` REAL NOT NULL, `limiterCeilingDb` REAL NOT NULL, `builtIn` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `v4a_presets` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `isBuiltIn` INTEGER NOT NULL, `eqGains` TEXT NOT NULL, `convolverIR` TEXT, `ddcProfile` TEXT, `fetAttack` REAL NOT NULL, `fetRelease` REAL NOT NULL, `fetRatio` REAL NOT NULL, `fetThreshold` REAL NOT NULL, `fetKnee` REAL NOT NULL, `tubeWarmth` REAL NOT NULL, `reverbRoom` REAL NOT NULL, `masterEnabled` INTEGER NOT NULL, `enabledEffectsCsv` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `playlists` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `playlist_tracks` (`playlistId` TEXT NOT NULL, `trackId` TEXT NOT NULL, `position` INTEGER NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`playlistId`, `trackId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd9f2ad70917549d8f3994c65fc58bbd8')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `cached_tracks`");
        db.execSQL("DROP TABLE IF EXISTS `favorite_tracks`");
        db.execSQL("DROP TABLE IF EXISTS `search_history`");
        db.execSQL("DROP TABLE IF EXISTS `dsp_presets`");
        db.execSQL("DROP TABLE IF EXISTS `v4a_presets`");
        db.execSQL("DROP TABLE IF EXISTS `playlists`");
        db.execSQL("DROP TABLE IF EXISTS `playlist_tracks`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCachedTracks = new HashMap<String, TableInfo.Column>(16);
        _columnsCachedTracks.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("thumbnailUrl", new TableInfo.Column("thumbnailUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("streamUrl", new TableInfo.Column("streamUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("webUrl", new TableInfo.Column("webUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("localUri", new TableInfo.Column("localUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("mimeType", new TableInfo.Column("mimeType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("bitrate", new TableInfo.Column("bitrate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("isDownloaded", new TableInfo.Column("isDownloaded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("downloadedAt", new TableInfo.Column("downloadedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("lastPlayedAt", new TableInfo.Column("lastPlayedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("playCount", new TableInfo.Column("playCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedTracks.put("fileSizeBytes", new TableInfo.Column("fileSizeBytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedTracks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedTracks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedTracks = new TableInfo("cached_tracks", _columnsCachedTracks, _foreignKeysCachedTracks, _indicesCachedTracks);
        final TableInfo _existingCachedTracks = TableInfo.read(db, "cached_tracks");
        if (!_infoCachedTracks.equals(_existingCachedTracks)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_tracks(com.deepeye.musicpro.db.CachedTrack).\n"
                  + " Expected:\n" + _infoCachedTracks + "\n"
                  + " Found:\n" + _existingCachedTracks);
        }
        final HashMap<String, TableInfo.Column> _columnsFavoriteTracks = new HashMap<String, TableInfo.Column>(6);
        _columnsFavoriteTracks.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteTracks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteTracks.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteTracks.put("thumbnailUrl", new TableInfo.Column("thumbnailUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteTracks.put("webUrl", new TableInfo.Column("webUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteTracks.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoriteTracks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavoriteTracks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavoriteTracks = new TableInfo("favorite_tracks", _columnsFavoriteTracks, _foreignKeysFavoriteTracks, _indicesFavoriteTracks);
        final TableInfo _existingFavoriteTracks = TableInfo.read(db, "favorite_tracks");
        if (!_infoFavoriteTracks.equals(_existingFavoriteTracks)) {
          return new RoomOpenHelper.ValidationResult(false, "favorite_tracks(com.deepeye.musicpro.db.FavoriteTrack).\n"
                  + " Expected:\n" + _infoFavoriteTracks + "\n"
                  + " Found:\n" + _existingFavoriteTracks);
        }
        final HashMap<String, TableInfo.Column> _columnsSearchHistory = new HashMap<String, TableInfo.Column>(3);
        _columnsSearchHistory.put("query", new TableInfo.Column("query", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSearchHistory.put("lastSearchedAt", new TableInfo.Column("lastSearchedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSearchHistory.put("hitCount", new TableInfo.Column("hitCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSearchHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSearchHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSearchHistory = new TableInfo("search_history", _columnsSearchHistory, _foreignKeysSearchHistory, _indicesSearchHistory);
        final TableInfo _existingSearchHistory = TableInfo.read(db, "search_history");
        if (!_infoSearchHistory.equals(_existingSearchHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "search_history(com.deepeye.musicpro.db.SearchHistoryEntry).\n"
                  + " Expected:\n" + _infoSearchHistory + "\n"
                  + " Found:\n" + _existingSearchHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsDspPresets = new HashMap<String, TableInfo.Column>(16);
        _columnsDspPresets.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("eqGains", new TableInfo.Column("eqGains", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("bassBoost", new TableInfo.Column("bassBoost", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("stereoWidth", new TableInfo.Column("stereoWidth", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("compressorThresholdDb", new TableInfo.Column("compressorThresholdDb", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("compressorRatio", new TableInfo.Column("compressorRatio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("compressorAttackMs", new TableInfo.Column("compressorAttackMs", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("compressorReleaseMs", new TableInfo.Column("compressorReleaseMs", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("reverbRoom", new TableInfo.Column("reverbRoom", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("reverbDamping", new TableInfo.Column("reverbDamping", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("reverbWidth", new TableInfo.Column("reverbWidth", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("reverbWet", new TableInfo.Column("reverbWet", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("limiterCeilingDb", new TableInfo.Column("limiterCeilingDb", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("builtIn", new TableInfo.Column("builtIn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDspPresets.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDspPresets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDspPresets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDspPresets = new TableInfo("dsp_presets", _columnsDspPresets, _foreignKeysDspPresets, _indicesDspPresets);
        final TableInfo _existingDspPresets = TableInfo.read(db, "dsp_presets");
        if (!_infoDspPresets.equals(_existingDspPresets)) {
          return new RoomOpenHelper.ValidationResult(false, "dsp_presets(com.deepeye.musicpro.db.DspPresetEntity).\n"
                  + " Expected:\n" + _infoDspPresets + "\n"
                  + " Found:\n" + _existingDspPresets);
        }
        final HashMap<String, TableInfo.Column> _columnsV4aPresets = new HashMap<String, TableInfo.Column>(16);
        _columnsV4aPresets.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("isBuiltIn", new TableInfo.Column("isBuiltIn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("eqGains", new TableInfo.Column("eqGains", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("convolverIR", new TableInfo.Column("convolverIR", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("ddcProfile", new TableInfo.Column("ddcProfile", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("fetAttack", new TableInfo.Column("fetAttack", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("fetRelease", new TableInfo.Column("fetRelease", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("fetRatio", new TableInfo.Column("fetRatio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("fetThreshold", new TableInfo.Column("fetThreshold", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("fetKnee", new TableInfo.Column("fetKnee", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("tubeWarmth", new TableInfo.Column("tubeWarmth", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("reverbRoom", new TableInfo.Column("reverbRoom", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("masterEnabled", new TableInfo.Column("masterEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("enabledEffectsCsv", new TableInfo.Column("enabledEffectsCsv", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsV4aPresets.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysV4aPresets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesV4aPresets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoV4aPresets = new TableInfo("v4a_presets", _columnsV4aPresets, _foreignKeysV4aPresets, _indicesV4aPresets);
        final TableInfo _existingV4aPresets = TableInfo.read(db, "v4a_presets");
        if (!_infoV4aPresets.equals(_existingV4aPresets)) {
          return new RoomOpenHelper.ValidationResult(false, "v4a_presets(com.deepeye.musicpro.db.V4APresetEntity).\n"
                  + " Expected:\n" + _infoV4aPresets + "\n"
                  + " Found:\n" + _existingV4aPresets);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaylists = new HashMap<String, TableInfo.Column>(5);
        _columnsPlaylists.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylists.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaylists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaylists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaylists = new TableInfo("playlists", _columnsPlaylists, _foreignKeysPlaylists, _indicesPlaylists);
        final TableInfo _existingPlaylists = TableInfo.read(db, "playlists");
        if (!_infoPlaylists.equals(_existingPlaylists)) {
          return new RoomOpenHelper.ValidationResult(false, "playlists(com.deepeye.musicpro.db.PlaylistEntity).\n"
                  + " Expected:\n" + _infoPlaylists + "\n"
                  + " Found:\n" + _existingPlaylists);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaylistTracks = new HashMap<String, TableInfo.Column>(4);
        _columnsPlaylistTracks.put("playlistId", new TableInfo.Column("playlistId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistTracks.put("trackId", new TableInfo.Column("trackId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistTracks.put("position", new TableInfo.Column("position", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaylistTracks.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaylistTracks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaylistTracks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaylistTracks = new TableInfo("playlist_tracks", _columnsPlaylistTracks, _foreignKeysPlaylistTracks, _indicesPlaylistTracks);
        final TableInfo _existingPlaylistTracks = TableInfo.read(db, "playlist_tracks");
        if (!_infoPlaylistTracks.equals(_existingPlaylistTracks)) {
          return new RoomOpenHelper.ValidationResult(false, "playlist_tracks(com.deepeye.musicpro.db.PlaylistTrackCrossRef).\n"
                  + " Expected:\n" + _infoPlaylistTracks + "\n"
                  + " Found:\n" + _existingPlaylistTracks);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d9f2ad70917549d8f3994c65fc58bbd8", "5e41c4e3839b1814090ed6c1b0987ca4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cached_tracks","favorite_tracks","search_history","dsp_presets","v4a_presets","playlists","playlist_tracks");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `cached_tracks`");
      _db.execSQL("DELETE FROM `favorite_tracks`");
      _db.execSQL("DELETE FROM `search_history`");
      _db.execSQL("DELETE FROM `dsp_presets`");
      _db.execSQL("DELETE FROM `v4a_presets`");
      _db.execSQL("DELETE FROM `playlists`");
      _db.execSQL("DELETE FROM `playlist_tracks`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TrackDao.class, TrackDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FavoriteDao.class, FavoriteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SearchHistoryDao.class, SearchHistoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PresetDao.class, PresetDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(V4APresetDao.class, V4APresetDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PlaylistDao.class, PlaylistDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
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
  public TrackDao trackDao() {
    if (_trackDao != null) {
      return _trackDao;
    } else {
      synchronized(this) {
        if(_trackDao == null) {
          _trackDao = new TrackDao_Impl(this);
        }
        return _trackDao;
      }
    }
  }

  @Override
  public FavoriteDao favoriteDao() {
    if (_favoriteDao != null) {
      return _favoriteDao;
    } else {
      synchronized(this) {
        if(_favoriteDao == null) {
          _favoriteDao = new FavoriteDao_Impl(this);
        }
        return _favoriteDao;
      }
    }
  }

  @Override
  public SearchHistoryDao searchHistoryDao() {
    if (_searchHistoryDao != null) {
      return _searchHistoryDao;
    } else {
      synchronized(this) {
        if(_searchHistoryDao == null) {
          _searchHistoryDao = new SearchHistoryDao_Impl(this);
        }
        return _searchHistoryDao;
      }
    }
  }

  @Override
  public PresetDao presetDao() {
    if (_presetDao != null) {
      return _presetDao;
    } else {
      synchronized(this) {
        if(_presetDao == null) {
          _presetDao = new PresetDao_Impl(this);
        }
        return _presetDao;
      }
    }
  }

  @Override
  public V4APresetDao v4aPresetDao() {
    if (_v4APresetDao != null) {
      return _v4APresetDao;
    } else {
      synchronized(this) {
        if(_v4APresetDao == null) {
          _v4APresetDao = new V4APresetDao_Impl(this);
        }
        return _v4APresetDao;
      }
    }
  }

  @Override
  public PlaylistDao playlistDao() {
    if (_playlistDao != null) {
      return _playlistDao;
    } else {
      synchronized(this) {
        if(_playlistDao == null) {
          _playlistDao = new PlaylistDao_Impl(this);
        }
        return _playlistDao;
      }
    }
  }
}
