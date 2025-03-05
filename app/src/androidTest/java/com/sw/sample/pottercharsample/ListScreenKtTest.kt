package com.sw.sample.pottercharsample

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.sw.sample.domain.model.ListScreenData
import com.sw.sample.potterchar.ui.list.ShowCharList
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListDisplaysItems() {
        val sampleData = listOf(
            ListScreenData(
                "1",
                "Harry Potter",
                "Daniel Radcliffe",
                "human",
                "Gryffindor",
                "31-07-1980",
                "https://ik.imagekit.io/hpapi/harry.jpg",
                true
            ),
            ListScreenData(
                "2",
                "Hermione Granger",
                "Emma Watson",
                "human",
                "Gryffindor",
                "19-09-1979",
                "https://ik.imagekit.io/hpapi/hermione.jpeg",
                true
            )        )

        composeTestRule.setContent {
            ShowCharList(modifier = Modifier, list = sampleData) {}
        }

        composeTestRule.onNodeWithText("Harry Potter").assertExists()
        composeTestRule.onNodeWithText("Hermione Granger").assertExists()
    }
}