package com.sw.sample.pottercharsample.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sw.sample.potterchar.ui.detail.DetailScreen
import com.sw.sample.potterchar.ui.list.ListScreen

@Composable
fun MainNavigation(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier
    ) {
        composable("list"){
            ListScreen(modifier,navController,onClick = {
                navController.navigate("detail/$it")
            })
        }
        composable("detail/{id}"){
            DetailScreen(modifier,navController)
        }

    }
}