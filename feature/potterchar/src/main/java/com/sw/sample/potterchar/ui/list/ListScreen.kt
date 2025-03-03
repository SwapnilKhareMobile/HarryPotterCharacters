package com.sw.sample.potterchar.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sw.sample.domain.model.ListScreenData

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onClick: (str: String) -> Unit,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Search Characters") },
            modifier = Modifier.fillMaxWidth()
        )
        when (uiState.value) {
            is ListScreenUIState.Error -> ErrorScreen(
                modifier,
                onClick = { viewModel.fetchCharsList() }
            )

            ListScreenUIState.Loading -> LoadingProgress(modifier)
            ListScreenUIState.Nothing -> {}
            is ListScreenUIState.Success -> ShowCharList(
                modifier,
                (uiState.value as ListScreenUIState.Success).data
            ) {
                viewModel.clearSearch()
                onClick(it)
            }

            null -> ErrorScreen(
                modifier,
                onClick = { viewModel.fetchCharsList() }
            )
        }
    }
}

@Composable
fun ShowCharList(modifier: Modifier, list: List<ListScreenData>, onClick: (str: String) -> Unit) {
    LazyColumn {
        items(list.size) {
            CharacterItem(character = list[it]) { onClick(list[it].id) }
        }
    }
}

@Composable
fun LoadingProgress(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error")
        Spacer(modifier = modifier)
        Text(text = "Click here to try again", modifier = modifier.clickable {
            onClick()
        })

    }
}

@Composable
fun CharacterItem(character: ListScreenData, onClick: () -> Unit) {
    val houseColor = getHouseColor(character.house)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(houseColor, shape = CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = character.charName, fontWeight = FontWeight.Bold)
                Text(text = "Played by: ${character.actorName}")
            }
        }
    }
}

@Composable
fun getHouseColor(house: String?): Color {
    return when (house?.lowercase()) {
        "gryffindor" -> Color(0xFF740001)
        "slytherin" -> Color(0xFF1A472A)
        "ravenclaw" -> Color(0xFF0C1A40)
        "hufflepuff" -> Color(0xFFEEB939)
        else -> Color.Gray // Default color for unknown houses
    }
}