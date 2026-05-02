package com.deepeye.musicpro.ui.v4a;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.deepeye.musicpro.DeepEyeApp;
import com.deepeye.musicpro.dsp.v4a.DDCProfile;
import com.deepeye.musicpro.dsp.v4a.V4AAssetLoader;
import com.deepeye.musicpro.dsp.v4a.V4AEffect;
import com.deepeye.musicpro.dsp.v4a.V4APreset;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001aJ\u000e\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!J\b\u0010\"\u001a\u00020\u001dH\u0002J\u0010\u0010#\u001a\u00020\u001d2\b\b\u0002\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020\u001d2\u0006\u0010\'\u001a\u00020\bJ\u000e\u0010(\u001a\u00020\u001d2\u0006\u0010\'\u001a\u00020\bJ\u0016\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-J&\u0010.\u001a\u00020\u001d2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002002\u0006\u00102\u001a\u0002002\u0006\u00103\u001a\u000200J\u000e\u00104\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020-J\u000e\u00105\u001a\u00020\u001d2\u0006\u00106\u001a\u000200R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011\u00a8\u00067"}, d2 = {"Lcom/deepeye/musicpro/ui/v4a/V4AViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_ddcFiles", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Ljava/io/File;", "_irsFiles", "app", "Lcom/deepeye/musicpro/DeepEyeApp;", "database", "Lcom/deepeye/musicpro/db/AppDatabase;", "ddcFiles", "Lkotlinx/coroutines/flow/StateFlow;", "getDdcFiles", "()Lkotlinx/coroutines/flow/StateFlow;", "engine", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngine;", "engineState", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "getEngineState", "irsFiles", "getIrsFiles", "presets", "Lcom/deepeye/musicpro/dsp/v4a/V4APreset;", "getPresets", "applyPreset", "", "preset", "getSpectrumMagnitudes", "out", "", "refreshAssets", "saveCurrentPreset", "name", "", "selectConvolver", "file", "selectDDC", "setEffectEnabled", "effect", "Lcom/deepeye/musicpro/dsp/v4a/V4AEffect;", "enabled", "", "setFET", "attack", "", "release", "threshold", "knee", "setMasterEnabled", "setTubeWarmth", "warmth", "app_debug"})
public final class V4AViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.DeepEyeApp app = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.v4a.V4AEngine engine = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.db.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.io.File>> _irsFiles = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.io.File>> _ddcFiles = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> engineState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> irsFiles = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> ddcFiles = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.deepeye.musicpro.dsp.v4a.V4APreset>> presets = null;
    
    public V4AViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> getEngineState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> getIrsFiles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.io.File>> getDdcFiles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.deepeye.musicpro.dsp.v4a.V4APreset>> getPresets() {
        return null;
    }
    
    public final void setMasterEnabled(boolean enabled) {
    }
    
    public final void setEffectEnabled(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEffect effect, boolean enabled) {
    }
    
    public final void applyPreset(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4APreset preset) {
    }
    
    public final void selectConvolver(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
    }
    
    public final void selectDDC(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
    }
    
    public final void setFET(float attack, float release, float threshold, float knee) {
    }
    
    public final void setTubeWarmth(float warmth) {
    }
    
    public final void getSpectrumMagnitudes(@org.jetbrains.annotations.NotNull()
    float[] out) {
    }
    
    public final void saveCurrentPreset(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    private final void refreshAssets() {
    }
}