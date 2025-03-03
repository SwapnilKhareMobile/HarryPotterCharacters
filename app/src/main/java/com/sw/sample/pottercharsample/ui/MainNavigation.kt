package com.sw.sample.pottercharsample.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            ListScreen(modifier,navController,onClick = { id ->
                navController.navigate("detail/$id")
            })
        }
        composable("detail/{id}", listOf(navArgument("id") { type = NavType.StringType })){
            DetailScreen(modifier,navController)
        }

    }
}