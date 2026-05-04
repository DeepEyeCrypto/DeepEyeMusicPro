package com.deepeye.musicpro.ui.v4a;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import com.deepeye.musicpro.databinding.BottomsheetV4aM3Binding;
import com.deepeye.musicpro.dsp.v4a.V4AEffect;
import com.deepeye.musicpro.dsp.v4a.V4AEngineState;
import com.deepeye.musicpro.dsp.v4a.V4APreset;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0011H\u0016J\u001a\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0016\u0010!\u001a\u00020\u00112\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#H\u0002J\u0016\u0010%\u001a\u00020\u00112\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0#H\u0002J\u0010\u0010(\u001a\u00020\u00112\u0006\u0010)\u001a\u00020*H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r\u00a8\u0006,"}, d2 = {"Lcom/deepeye/musicpro/ui/v4a/V4ABottomSheetFragment;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "_binding", "Lcom/deepeye/musicpro/databinding/BottomsheetV4aM3Binding;", "binding", "getBinding", "()Lcom/deepeye/musicpro/databinding/BottomsheetV4aM3Binding;", "rendering", "", "viewModel", "Lcom/deepeye/musicpro/ui/v4a/V4AViewModel;", "getViewModel", "()Lcom/deepeye/musicpro/ui/v4a/V4AViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "checkChip", "", "group", "Lcom/google/android/material/chip/ChipGroup;", "selected", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "populateConvolverChips", "files", "", "Ljava/io/File;", "populatePresetChips", "presets", "Lcom/deepeye/musicpro/dsp/v4a/V4APreset;", "renderState", "state", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "Companion", "app_debug"})
public final class V4ABottomSheetFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    @org.jetbrains.annotations.Nullable()
    private com.deepeye.musicpro.databinding.BottomsheetV4aM3Binding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private boolean rendering = false;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String TAG = "V4ABottomSheet";
    @org.jetbrains.annotations.NotNull()
    private static final com.deepeye.musicpro.ui.v4a.V4ABottomSheetFragment.Companion Companion = null;
    
    public V4ABottomSheetFragment() {
        super();
    }
    
    private final com.deepeye.musicpro.databinding.BottomsheetV4aM3Binding getBinding() {
        return null;
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
    
    private final void populatePresetChips(java.util.List<com.deepeye.musicpro.dsp.v4a.V4APreset> presets) {
    }
    
    private final void populateConvolverChips(java.util.List<? extends java.io.File> files) {
    }
    
    private final void renderState(com.deepeye.musicpro.dsp.v4a.V4AEngineState state) {
    }
    
    private final void checkChip(com.google.android.material.chip.ChipGroup group, java.lang.String selected) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/deepeye/musicpro/ui/v4a/V4ABottomSheetFragment$Companion;", "", "()V", "TAG", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}