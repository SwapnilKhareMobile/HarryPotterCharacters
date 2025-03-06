package com.sw.sample.pottercharsample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sw.sample.pottercharsample.ui.MainNavigation
import com.sw.sample.pottercharsample.ui.theme.PotterCharSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            PotterCharSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                ) {
                    navController = rememberNavController()
                    MainNavigation(
                        modifier = Modifier.padding(1.dp),
                        navController,
                    )
                }
            }
        }
    }
}