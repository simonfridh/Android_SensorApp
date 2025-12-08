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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ergonomics.ui.viewmodel.FakeVM
import com.example.ergonomics.ui.viewmodel.MeasurementState

@Composable
fun MeasurementData(
    measurementState: MeasurementState,
    modifier: Modifier = Modifier
) {
    var displayGraph by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
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

            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Measurement stopped \n" +
                            "Time: ${"%.2f".format(measurementState.totalTime)}s",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { displayGraph = !displayGraph }
                ) { Text("change mode") }
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                if(displayGraph) {
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
    MeasurementData(FakeVM().measurementState.value)
}