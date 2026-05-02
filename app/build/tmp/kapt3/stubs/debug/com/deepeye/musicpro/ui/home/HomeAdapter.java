package com.deepeye.musicpro.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.deepeye.musicpro.databinding.ItemQuickActionBinding;
import com.deepeye.musicpro.model.Track;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0013\u0014B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/deepeye/musicpro/ui/home/HomeAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/deepeye/musicpro/ui/home/HomeAdapter$ViewHolder;", "tracks", "", "Lcom/deepeye/musicpro/model/Track;", "(Ljava/util/List;)V", "rows", "Lcom/deepeye/musicpro/ui/home/HomeAdapter$Row$TrackRow;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "Row", "ViewHolder", "app_debug"})
public final class HomeAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.deepeye.musicpro.ui.home.HomeAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.deepeye.musicpro.model.Track> tracks = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.deepeye.musicpro.ui.home.HomeAdapter.Row.TrackRow> rows = null;
    
    public HomeAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.Track> tracks) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.deepeye.musicpro.ui.home.HomeAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.ui.home.HomeAdapter.ViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0001\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/deepeye/musicpro/ui/home/HomeAdapter$Row;", "", "()V", "TrackRow", "Lcom/deepeye/musicpro/ui/home/HomeAdapter$Row$TrackRow;", "app_debug"})
    public static abstract class Row {
        
        private Row() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/deepeye/musicpro/ui/home/HomeAdapter$Row$TrackRow;", "Lcom/deepeye/musicpro/ui/home/HomeAdapter$Row;", "track", "Lcom/deepeye/musicpro/model/Track;", "(Lcom/deepeye/musicpro/model/Track;)V", "getTrack", "()Lcom/deepeye/musicpro/model/Track;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class TrackRow extends com.deepeye.musicpro.ui.home.HomeAdapter.Row {
            @org.jetbrains.annotations.NotNull()
            private final com.deepeye.musicpro.model.Track track = null;
            
            public TrackRow(@org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.model.Track track) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.deepeye.musicpro.model.Track getTrack() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.deepeye.musicpro.model.Track component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.deepeye.musicpro.ui.home.HomeAdapter.Row.TrackRow copy(@org.jetbrains.annotations.NotNull()
            com.deepeye.musicpro.model.Track track) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/deepeye/musicpro/ui/home/HomeAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/deepeye/musicpro/databinding/ItemQuickActionBinding;", "(Lcom/deepeye/musicpro/databinding/ItemQuickActionBinding;)V", "bind", "", "row", "Lcom/deepeye/musicpro/ui/home/HomeAdapter$Row$TrackRow;", "app_debug"})
    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.deepeye.musicpro.databinding.ItemQuickActionBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        com.deepeye.musicpro.databinding.ItemQuickActionBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.deepeye.musicpro.ui.home.HomeAdapter.Row.TrackRow row) {
        }
    }
}