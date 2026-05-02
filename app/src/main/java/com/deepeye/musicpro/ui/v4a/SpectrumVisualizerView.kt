package com.deepeye.musicpro.ui.v4a

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.deepeye.musicpro.R
import kotlin.math.max

class SpectrumVisualizerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val magnitudes = FloatArray(BINS)

    fun setMagnitudes(values: FloatArray) {
        val count = minOf(values.size, magnitudes.size)
        for (index in 0 until count) magnitudes[index] = values[index].coerceIn(0f, 1f)
        for (index in count until magnitudes.size) magnitudes[index] = 0f
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val barGap = 4f
        val barWidth = max(3f, (width - barGap * (BINS - 1)) / BINS.toFloat())
        val bottom = height.toFloat()
        paint.shader = LinearGradient(
            0f,
            0f,
            0f,
            bottom,
            ContextCompat.getColor(context, R.color.deep_accent_cyan),
            ContextCompat.getColor(context, R.color.deep_accent_magenta),
            Shader.TileMode.CLAMP
        )
        for (index in 0 until BINS) {
            val magnitude = magnitudes[index]
            val barHeight = (height * (0.08f + magnitude * 0.92f)).coerceAtLeast(6f)
            val left = index * (barWidth + barGap)
            canvas.drawRoundRect(left, bottom - barHeight, left + barWidth, bottom, barWidth * 0.5f, barWidth * 0.5f, paint)
        }
        paint.shader = null
    }

    private companion object {
        const val BINS = 32
    }
}
