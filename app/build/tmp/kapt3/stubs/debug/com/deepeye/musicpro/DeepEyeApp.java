package com.deepeye.musicpro;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.room.Room;
import com.deepeye.musicpro.adblock.AdBlockEngine;
import com.deepeye.musicpro.db.AppDatabase;
import com.deepeye.musicpro.dsp.DSPManager;
import com.deepeye.musicpro.dsp.v4a.V4AAssetLoader;
import com.deepeye.musicpro.dsp.v4a.V4AEngine;
import com.deepeye.musicpro.extractor.DownloaderImpl;
import com.deepeye.musicpro.extractor.SearchService;
import com.deepeye.musicpro.extractor.StreamExtractor;
import com.deepeye.musicpro.repository.AppRepository;
import com.deepeye.musicpro.repository.DSPRepository;
import com.deepeye.musicpro.repository.DownloadRepository;
import com.deepeye.musicpro.repository.PlayerRepository;
import com.deepeye.musicpro.repository.SearchRepository;
import com.deepeye.musicpro.repository.SettingsRepository;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 Q2\u00020\u0001:\u0001QB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010N\u001a\u00020OH\u0002J\b\u0010P\u001a\u00020OH\u0016R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0012\u001a\u00020\u00138FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0017\u001a\u00020\u00188FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\b\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001c\u001a\u00020\u001d8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b \u0010\b\u001a\u0004\b\u001e\u0010\u001fR\u001b\u0010!\u001a\u00020\"8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b%\u0010\b\u001a\u0004\b#\u0010$R\u001b\u0010&\u001a\u00020\'8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b*\u0010\b\u001a\u0004\b(\u0010)R\u001b\u0010+\u001a\u00020,8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b/\u0010\b\u001a\u0004\b-\u0010.R\u001b\u00100\u001a\u0002018FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b4\u0010\b\u001a\u0004\b2\u00103R\u001b\u00105\u001a\u0002068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b9\u0010\b\u001a\u0004\b7\u00108R\u001b\u0010:\u001a\u00020;8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b>\u0010\b\u001a\u0004\b<\u0010=R\u001b\u0010?\u001a\u00020@8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bC\u0010\b\u001a\u0004\bA\u0010BR\u001b\u0010D\u001a\u00020E8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bH\u0010\b\u001a\u0004\bF\u0010GR\u001b\u0010I\u001a\u00020J8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bM\u0010\b\u001a\u0004\bK\u0010L\u00a8\u0006R"}, d2 = {"Lcom/deepeye/musicpro/DeepEyeApp;", "Landroid/app/Application;", "()V", "adBlockEngine", "Lcom/deepeye/musicpro/adblock/AdBlockEngine;", "getAdBlockEngine", "()Lcom/deepeye/musicpro/adblock/AdBlockEngine;", "adBlockEngine$delegate", "Lkotlin/Lazy;", "appRepository", "Lcom/deepeye/musicpro/repository/AppRepository;", "getAppRepository", "()Lcom/deepeye/musicpro/repository/AppRepository;", "appRepository$delegate", "applicationScope", "Lkotlinx/coroutines/CoroutineScope;", "getApplicationScope", "()Lkotlinx/coroutines/CoroutineScope;", "database", "Lcom/deepeye/musicpro/db/AppDatabase;", "getDatabase", "()Lcom/deepeye/musicpro/db/AppDatabase;", "database$delegate", "downloadRepository", "Lcom/deepeye/musicpro/repository/DownloadRepository;", "getDownloadRepository", "()Lcom/deepeye/musicpro/repository/DownloadRepository;", "downloadRepository$delegate", "downloader", "Lcom/deepeye/musicpro/extractor/DownloaderImpl;", "getDownloader", "()Lcom/deepeye/musicpro/extractor/DownloaderImpl;", "downloader$delegate", "dspManager", "Lcom/deepeye/musicpro/dsp/DSPManager;", "getDspManager", "()Lcom/deepeye/musicpro/dsp/DSPManager;", "dspManager$delegate", "dspRepository", "Lcom/deepeye/musicpro/repository/DSPRepository;", "getDspRepository", "()Lcom/deepeye/musicpro/repository/DSPRepository;", "dspRepository$delegate", "httpClient", "Lokhttp3/OkHttpClient;", "getHttpClient", "()Lokhttp3/OkHttpClient;", "httpClient$delegate", "playerRepository", "Lcom/deepeye/musicpro/repository/PlayerRepository;", "getPlayerRepository", "()Lcom/deepeye/musicpro/repository/PlayerRepository;", "playerRepository$delegate", "searchRepository", "Lcom/deepeye/musicpro/repository/SearchRepository;", "getSearchRepository", "()Lcom/deepeye/musicpro/repository/SearchRepository;", "searchRepository$delegate", "searchService", "Lcom/deepeye/musicpro/extractor/SearchService;", "getSearchService", "()Lcom/deepeye/musicpro/extractor/SearchService;", "searchService$delegate", "settingsRepository", "Lcom/deepeye/musicpro/repository/SettingsRepository;", "getSettingsRepository", "()Lcom/deepeye/musicpro/repository/SettingsRepository;", "settingsRepository$delegate", "streamExtractor", "Lcom/deepeye/musicpro/extractor/StreamExtractor;", "getStreamExtractor", "()Lcom/deepeye/musicpro/extractor/StreamExtractor;", "streamExtractor$delegate", "v4aEngine", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngine;", "getV4aEngine", "()Lcom/deepeye/musicpro/dsp/v4a/V4AEngine;", "v4aEngine$delegate", "createNotificationChannels", "", "onCreate", "Companion", "app_debug"})
public final class DeepEyeApp extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope applicationScope = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy httpClient$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy settingsRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy adBlockEngine$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy downloader$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy streamExtractor$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy searchService$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy searchRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy dspManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy v4aEngine$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy dspRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy downloadRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy playerRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy appRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_PLAYBACK = "deepeye_playback";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_DOWNLOADS = "deepeye_downloads";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_DIAGNOSTICS = "deepeye_diagnostics";
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.DeepEyeApp.Companion Companion = null;
    
    public DeepEyeApp() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.CoroutineScope getApplicationScope() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient getHttpClient() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.db.AppDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.repository.SettingsRepository getSettingsRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.adblock.AdBlockEngine getAdBlockEngine() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.extractor.DownloaderImpl getDownloader() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.extractor.StreamExtractor getStreamExtractor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.extractor.SearchService getSearchService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.repository.SearchRepository getSearchRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.DSPManager getDspManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEngine getV4aEngine() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.repository.DSPRepository getDspRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.repository.DownloadRepository getDownloadRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.repository.PlayerRepository getPlayerRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.repository.AppRepository getAppRepository() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void createNotificationChannels() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/deepeye/musicpro/DeepEyeApp$Companion;", "", "()V", "CHANNEL_DIAGNOSTICS", "", "CHANNEL_DOWNLOADS", "CHANNEL_PLAYBACK", "from", "Lcom/deepeye/musicpro/DeepEyeApp;", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.deepeye.musicpro.DeepEyeApp from(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}