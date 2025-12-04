package com.example.ergonomics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ergonomics.ui.viewmodel.SensorState

@Composable
fun MeasurementData(
    sensorState: SensorState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(sensorState.measurementRunning) {
            Text(
                text = "Measurement running",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${sensorState.currentAngle}°",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
        else{
            //TODO replace with graph later
            Text(
                text = "Measurement stopped",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun MeasurementDataPreview() {
    MeasurementData(sensorState = SensorState(measurementRunning = true, currentAngle = 34f))
}