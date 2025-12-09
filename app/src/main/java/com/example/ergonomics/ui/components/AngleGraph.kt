package com.example.ergonomics.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
    Row (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(values.isEmpty()){
            Text("No data to display")
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 2.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("135")
                Text("90")
                Text("45")
                Text("0")
                Text("-45")
            }
            Canvas(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                val xMin = values.minOf {it.timestamp}
                val xMax = values.maxOf { it.timestamp }
                val xRange = xMax - xMin

                val yMin = -45f
                val yMax = 135f
                val yRange = yMax - yMin

                val points = values.map { value ->
                    val x =  ((value.timestamp - xMin) / xRange) * size.width
                    if (value.angle >= yMax) Offset(x, 1f)
                    else if (value.angle <= yMin) Offset(x, size.height-1)
                    else {
                        val y =  size.height - ((value.angle - yMin) / yRange) * size.height
                        Offset(x,y)
                    }
                }


                for (i in 0 until points.size-1) {
                    drawLine(
                        start = points[i],
                        end = points[i + 1],
                        color = Color(0xFFFF0000),
                        strokeWidth = 5f
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AngleGraphPreview() {
    AngleGraph(values=listOf(
        Measurement(-180f,  0.05f),
        Measurement(-180f,  0.10f),
        Measurement(-90f,  0.15f),
        Measurement(-90f,  0.20f),
        Measurement(0f,  0.25f),
        Measurement(0f,  0.30f),
        Measurement(90f,  0.35f),
        Measurement(90f,  0.40f),
        Measurement(180f,  0.45f),
        Measurement(180f,  0.50f),
    ))
}