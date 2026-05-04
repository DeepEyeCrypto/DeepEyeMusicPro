package com.deepeye.musicpro.ui.player;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.deepeye.musicpro.DeepEyeApp;
import com.deepeye.musicpro.player.PlayerController;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0012H\u0014J\u0006\u0010\u0014\u001a\u00020\u0012J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0012R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r\u00a8\u0006\u0019"}, d2 = {"Lcom/deepeye/musicpro/ui/player/NowPlayingViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "controller", "Lcom/deepeye/musicpro/player/PlayerController;", "getController", "()Lcom/deepeye/musicpro/player/PlayerController;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/deepeye/musicpro/player/PlayerState;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "v4aState", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "getV4aState", "next", "", "onCleared", "previous", "seekTo", "positionMs", "", "toggle", "app_debug"})
public final class NowPlayingViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.player.PlayerController controller = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.player.PlayerState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> v4aState = null;
    
    public NowPlayingViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.player.PlayerController getController() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.player.PlayerState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> getV4aState() {
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
    
    @java.lang.Override()
    protected void onCleared() {
    }
}