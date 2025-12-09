package com.example.ergonomics.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ergonomics.ui.viewmodel.MeasurementState

@Composable
fun MeasurementInfo(
    measurementState: MeasurementState,
    onChangeDisplayMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (measurementState.measurementRunning) {
            Text(
                text = "Measurement running \n" +
                        "Time: ${"%.2f".format(measurementState.totalTime)}s",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${"%.1f".format(measurementState.currentAngle)}°",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "Measurement stopped \n" +
                        "Time: ${"%.2f".format(measurementState.totalTime)}s",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onChangeDisplayMode,
                shape = RoundedCornerShape(8.dp)
            ) { Text("change display mode") }
        }
    }
}

@Preview
@Composable
private fun MeasurementInfoPreview(){
    MeasurementInfo(MeasurementState(measurementRunning = false), {})
}