package com.deepeye.musicpro.ui.queue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.deepeye.musicpro.R;
import com.deepeye.musicpro.databinding.ItemQueueTrackBinding;
import com.deepeye.musicpro.databinding.ScreenQueueSheetBinding;
import com.deepeye.musicpro.model.Track;
import com.deepeye.musicpro.player.PlayerController;
import com.deepeye.musicpro.util.TimeUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001a\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/deepeye/musicpro/ui/queue/QueueBottomSheetFragment;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "adapter", "Lcom/deepeye/musicpro/ui/queue/QueueBottomSheetFragment$QueueAdapter;", "binding", "Lcom/deepeye/musicpro/databinding/ScreenQueueSheetBinding;", "playerController", "Lcom/deepeye/musicpro/player/PlayerController;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onViewCreated", "view", "QueueAdapter", "app_debug"})
public final class QueueBottomSheetFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    @org.jetbrains.annotations.Nullable()
    private com.deepeye.musicpro.databinding.ScreenQueueSheetBinding binding;
    private com.deepeye.musicpro.player.PlayerController playerController;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.ui.queue.QueueBottomSheetFragment.QueueAdapter adapter = null;
    
    public QueueBottomSheetFragment() {
        super();
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
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0002\u0014\u0015B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0006H\u0016J\u0018\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/deepeye/musicpro/ui/queue/QueueBottomSheetFragment$QueueAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/deepeye/musicpro/model/Track;", "Lcom/deepeye/musicpro/ui/queue/QueueBottomSheetFragment$QueueAdapter$QueueViewHolder;", "()V", "value", "", "currentIndex", "getCurrentIndex", "()I", "setCurrentIndex", "(I)V", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "DiffCallback", "QueueViewHolder", "app_debug"})
    static final class QueueAdapter extends androidx.recyclerview.widget.ListAdapter<com.deepeye.musicpro.model.Track, com.deepeye.musicpro.ui.queue.QueueBottomSheetFragment.QueueAdapter.QueueViewHolder> {
        private int currentIndex = -1;
        
        public QueueAdapter() {
            super(null);
        }
        
        public final int getCurrentIndex() {
            return 0;
        }
        
        public final void setCurrentIndex(int value) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.deepeye.musicpro.ui.queue.QueueBottomSheetFragment.QueueAdapter.QueueViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override()
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
        com.deepeye.musicpro.ui.queue.QueueBottomSheetFragment.QueueAdapter.QueueViewHolder holder, int position) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c2\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/deepeye/musicpro/ui/queue/QueueBottomSheetFragment$QueueAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/deepeye/musicpro/model/Track;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
        static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.deepeye.musicpro.model.Track> {
            @org.jetbrains.annotations.NotNull()
            public static final com.deepeye.musicpro.ui.queue.QueueBottomSheetFragment.QueueAdapter.DiffCallback INSTANCE = null;
            
            private DiffCallback() {
                super();
            }
            
            @java.lang.Override()
            public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.model.Track oldItem, @org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.model.Track newItem) {
                return false;
            }
            
            @java.lang.Override()
            public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.model.Track oldItem, @org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.model.Track newItem) {
                return false;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/deepeye/musicpro/ui/queue/QueueBottomSheetFragment$QueueAdapter$QueueViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/deepeye/musicpro/databinding/ItemQueueTrackBinding;", "(Lcom/deepeye/musicpro/databinding/ItemQueueTrackBinding;)V", "bind", "", "track", "Lcom/deepeye/musicpro/model/Track;", "index", "", "isCurrent", "", "app_debug"})
        public static final class QueueViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull()
            private final com.deepeye.musicpro.databinding.ItemQueueTrackBinding binding = null;
            
            public QueueViewHolder(@org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.databinding.ItemQueueTrackBinding binding) {
                super(null);
            }
            
            public final void bind(@org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.model.Track track, int index, boolean isCurrent) {
            }
        }
    }
}