package com.deepeye.musicpro.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.session.LibraryResult;
import androidx.media3.session.MediaLibraryService;
import androidx.media3.session.MediaLibraryService.LibraryParams;
import androidx.media3.session.MediaLibraryService.MediaLibrarySession;
import androidx.media3.session.MediaSession;
import androidx.media3.session.SessionCommand;
import com.deepeye.musicpro.DeepEyeApp;
import com.deepeye.musicpro.notification.PlayerNotificationManager;
import com.deepeye.musicpro.util.Logger;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import kotlinx.coroutines.Dispatchers;
import com.deepeye.musicpro.player.PlayerController;
import com.deepeye.musicpro.player.AutoRadioEngine;
import com.deepeye.musicpro.player.LazyStreamResolver;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001a2\u00020\u0001:\u0002\u0019\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/deepeye/musicpro/service/MusicPlayerService;", "Landroidx/media3/session/MediaLibraryService;", "()V", "autoRadio", "Lcom/deepeye/musicpro/player/AutoRadioEngine;", "lazyStreamResolver", "Lcom/deepeye/musicpro/player/LazyStreamResolver;", "mediaLibrarySession", "Landroidx/media3/session/MediaLibraryService$MediaLibrarySession;", "noisyReceiver", "Landroid/content/BroadcastReceiver;", "player", "Landroidx/media3/exoplayer/ExoPlayer;", "playerController", "Lcom/deepeye/musicpro/player/PlayerController;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "onCreate", "", "onDestroy", "onGetSession", "controllerInfo", "Landroidx/media3/session/MediaSession$ControllerInfo;", "requireSession", "Landroidx/media3/session/MediaSession;", "Callback", "Companion", "app_debug"})
public final class MusicPlayerService extends androidx.media3.session.MediaLibraryService {
    private androidx.media3.exoplayer.ExoPlayer player;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.session.MediaLibraryService.MediaLibrarySession mediaLibrarySession;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private com.deepeye.musicpro.player.PlayerController playerController;
    private com.deepeye.musicpro.player.AutoRadioEngine autoRadio;
    private com.deepeye.musicpro.player.LazyStreamResolver lazyStreamResolver;
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver noisyReceiver = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROOT_ID = "deepeye_root";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMMAND_DSP_TOGGLE = "com.deepeye.musicpro.DSP_TOGGLE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String COMMAND_DSP_PRESET = "com.deepeye.musicpro.DSP_PRESET";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MusicPlayerService";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_AGENT = "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36";
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.service.MusicPlayerService.Companion Companion = null;
    
    public MusicPlayerService() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public androidx.media3.session.MediaLibraryService.MediaLibrarySession onGetSession(@org.jetbrains.annotations.NotNull()
    androidx.media3.session.MediaSession.ControllerInfo controllerInfo) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final androidx.media3.session.MediaSession requireSession() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J.\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J.\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\n2\u0006\u0010\u0005\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/deepeye/musicpro/service/MusicPlayerService$Callback;", "Landroidx/media3/session/MediaLibraryService$MediaLibrarySession$Callback;", "(Lcom/deepeye/musicpro/service/MusicPlayerService;)V", "onConnect", "Landroidx/media3/session/MediaSession$ConnectionResult;", "session", "Landroidx/media3/session/MediaSession;", "controller", "Landroidx/media3/session/MediaSession$ControllerInfo;", "onCustomCommand", "Lcom/google/common/util/concurrent/ListenableFuture;", "Landroidx/media3/session/SessionResult;", "customCommand", "Landroidx/media3/session/SessionCommand;", "args", "Landroid/os/Bundle;", "onGetLibraryRoot", "Landroidx/media3/session/LibraryResult;", "Landroidx/media3/common/MediaItem;", "Landroidx/media3/session/MediaLibraryService$MediaLibrarySession;", "browser", "params", "Landroidx/media3/session/MediaLibraryService$LibraryParams;", "app_debug"})
    final class Callback implements androidx.media3.session.MediaLibraryService.MediaLibrarySession.Callback {
        
        public Callback() {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public androidx.media3.session.MediaSession.ConnectionResult onConnect(@org.jetbrains.annotations.NotNull()
        androidx.media3.session.MediaSession session, @org.jetbrains.annotations.NotNull()
        androidx.media3.session.MediaSession.ControllerInfo controller) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.google.common.util.concurrent.ListenableFuture<androidx.media3.session.SessionResult> onCustomCommand(@org.jetbrains.annotations.NotNull()
        androidx.media3.session.MediaSession session, @org.jetbrains.annotations.NotNull()
        androidx.media3.session.MediaSession.ControllerInfo controller, @org.jetbrains.annotations.NotNull()
        androidx.media3.session.SessionCommand customCommand, @org.jetbrains.annotations.NotNull()
        android.os.Bundle args) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.google.common.util.concurrent.ListenableFuture<androidx.media3.session.LibraryResult<androidx.media3.common.MediaItem>> onGetLibraryRoot(@org.jetbrains.annotations.NotNull()
        androidx.media3.session.MediaLibraryService.MediaLibrarySession session, @org.jetbrains.annotations.NotNull()
        androidx.media3.session.MediaSession.ControllerInfo browser, @org.jetbrains.annotations.Nullable()
        androidx.media3.session.MediaLibraryService.LibraryParams params) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/deepeye/musicpro/service/MusicPlayerService$Companion;", "", "()V", "COMMAND_DSP_PRESET", "", "COMMAND_DSP_TOGGLE", "ROOT_ID", "TAG", "USER_AGENT", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}