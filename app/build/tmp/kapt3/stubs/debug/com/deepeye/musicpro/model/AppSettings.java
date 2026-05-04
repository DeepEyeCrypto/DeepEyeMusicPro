package com.deepeye.musicpro.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b&\b\u0087\b\u0018\u00002\u00020\u0001B\u0091\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0012H\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0005H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\u0095\u0001\u00103\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u00104\u001a\u00020\u00052\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u00020\u0012H\u00d6\u0001J\t\u00107\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015R\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017\u00a8\u00068"}, d2 = {"Lcom/deepeye/musicpro/model/AppSettings;", "", "themeMode", "", "dynamicAccent", "", "audioQuality", "autoplay", "restoreLastSession", "notificationStyle", "downloadLocationStrategy", "dspEnabledDefault", "chosenPreset", "webViewFallbackEnabled", "explicitContentEnabled", "autoRadioEnabled", "radioSource", "prefetchCount", "", "(Ljava/lang/String;ZLjava/lang/String;ZZLjava/lang/String;Ljava/lang/String;ZLjava/lang/String;ZZZLjava/lang/String;I)V", "getAudioQuality", "()Ljava/lang/String;", "getAutoRadioEnabled", "()Z", "getAutoplay", "getChosenPreset", "getDownloadLocationStrategy", "getDspEnabledDefault", "getDynamicAccent", "getExplicitContentEnabled", "getNotificationStyle", "getPrefetchCount", "()I", "getRadioSource", "getRestoreLastSession", "getThemeMode", "getWebViewFallbackEnabled", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class AppSettings {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String themeMode = null;
    private final boolean dynamicAccent = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String audioQuality = null;
    private final boolean autoplay = false;
    private final boolean restoreLastSession = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String notificationStyle = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String downloadLocationStrategy = null;
    private final boolean dspEnabledDefault = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String chosenPreset = null;
    private final boolean webViewFallbackEnabled = false;
    private final boolean explicitContentEnabled = false;
    private final boolean autoRadioEnabled = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String radioSource = null;
    private final int prefetchCount = 0;
    
    public AppSettings(@org.jetbrains.annotations.NotNull()
    java.lang.String themeMode, boolean dynamicAccent, @org.jetbrains.annotations.NotNull()
    java.lang.String audioQuality, boolean autoplay, boolean restoreLastSession, @org.jetbrains.annotations.NotNull()
    java.lang.String notificationStyle, @org.jetbrains.annotations.NotNull()
    java.lang.String downloadLocationStrategy, boolean dspEnabledDefault, @org.jetbrains.annotations.NotNull()
    java.lang.String chosenPreset, boolean webViewFallbackEnabled, boolean explicitContentEnabled, boolean autoRadioEnabled, @org.jetbrains.annotations.NotNull()
    java.lang.String radioSource, int prefetchCount) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThemeMode() {
        return null;
    }
    
    public final boolean getDynamicAccent() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAudioQuality() {
        return null;
    }
    
    public final boolean getAutoplay() {
        return false;
    }
    
    public final boolean getRestoreLastSession() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNotificationStyle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDownloadLocationStrategy() {
        return null;
    }
    
    public final boolean getDspEnabledDefault() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getChosenPreset() {
        return null;
    }
    
    public final boolean getWebViewFallbackEnabled() {
        return false;
    }
    
    public final boolean getExplicitContentEnabled() {
        return false;
    }
    
    public final boolean getAutoRadioEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRadioSource() {
        return null;
    }
    
    public final int getPrefetchCount() {
        return 0;
    }
    
    public AppSettings() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    public final boolean component12() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.model.AppSettings copy(@org.jetbrains.annotations.NotNull()
    java.lang.String themeMode, boolean dynamicAccent, @org.jetbrains.annotations.NotNull()
    java.lang.String audioQuality, boolean autoplay, boolean restoreLastSession, @org.jetbrains.annotations.NotNull()
    java.lang.String notificationStyle, @org.jetbrains.annotations.NotNull()
    java.lang.String downloadLocationStrategy, boolean dspEnabledDefault, @org.jetbrains.annotations.NotNull()
    java.lang.String chosenPreset, boolean webViewFallbackEnabled, boolean explicitContentEnabled, boolean autoRadioEnabled, @org.jetbrains.annotations.NotNull()
    java.lang.String radioSource, int prefetchCount) {
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