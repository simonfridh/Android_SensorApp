package com.example.ergonomics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun MeasurementData(
    measurementState: MeasurementState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(measurementState.measurementRunning) {
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
        }
        else{
            //TODO replace with graph later. just a list showing all the values

            AngleGraph(measurementState.measurementSummary)

            /*
            Text(
                text = "Measurement stopped \n" +
                        "Time: ${measurementState.totalTime}s",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier
                    .size(256.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                items(items = measurementState.measurementSummary) { item ->
                    Text(
                        text = "A: ${"%.1f".format(item.angle)}°, T: ${item.timestamp}s",
                        modifier = Modifier.padding(8.dp,4.dp)
                    )
                }
            }*/
        }
    }
}

@Preview
@Composable
private fun MeasurementDataPreview() {
    MeasurementData(measurementState = MeasurementState(measurementRunning = true, currentAngle = 34f))
}