package com.example.ergonomics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun TopBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navStack by navController.currentBackStackEntryAsState()
    val currentRoute = navStack?.destination?.route

    Row(
        modifier = modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(currentRoute != "HomeScreen") { //Only shows the topbar if not on homescreen
            Button(
                modifier = Modifier
                    .padding(8.dp, 4.dp)
                    .fillMaxHeight(),
                onClick = {
                    navController.popBackStack() //Go back to home
                }
            ) {
                Text("Back")
            }
        }
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(rememberNavController())
}