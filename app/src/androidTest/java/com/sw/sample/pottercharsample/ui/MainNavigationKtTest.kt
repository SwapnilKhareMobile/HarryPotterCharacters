package com.sw.sample.pottercharsample.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sw.sample.pottercharsample.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainNavigationTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: androidx.navigation.NavHostController

    @Before
    fun setup() {
        hiltRule.inject()

        composeTestRule.activityRule.scenario.onActivity { activity ->
            navController = activity.navController
        }

    }
    @Test
    fun listScreenIsStartDestination() {
        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals("list", route)

    }

}