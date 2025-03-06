package com.sw.sample.potterchar.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.sw.sample.common.AppUtils
import com.sw.sample.domain.model.ListScreenData
import com.sw.sample.potterchar.ui.list.ErrorScreen
import com.sw.sample.potterchar.ui.list.LoadingProgress
import com.sw.sample.potterchar.ui.list.getHouseColor

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = { SecondScreenTopBar() }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (uiState.value) {
                is DetailScreenUIState.Error -> ErrorScreen(
                    modifier,
                    onClick = { navController.popBackStack() })

                DetailScreenUIState.Loading -> LoadingProgress(modifier)
                is DetailScreenUIState.Success -> CharacterItemDetail((uiState.value as DetailScreenUIState.Success).data) { navController.popBackStack() }
                DetailScreenUIState.Nothing -> {}

            }
        }
    }

}

@Composable
fun CharacterItemDetail(character: ListScreenData, onClick: () -> Unit) {
    val houseColor = getHouseColor(character.house)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                modifier = Modifier.size(100.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = "Character Image"
            )

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Character: ${character.charName}", fontWeight = FontWeight.Bold)
                Text(text = "Name: ${character.actorName}")
                Text(text = "DOB: ${AppUtils.getDateFormat(character.dateOfBirth)}")
                if (character.isAlive) Text(text = "Alive")
                else Text(text = "Dead")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreenTopBar() {
    TopAppBar(
        title = {
            Text(text = "Character Details")
        }
    )


}