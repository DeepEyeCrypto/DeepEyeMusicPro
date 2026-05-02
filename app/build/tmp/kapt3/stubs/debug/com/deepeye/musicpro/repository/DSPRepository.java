package com.deepeye.musicpro.repository;

import com.deepeye.musicpro.db.AppDatabase;
import com.deepeye.musicpro.dsp.DSPManager;
import com.deepeye.musicpro.dsp.DSPPreset;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fJ\u0016\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0017"}, d2 = {"Lcom/deepeye/musicpro/repository/DSPRepository;", "", "database", "Lcom/deepeye/musicpro/db/AppDatabase;", "dspManager", "Lcom/deepeye/musicpro/dsp/DSPManager;", "(Lcom/deepeye/musicpro/db/AppDatabase;Lcom/deepeye/musicpro/dsp/DSPManager;)V", "getDspManager", "()Lcom/deepeye/musicpro/dsp/DSPManager;", "applyPreset", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observePresets", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/deepeye/musicpro/dsp/DSPPreset;", "saveCustom", "preset", "(Lcom/deepeye/musicpro/dsp/DSPPreset;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "seedBuiltIns", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DSPRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.db.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.DSPManager dspManager = null;
    
    public DSPRepository(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.db.AppDatabase database, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.DSPManager dspManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.DSPManager getDspManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.deepeye.musicpro.dsp.DSPPreset>> observePresets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object seedBuiltIns(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveCustom(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.DSPPreset preset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object applyPreset(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}