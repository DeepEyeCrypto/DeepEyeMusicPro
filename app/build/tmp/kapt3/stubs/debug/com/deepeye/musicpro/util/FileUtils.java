package com.deepeye.musicpro.util;

import android.content.Context;
import android.net.Uri;
import androidx.core.content.FileProvider;
import com.deepeye.musicpro.model.Track;
import java.io.File;
import java.security.MessageDigest;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\bJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b\u00a8\u0006\u0011"}, d2 = {"Lcom/deepeye/musicpro/util/FileUtils;", "", "()V", "downloadsDir", "Ljava/io/File;", "context", "Landroid/content/Context;", "extensionForMime", "", "mimeType", "fileUri", "Landroid/net/Uri;", "file", "safeFileName", "track", "Lcom/deepeye/musicpro/model/Track;", "extension", "app_debug"})
public final class FileUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.util.FileUtils INSTANCE = null;
    
    private FileUtils() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.io.File downloadsDir(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String safeFileName(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.model.Track track, @org.jetbrains.annotations.NotNull()
    java.lang.String extension) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String extensionForMime(@org.jetbrains.annotations.Nullable()
    java.lang.String mimeType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.net.Uri fileUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return null;
    }
}