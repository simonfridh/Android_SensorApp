package com.example.ergonomics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ergonomics.ui.viewmodel.SensorState

@Composable
fun MeasurementData(
    sensorState: SensorState,
    modifier: Modifier = Modifier
) {
    //TODO replace with graph later
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Current measurement data:")
        Text("Accelerometer:\n" +
                "x: ${sensorState.accelerometerValues.x}\n" +
                "y: ${sensorState.accelerometerValues.y}\n" +
                "z: ${sensorState.accelerometerValues.z}\n"
        )

    }
}

@Preview
@Composable
private fun MeasurementDataPreview() {
    MeasurementData(sensorState = SensorState())
}