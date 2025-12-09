package com.example.ergonomics.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ergonomics.ui.components.ExportTextField
import com.example.ergonomics.ui.viewmodel.FakeVM
import com.example.ergonomics.ui.viewmodel.IErgonomicsVM

@Composable
fun ExportScreen(
    vm: IErgonomicsVM,
    modifier: Modifier = Modifier
) {
    val measurementState by vm.measurementState.collectAsState()
    val orientation = LocalConfiguration.current.orientation
    val fileNameState = remember{ mutableStateOf("") }

    //Portrait mode
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(modifier = modifier.background(MaterialTheme.colorScheme.background)
        ) {
            //TOP HALF
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.70f), //Uses 70% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ){
                ExportTextField(
                    modifier = modifier,
                    fileNameState = fileNameState
                )
            }
            //BOTTOM HALF
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.30f), //Uses 30% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){

                Button(
                    onClick = { vm.exportData(fileNameState.value) },
                    enabled = !measurementState.measurementRunning,
                    modifier = Modifier
                        .width(256.dp)
                        .height(50.dp)
                        .padding(bottom= 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("EXPORT TO CSV")
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
                    .weight(0.50f), //Uses 50% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

            }

            //RIGHT SIDE
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.50f), //Uses 50% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


            }
        }
    }
}

@Preview
@Composable
private fun PortraitPreview() {
    ExportScreen(FakeVM())
}

@Preview(widthDp = 915, heightDp = 412)
@Composable
private fun LandscapePreview() {
    ExportScreen(FakeVM())
}
