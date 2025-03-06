package com.sw.sample.pottercharsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sw.sample.pottercharsample.ui.MainNavigation
import com.sw.sample.pottercharsample.ui.theme.PotterCharSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PotterCharSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                ) {
                    innerPadding ->
                    navController = rememberNavController()
                    MainNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController,
                    )
                }
            }
        }
    }
}