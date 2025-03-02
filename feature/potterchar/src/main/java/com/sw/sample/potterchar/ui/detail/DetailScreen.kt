package com.sw.sample.potterchar.ui.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun DetailScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Text(modifier = modifier, text = "Detail Screen")
}