package com.deepeye.musicpro.ui.v4a

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.deepeye.musicpro.R
import com.deepeye.musicpro.dsp.v4a.V4AEngineMode
import com.deepeye.musicpro.dsp.v4a.V4AEngineState

class V4AModeIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val title = TextView(context)
    private val subtitle = TextView(context)

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_VERTICAL
        setPadding(18.dp, 16.dp, 18.dp, 16.dp)
        addView(title, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        addView(subtitle, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        title.setTextColor(ContextCompat.getColor(context, R.color.deep_text_primary))
        title.textSize = 16f
        title.typeface = Typeface.DEFAULT_BOLD
        subtitle.setTextColor(ContextCompat.getColor(context, R.color.deep_text_secondary))
        subtitle.textSize = 13f
        bind(V4AEngineState())
    }

    fun bind(state: V4AEngineState) {
        when (state.mode) {
            V4AEngineMode.SYSTEM -> {
                setBackgroundResource(R.drawable.bg_v4a_system_active)
                title.text = "System V4A detected"
                subtitle.text = "Hardware processing active on audio session ${state.audioSessionId}"
            }
            V4AEngineMode.BUNDLED -> {
                setBackgroundResource(R.drawable.bg_v4a_bundled_active)
                title.text = "Bundled V4A engine"
                subtitle.text = "Software DSP active; Magisk V4A can improve hardware integration"
            }
        }
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
