package com.example.ergonomics.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ergonomics.R
import com.example.ergonomics.ui.viewmodel.IErgonomicsVM
import com.example.ergonomics.ui.viewmodel.FakeVM

@Composable
fun HomeScreen(
    vm: IErgonomicsVM,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val orientation = LocalConfiguration.current.orientation

    LaunchedEffect(Unit) {
        //Place anything that needs to run when screen loads here
    }

    //Portrait mode
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
            //TOP HALF
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f), //Uses 60% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(
                    painter = painterResource(R.drawable.ergonomics),
                    contentDescription = "Ergonomics icon",
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "Ergonomics app",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
            }

            //BOTTOM HALF
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f), //Uses 40% of remaining screen
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                Button(
                    modifier = Modifier.size(150.dp),
                    shape = CircleShape,
                    onClick = {
                        navController.navigate("MeasurementScreen") { launchSingleTop = true }
                        vm.startMeasurement()
                    }
                ) {
                    Text(
                        text = "START MEASUREMENT",
                        textAlign = TextAlign.Center
                    )
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
                Icon(
                    painter = painterResource(R.drawable.ergonomics),
                    contentDescription = "Ergonomics icon",
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "Bluetooth app",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
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
                    modifier = Modifier.size(150.dp),
                    shape = CircleShape,
                    onClick = {
                        navController.navigate("MeasurementScreen") { launchSingleTop = true }
                        vm.startMeasurement()
                    }
                ) {
                    Text(
                        text = "START MEASUREMENT",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PortraitPreview() {
    HomeScreen(FakeVM(), rememberNavController())
}

@Preview(widthDp = 915, heightDp = 412)
@Composable
private fun LandscapePreview() {
    HomeScreen(FakeVM(), rememberNavController())
}