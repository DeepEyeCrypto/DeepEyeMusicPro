# ══════════════════════════════════════════════════════════════════
# DeepEyeMusicPro — Production ProGuard / R8 Rules
# ══════════════════════════════════════════════════════════════════

# ── General Android ──────────────────────────────────────────────
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses,EnclosingMethod

# ── Kotlin ───────────────────────────────────────────────────────
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}
# Keep coroutines internals used via reflection
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# ── JNI / Native DSP Engine ──────────────────────────────────────
# CRITICAL: All native methods must survive obfuscation.
# The JNI bridge calls these by exact name.
-keep class com.deepeye.musicpro.dsp.NativeDSP {
    native <methods>;
    *;
}
-keepclasseswithmembers class * {
    native <methods>;
}

# ── Data Models (serialized / Room entities) ─────────────────────
-keep class com.deepeye.musicpro.model.** { *; }
-keep class com.deepeye.musicpro.db.** { *; }

# ── Room Database ────────────────────────────────────────────────
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-dontwarn androidx.room.paging.**

# ── Media3 / ExoPlayer ──────────────────────────────────────────
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**
-keep class com.deepeye.musicpro.dsp.DSPAudioProcessor { *; }
-keep class com.deepeye.musicpro.service.MusicPlayerService { *; }

# ── NewPipe Extractor ────────────────────────────────────────────
-keep class org.schabi.newpipe.extractor.** { *; }
-dontwarn org.schabi.newpipe.extractor.**
# NewPipe uses reflection for service loading
-keep class org.schabi.newpipe.extractor.services.** { *; }

# ── Google Cast SDK ──────────────────────────────────────────────
-keep class com.google.android.gms.cast.** { *; }
-keep class com.deepeye.musicpro.cast.** { *; }
-dontwarn com.google.android.gms.cast.**

# ── Google Play Services Auth ────────────────────────────────────
-keep class com.google.android.gms.auth.** { *; }
-keep class com.google.android.gms.common.** { *; }
-dontwarn com.google.android.gms.**

# ── OkHttp ───────────────────────────────────────────────────────
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keepclassmembers class okhttp3.** { *; }

# ── Coil (image loading) ────────────────────────────────────────
-dontwarn coil.**
-keep class coil.** { *; }

# ── Lottie ───────────────────────────────────────────────────────
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** { *; }

# ── Shimmer ──────────────────────────────────────────────────────
-keep class com.facebook.shimmer.** { *; }

# ── Jetpack Compose ──────────────────────────────────────────────
# Compose compiler generates classes that use reflection for state
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
# Keep @Composable annotated functions from being removed
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# ── DataStore / Preferences ──────────────────────────────────────
-keep class androidx.datastore.** { *; }
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite { *; }

# ── AndroidX Security (EncryptedSharedPreferences) ───────────────
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**

# ── Credentials API ──────────────────────────────────────────────
-keep class androidx.credentials.** { *; }
-dontwarn androidx.credentials.**

# ── Oboe (native audio, loaded via JNI internally) ───────────────
-dontwarn com.google.oboe.**

# ── Enums ────────────────────────────────────────────────────────
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ── Parcelable ───────────────────────────────────────────────────
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# ── Serializable ─────────────────────────────────────────────────
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ── Strip logging in release ─────────────────────────────────────
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
}

# ── App-specific keepers ─────────────────────────────────────────
-keep class com.deepeye.musicpro.auth.** { *; }
-keep class com.deepeye.musicpro.receiver.** { *; }
-keep class com.deepeye.musicpro.extractor.DownloaderImpl { *; }

# ── ProfileInstaller (baseline profile delivery at install) ──────
-keep class androidx.profileinstaller.** { *; }

# ── Visualizer ViewModel & Compose ───────────────────────────────
-keep class com.deepeye.musicpro.ui.visualizer.** { *; }

