package com.sw.sample.potterchar.ui.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sw.sample.potterchar.ui.list.ErrorScreen
import com.sw.sample.potterchar.ui.list.LoadingProgress

@Composable
fun DetailScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: DetailScreenViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    when(uiState.value){
        is DetailScreenUIState.Error -> ErrorScreen(modifier, onClick = { navController.popBackStack() })
        DetailScreenUIState.Loading -> LoadingProgress(modifier)
        is DetailScreenUIState.Success -> Text(text = (uiState.value as DetailScreenUIState.Success).data.actorName)
        DetailScreenUIState.Nothing -> {}

    }
}