package com.deepeye.musicpro.ui.v4a;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import com.deepeye.musicpro.databinding.ItemV4aEffectToggleBinding;
import com.deepeye.musicpro.databinding.ScreenV4aSheetBinding;
import com.deepeye.musicpro.dsp.v4a.V4AEffect;
import com.deepeye.musicpro.dsp.v4a.V4AEngineState;
import com.deepeye.musicpro.dsp.v4a.V4APreset;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 82\u00020\u0001:\u00018B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u001a\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0002J$\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0012H\u0016J\u001a\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u001b2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0016\u0010%\u001a\u00020\u00122\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0\'H\u0002J\u0016\u0010)\u001a\u00020\u00122\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0\'H\u0002J\b\u0010*\u001a\u00020\u0012H\u0002J\u0016\u0010+\u001a\u00020\u00122\f\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\'H\u0002J=\u0010.\u001a\u00020\u00122\n\b\u0002\u0010/\u001a\u0004\u0018\u0001002\n\b\u0002\u00101\u001a\u0004\u0018\u0001002\n\b\u0002\u00102\u001a\u0004\u0018\u0001002\n\b\u0002\u00103\u001a\u0004\u0018\u000100H\u0002\u00a2\u0006\u0002\u00104J\u0010\u00105\u001a\u00020\u00122\u0006\u00106\u001a\u000207H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e\u00a8\u00069"}, d2 = {"Lcom/deepeye/musicpro/ui/v4a/V4ABottomSheetFragment;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "binding", "Lcom/deepeye/musicpro/databinding/ScreenV4aSheetBinding;", "effectSwitches", "", "Lcom/deepeye/musicpro/dsp/v4a/V4AEffect;", "Lcom/google/android/material/switchmaterial/SwitchMaterial;", "rendering", "", "viewModel", "Lcom/deepeye/musicpro/ui/v4a/V4AViewModel;", "getViewModel", "()Lcom/deepeye/musicpro/ui/v4a/V4AViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "animateSpectrum", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkChip", "group", "Lcom/google/android/material/chip/ChipGroup;", "selected", "", "configureSliders", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "populateConvolverChips", "files", "", "Ljava/io/File;", "populateDdcChips", "populateEffectToggles", "populatePresetChips", "presets", "Lcom/deepeye/musicpro/dsp/v4a/V4APreset;", "pushFetFromSliders", "attack", "", "release", "threshold", "knee", "(Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;)V", "renderState", "state", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "Companion", "app_debug"})
public final class V4ABottomSheetFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    @org.jetbrains.annotations.Nullable()
    private com.deepeye.musicpro.databinding.ScreenV4aSheetBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.deepeye.musicpro.dsp.v4a.V4AEffect, com.google.android.material.switchmaterial.SwitchMaterial> effectSwitches = null;
    private boolean rendering = false;
    @java.lang.Deprecated()
    public static final long SPECTRUM_FRAME_MS = 16L;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String TAG = "V4ABottomSheet";
    @org.jetbrains.annotations.NotNull()
    private static final com.deepeye.musicpro.ui.v4a.V4ABottomSheetFragment.Companion Companion = null;
    
    public V4ABottomSheetFragment() {
        super();
    }
    
    private final com.deepeye.musicpro.ui.v4a.V4AViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void configureSliders() {
    }
    
    private final void populateEffectToggles() {
    }
    
    private final void populatePresetChips(java.util.List<com.deepeye.musicpro.dsp.v4a.V4APreset> presets) {
    }
    
    private final void populateConvolverChips(java.util.List<? extends java.io.File> files) {
    }
    
    private final void populateDdcChips(java.util.List<? extends java.io.File> files) {
    }
    
    private final void renderState(com.deepeye.musicpro.dsp.v4a.V4AEngineState state) {
    }
    
    private final void checkChip(com.google.android.material.chip.ChipGroup group, java.lang.String selected) {
    }
    
    private final void pushFetFromSliders(java.lang.Float attack, java.lang.Float release, java.lang.Float threshold, java.lang.Float knee) {
    }
    
    private final java.lang.Object animateSpectrum(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/deepeye/musicpro/ui/v4a/V4ABottomSheetFragment$Companion;", "", "()V", "SPECTRUM_FRAME_MS", "", "TAG", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}