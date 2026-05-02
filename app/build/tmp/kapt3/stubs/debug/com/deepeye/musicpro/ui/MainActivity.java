package com.deepeye.musicpro.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import com.deepeye.musicpro.player.PlayerController;
import com.deepeye.musicpro.DeepEyeApp;
import com.deepeye.musicpro.R;
import com.deepeye.musicpro.databinding.ActivityMainBinding;
import com.deepeye.musicpro.extractor.LinkParser;
import com.deepeye.musicpro.ui.home.HomeFragment;
import com.deepeye.musicpro.ui.library.DownloadsFragment;
import com.deepeye.musicpro.ui.library.LibraryFragment;
import com.deepeye.musicpro.ui.search.SearchFragment;
import com.deepeye.musicpro.ui.settings.SettingsFragment;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\u0012\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\b\u0010\u0010\u001a\u00020\u000bH\u0014J\b\u0010\u0011\u001a\u00020\u000bH\u0002J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/deepeye/musicpro/ui/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/deepeye/musicpro/databinding/ActivityMainBinding;", "notificationPermission", "Landroidx/activity/result/ActivityResultLauncher;", "", "playerController", "Lcom/deepeye/musicpro/player/PlayerController;", "handleIncomingLink", "", "maybeRequestNotificationPermission", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setupNavigation", "show", "fragment", "Landroidx/fragment/app/Fragment;", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.deepeye.musicpro.databinding.ActivityMainBinding binding;
    private com.deepeye.musicpro.player.PlayerController playerController;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> notificationPermission = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupNavigation() {
    }
    
    private final void show(androidx.fragment.app.Fragment fragment) {
    }
    
    private final void maybeRequestNotificationPermission() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void handleIncomingLink() {
    }
}