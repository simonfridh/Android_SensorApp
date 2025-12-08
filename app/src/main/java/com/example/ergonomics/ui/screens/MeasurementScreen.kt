package com.example.ergonomics.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ergonomics.ui.components.MeasurementData
import com.example.ergonomics.ui.viewmodel.FakeVM
import com.example.ergonomics.ui.viewmodel.IErgonomicsVM

@Composable
fun MeasurementScreen(
    vm: IErgonomicsVM,
    modifier: Modifier = Modifier
) {
    val measurementState by vm.measurementState.collectAsState()
    val orientation = LocalConfiguration.current.orientation


    //Portrait mode
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(modifier = modifier.background(MaterialTheme.colorScheme.background)
        ) {
            //TOP HALF
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.60f), //Uses 60% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                MeasurementData(
                    measurementState = measurementState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }

            //BOTTOM HALF
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.40f), //Uses 40% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Button(
                    enabled = measurementState.measurementRunning,
                    onClick = { vm.stopMeasurement() }
                ) {
                    Text("STOP")
                }
            }
        }
    }

    //LANDSCAPE MODE
    else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
        Row(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
            //LEFT SIDE
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), //Uses 50% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MeasurementData(
                    measurementState = measurementState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }

            //RIGHT SIDE
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), //Uses 50% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    enabled = measurementState.measurementRunning,
                    onClick = { vm.stopMeasurement() }
                ) {
                    Text("STOP")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PortraitPreview() {
    MeasurementScreen(FakeVM())
}

@Preview(widthDp = 915, heightDp = 412)
@Composable
private fun LandscapePreview() {
    MeasurementScreen(FakeVM())
}