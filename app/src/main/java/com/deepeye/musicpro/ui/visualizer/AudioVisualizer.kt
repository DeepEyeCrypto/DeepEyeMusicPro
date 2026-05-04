package com.deepeye.musicpro.ui.visualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AudioVisualizer(
    viewModel: VisualizerViewModel,
    modifier: Modifier = Modifier
) {
    val mode by viewModel.mode.collectAsState()
    val spectrum by viewModel.spectrum.collectAsState()
    val waveform by viewModel.waveform.collectAsState()
    val peak by viewModel.peak.collectAsState()

    val primaryColor = Color(0xFF00E5FF) // Cyan
    val secondaryColor = Color(0xFFFF00E5) // Magenta
    
    val brush = remember {
        Brush.verticalGradient(listOf(primaryColor, secondaryColor))
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { viewModel.nextMode() }
            }
    ) {
        when (mode) {
            VisualizerMode.BARS -> {
                drawFrequencyBars(spectrum, brush)
            }
            VisualizerMode.WAVEFORM -> {
                drawWaveformLine(waveform, primaryColor)
            }
            VisualizerMode.CIRCULAR -> {
                drawCircularSpectrum(spectrum, brush)
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawFrequencyBars(
    spectrum: FloatArray,
    brush: Brush
) {
    val barCount = spectrum.size
    if (barCount <= 0 || size.width <= 0 || size.height <= 0) return
    
    val barGap = 4f
    val totalGapWidth = barGap * (barCount - 1)
    val barWidth = (size.width - totalGapWidth) / barCount
    val canvasHeight = size.height

    for (i in spectrum.indices) {
        val magnitude = spectrum[i]
        val barHeight = magnitude * canvasHeight
        val x = i * (barWidth + barGap)
        
        drawRoundRect(
            brush = brush,
            topLeft = Offset(x, canvasHeight - barHeight),
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f)
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawWaveformLine(
    waveform: FloatArray,
    color: Color
) {
    val points = waveform.size
    if (points <= 1 || size.width <= 0 || size.height <= 0) return
    
    val step = size.width / (points - 1)
    val centerY = size.height / 2f
    val amplitude = size.height / 3f

    val path = Path().apply {
        moveTo(0f, centerY)
        for (i in waveform.indices) {
            val x = i * step
            val y = centerY + waveform[i] * amplitude
            lineTo(x, y)
        }
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCircularSpectrum(
    spectrum: FloatArray,
    brush: Brush
) {
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val innerRadius = size.width.coerceAtMost(size.height) / 4f
    val maxBarHeight = innerRadius * 0.8f
    
    val angleStep = 360f / spectrum.size

    for (i in spectrum.indices) {
        val magnitude = spectrum[i]
        val barHeight = magnitude * maxBarHeight
        
        rotate(degrees = i * angleStep, pivot = Offset(centerX, centerY)) {
            drawRoundRect(
                brush = brush,
                topLeft = Offset(centerX - 2f, centerY - innerRadius - barHeight),
                size = Size(4f, barHeight),
                cornerRadius = CornerRadius(2f, 2f)
            )
        }
    }
    
    // Pulse inner circle based on bass
    val bassIntensity = (spectrum[0] + spectrum[1] + spectrum[2]) / 3f
    drawCircle(
        brush = brush,
        radius = innerRadius + (bassIntensity * 20f),
        center = Offset(centerX, centerY),
        style = Stroke(width = 2f)
    )
}
