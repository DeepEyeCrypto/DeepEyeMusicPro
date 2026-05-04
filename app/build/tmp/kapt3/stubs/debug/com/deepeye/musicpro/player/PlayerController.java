package com.deepeye.musicpro.player;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionCommand;
import androidx.media3.session.SessionResult;
import androidx.media3.session.SessionToken;
import com.deepeye.musicpro.model.Track;
import com.deepeye.musicpro.service.MusicPlayerService;
import com.deepeye.musicpro.util.Logger;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0018\u001a\u00020\u00192\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u001bJ\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u0007J\u0014\u0010 \u001a\u00020\u00192\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"J\b\u0010$\u001a\u0004\u0018\u00010\u000bJ\b\u0010%\u001a\u0004\u0018\u00010&J\u0006\u0010\'\u001a\u00020(J\u0006\u0010)\u001a\u00020\u0019J\u0006\u0010*\u001a\u00020\u0019J\u001e\u0010+\u001a\u00020\u00192\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001e0\"2\b\b\u0002\u0010-\u001a\u00020(J\u0006\u0010.\u001a\u00020\u0019J\u0010\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u000201H\u0002J\u0006\u00102\u001a\u00020\u0019J\u0006\u00103\u001a\u00020\u0019J\u000e\u00104\u001a\u00020\u00192\u0006\u00105\u001a\u000206J\u000e\u00107\u001a\u00020\u00192\u0006\u00108\u001a\u000209J\u000e\u0010:\u001a\u00020\u00192\u0006\u0010;\u001a\u00020\u0007J\u0012\u0010<\u001a\u00020\u00192\b\u00100\u001a\u0004\u0018\u000101H\u0002J\u0006\u0010=\u001a\u00020\u0019R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015\u00a8\u0006>"}, d2 = {"Lcom/deepeye/musicpro/player/PlayerController;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_radioFetching", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_state", "Lcom/deepeye/musicpro/player/PlayerState;", "controller", "Landroidx/media3/session/MediaController;", "controllerFuture", "Lcom/google/common/util/concurrent/ListenableFuture;", "listener", "Landroidx/media3/common/Player$Listener;", "queueManager", "Lcom/deepeye/musicpro/player/QueueManager;", "radioFetching", "Lkotlinx/coroutines/flow/StateFlow;", "getRadioFetching", "()Lkotlinx/coroutines/flow/StateFlow;", "state", "getState", "connect", "", "onConnected", "Lkotlin/Function0;", "enqueue", "track", "Lcom/deepeye/musicpro/model/Track;", "playIfEmpty", "enqueueRelated", "results", "", "Lcom/deepeye/musicpro/model/SearchResult;", "getController", "getCurrentVideoId", "", "getQueueRemainingCount", "", "next", "pause", "playQueue", "tracks", "startIndex", "previous", "publishState", "player", "Landroidx/media3/common/Player;", "release", "resume", "seekTo", "positionMs", "", "setRepeatMode", "mode", "Lcom/deepeye/musicpro/player/RepeatMode;", "setShuffle", "enabled", "syncQueueManager", "toggle", "app_debug"})
public final class PlayerController {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.deepeye.musicpro.player.PlayerState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.player.PlayerState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _radioFetching = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> radioFetching = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.session.MediaController controller;
    @org.jetbrains.annotations.Nullable()
    private com.google.common.util.concurrent.ListenableFuture<androidx.media3.session.MediaController> controllerFuture;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.player.QueueManager queueManager = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.common.Player.Listener listener = null;
    
    public PlayerController(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.player.PlayerState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getRadioFetching() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.media3.session.MediaController getController() {
        return null;
    }
    
    public final void connect(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConnected) {
    }
    
    public final void playQueue(@org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.Track> tracks, int startIndex) {
    }
    
    public final void enqueue(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.model.Track track, boolean playIfEmpty) {
    }
    
    public final void enqueueRelated(@org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.SearchResult> results) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCurrentVideoId() {
        return null;
    }
    
    public final void pause() {
    }
    
    public final void resume() {
    }
    
    public final void toggle() {
    }
    
    public final void next() {
    }
    
    public final void previous() {
    }
    
    public final void seekTo(long positionMs) {
    }
    
    public final void setShuffle(boolean enabled) {
    }
    
    public final void setRepeatMode(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.player.RepeatMode mode) {
    }
    
    public final void release() {
    }
    
    public final int getQueueRemainingCount() {
        return 0;
    }
    
    private final void syncQueueManager(androidx.media3.common.Player player) {
    }
    
    private final void publishState(androidx.media3.common.Player player) {
    }
}