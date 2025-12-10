package com.example.ergonomics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ergonomics.domain.models.Measurement
import com.example.ergonomics.ui.viewmodel.MeasurementState

@Composable
fun MeasurementData(
    measurementState: MeasurementState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .border(width = 3.dp, color = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if(measurementState.measurementRunning) {
                AngleGraph(measurementState.measurementSummary)
            }
            else{
                if(measurementState.displayGraph) {
                    AngleGraph(measurementState.measurementSummary)
                }
                else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(items = measurementState.measurementSummary) { item ->
                            Text(
                                text = "${"%.2f".format(item.timestamp)}s: ${"%.1f".format(item.angle)}°",
                                modifier = Modifier.padding(8.dp,4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MeasurementDataPreview() {
    MeasurementData(
        MeasurementState(
            measurementSummary = listOf(
                Measurement(-180f,  0.05f),
                Measurement(-180f,  0.10f),
                Measurement(-90f,  0.15f),
                Measurement(-90f,  0.20f),
                Measurement(0f,  0.25f),
                Measurement(0f,  0.30f),
                Measurement(90f,  0.35f),
                Measurement(90f,  0.40f),
                Measurement(180f,  0.45f),
                Measurement(180f,  0.50f)
            )
        )
    )
}