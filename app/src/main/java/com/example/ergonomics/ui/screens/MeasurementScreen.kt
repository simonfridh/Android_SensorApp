package com.example.ergonomics.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val sensorState by vm.sensorState.collectAsState()
    val orientation = LocalConfiguration.current.orientation

    LaunchedEffect(Unit) {
        //Place anything that needs to run when screen loads here
    }

    //Portrait mode
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
            //TOP HALF
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(0.6f), //Uses 60% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                MeasurementData(sensorState = sensorState, modifier = modifier.padding(8.dp))
            }

            //BOTTOM HALF
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(0.4f), //Uses 40% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Button(
                    enabled = false,
                    onClick = { /* TODO add stop function from viewmodel */ }
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
                modifier = modifier
                    .fillMaxHeight()
                    .weight(0.5f), //Uses 50% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MeasurementData(sensorState = sensorState, modifier = modifier.padding(8.dp))
            }

            //RIGHT SIDE
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(0.5f), //Uses 50% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    enabled = false,
                    onClick = { /* TODO add stop function from viewmodel */ }
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