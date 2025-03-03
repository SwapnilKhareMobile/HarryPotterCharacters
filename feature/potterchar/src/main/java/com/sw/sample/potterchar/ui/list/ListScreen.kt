package com.sw.sample.potterchar.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    when (uiState.value) {
        is ListScreenUIState.Error -> ErrorScreen(
            modifier,
            onClick = { viewModel.fetchCharsList() }
        )
        ListScreenUIState.Loading -> LoadingProgress(modifier)
        ListScreenUIState.Nothing -> {}
        is ListScreenUIState.Success -> ShowCharList(modifier, (uiState.value as ListScreenUIState.Success).data) {
            onClick(it)
        }
        null -> ErrorScreen(
            modifier,
            onClick = { viewModel.fetchCharsList() }
        )
    }
}

@Composable
fun ShowCharList(modifier: Modifier, list: List<ListScreenData>, onClick: (str: String) -> Unit){
    LazyColumn {
        items(list.size) {
            Text(text = list[it].charName, modifier = modifier.clickable { onClick(list[it].id) })
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