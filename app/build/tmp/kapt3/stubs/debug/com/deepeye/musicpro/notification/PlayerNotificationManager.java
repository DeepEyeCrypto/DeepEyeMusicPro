package com.deepeye.musicpro.notification;

import android.app.Notification;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaStyleNotificationHelper;
import com.deepeye.musicpro.DeepEyeApp;
import com.deepeye.musicpro.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/deepeye/musicpro/notification/PlayerNotificationManager;", "", "()V", "DOWNLOAD_NOTIFICATION_ID", "", "PLAYBACK_NOTIFICATION_ID", "buildDownloadNotification", "Landroid/app/Notification;", "context", "Landroid/content/Context;", "title", "", "progress", "buildPlaybackNotification", "mediaSession", "Landroidx/media3/session/MediaSession;", "app_debug"})
public final class PlayerNotificationManager {
    public static final int PLAYBACK_NOTIFICATION_ID = 4010;
    public static final int DOWNLOAD_NOTIFICATION_ID = 4020;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.notification.PlayerNotificationManager INSTANCE = null;
    
    private PlayerNotificationManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Notification buildPlaybackNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.media3.session.MediaSession mediaSession) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Notification buildDownloadNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String title, int progress) {
        return null;
    }
}