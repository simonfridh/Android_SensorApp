package com.example.ergonomics.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ergonomics.domain.models.Measurement

@Composable
fun AngleGraph(
    values: List<Measurement>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (values.isEmpty()) return@Canvas

            // X-axis based on timestamps
            val minX = values.minOf { it.timestamp }
            val maxX = values.maxOf { it.timestamp }
            val xRange = maxX - minX

            // FIXED Y-axis range
            val minY = -180f
            val maxY = 180f
            val yRange = maxY - minY

            fun mapX(ts: Float): Float =
                if (xRange == 0f) size.width / 2f
                else ((ts - minX) / xRange) * size.width

            fun mapY(angle: Float): Float =
                size.height - ((angle - minY) / yRange) * size.height

            val points = values.map { m ->
                Offset(mapX(m.timestamp), mapY(m.angle))
            }

            for (i in 0 until points.lastIndex) {
                drawLine(
                    start = points[i],
                    end = points[i + 1],
                    color = Color(0xFF2962FF),
                    strokeWidth = 5f
                )
            }
        }
    }
}

@Preview
@Composable
private fun AngleGraphPreview() {
    AngleGraph(values=listOf(
        Measurement(0f,  0f),
        Measurement(10f,  0.05f),
        Measurement(30f,  0.1f),
        Measurement(67f,  0.15f),
        Measurement(91f,  0.2f),
        Measurement(50f,  0.25f),
        Measurement(23f,  0.3f),
        Measurement(13f,  0.35f)
    ))
}