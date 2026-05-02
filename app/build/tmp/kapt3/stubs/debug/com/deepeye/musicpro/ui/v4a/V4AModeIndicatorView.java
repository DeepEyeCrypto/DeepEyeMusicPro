package com.deepeye.musicpro.ui.v4a;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.deepeye.musicpro.R;
import com.deepeye.musicpro.dsp.v4a.V4AEngineMode;
import com.deepeye.musicpro.dsp.v4a.V4AEngineState;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\f\u001a\u00020\u0007*\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2 = {"Lcom/deepeye/musicpro/ui/v4a/V4AModeIndicatorView;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "subtitle", "Landroid/widget/TextView;", "title", "dp", "getDp", "(I)I", "bind", "", "state", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "app_debug"})
public final class V4AModeIndicatorView extends android.widget.LinearLayout {
    @org.jetbrains.annotations.NotNull()
    private final android.widget.TextView title = null;
    @org.jetbrains.annotations.NotNull()
    private final android.widget.TextView subtitle = null;
    
    @kotlin.jvm.JvmOverloads()
    public V4AModeIndicatorView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public V4AModeIndicatorView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public V4AModeIndicatorView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public final void bind(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEngineState state) {
    }
    
    private final int getDp(int $this$dp) {
        return 0;
    }
}