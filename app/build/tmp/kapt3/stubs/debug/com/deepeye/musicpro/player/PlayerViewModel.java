package com.deepeye.musicpro.player;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.deepeye.musicpro.DeepEyeApp;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\'\u001a\u00020(J\u0006\u0010)\u001a\u00020(J\b\u0010*\u001a\u00020(H\u0014J\u0006\u0010+\u001a\u00020(J\u000e\u0010,\u001a\u00020(2\u0006\u0010-\u001a\u00020\u0007J\u0006\u0010.\u001a\u00020(J\u0006\u0010/\u001a\u00020(J\u0006\u00100\u001a\u00020(R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000eR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000eR\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000eR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 \u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020%0 \u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010#\u00a8\u00061"}, d2 = {"Lcom/deepeye/musicpro/player/PlayerViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_position", "Landroidx/lifecycle/MutableLiveData;", "", "controller", "Lcom/deepeye/musicpro/player/PlayerController;", "currentItem", "Landroidx/lifecycle/LiveData;", "Lcom/deepeye/musicpro/model/Track;", "getCurrentItem", "()Landroidx/lifecycle/LiveData;", "dspPreset", "", "getDspPreset", "duration", "getDuration", "handler", "Landroid/os/Handler;", "isPlaying", "", "position", "getPosition", "positionRunnable", "Ljava/lang/Runnable;", "repeatMode", "Lcom/deepeye/musicpro/player/RepeatMode;", "getRepeatMode", "state", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/deepeye/musicpro/player/PlayerState;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "v4aState", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "getV4aState", "download", "", "next", "onCleared", "previous", "seekTo", "positionMs", "toggle", "toggleFavorite", "toggleRepeat", "app_debug"})
public final class PlayerViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.player.PlayerController controller = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.player.PlayerState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.deepeye.musicpro.model.Track> currentItem = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Long> duration = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.deepeye.musicpro.player.RepeatMode> repeatMode = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Long> _position = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Long> position = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> v4aState = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> dspPreset = null;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable positionRunnable = null;
    
    public PlayerViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.player.PlayerState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.deepeye.musicpro.model.Track> getCurrentItem() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isPlaying() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Long> getDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.deepeye.musicpro.player.RepeatMode> getRepeatMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Long> getPosition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> getV4aState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getDspPreset() {
        return null;
    }
    
    public final void toggle() {
    }
    
    public final void next() {
    }
    
    public final void previous() {
    }
    
    public final void seekTo(long positionMs) {
    }
    
    public final void toggleRepeat() {
    }
    
    public final void toggleFavorite() {
    }
    
    public final void download() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}