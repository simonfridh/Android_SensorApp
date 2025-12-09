package com.example.ergonomics.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ergonomics.ui.components.MeasurementData
import com.example.ergonomics.ui.components.MeasurementInfo
import com.example.ergonomics.ui.viewmodel.FakeVM
import com.example.ergonomics.ui.viewmodel.IErgonomicsVM

@Composable
fun MeasurementScreen(
    vm: IErgonomicsVM,
    navController: NavController,
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
                    .weight(0.70f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                MeasurementInfo(
                    measurementState = measurementState,
                    onChangeDisplayMode = { vm.changeDisplayGraph() },
                    modifier = Modifier.padding(8.dp)
                )
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
                    .weight(0.30f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Button(
                    enabled = !measurementState.measurementRunning,
                    onClick = { navController.navigate("ExportScreen") { launchSingleTop = true } },
                    modifier = Modifier
                        .width(256.dp)
                        .height(50.dp)
                        .padding(bottom= 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("EXPORT RESULT")
                }
                Button(
                    enabled = measurementState.measurementRunning,
                    onClick = { vm.stopMeasurement() },
                    modifier = Modifier
                        .width(256.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(8.dp)
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
                    .weight(0.70f),
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
                    .weight(0.3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MeasurementInfo(
                    measurementState = measurementState,
                    onChangeDisplayMode = { vm.changeDisplayGraph() },
                    modifier = Modifier.padding(8.dp)
                )
                Button(
                    enabled = !measurementState.measurementRunning,
                    onClick = { navController.navigate("ExportScreen") { launchSingleTop = true } },
                    modifier = Modifier
                        .width(256.dp)
                        .height(50.dp)
                        .padding(bottom= 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("EXPORT RESULT")
                }
                Button(
                    enabled = measurementState.measurementRunning,
                    onClick = { vm.stopMeasurement() },
                    modifier = Modifier
                        .width(256.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(8.dp)
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
    MeasurementScreen(FakeVM(), rememberNavController())
}

@Preview(widthDp = 915, heightDp = 412)
@Composable
private fun LandscapePreview() {
    MeasurementScreen(FakeVM(), rememberNavController())
}