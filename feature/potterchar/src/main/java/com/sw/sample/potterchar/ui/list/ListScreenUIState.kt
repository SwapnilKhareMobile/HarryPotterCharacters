package com.sw.sample.potterchar.ui.list

import com.sw.sample.domain.model.ListScreenData

sealed class ListScreenUIState {
    data class Success(val data: List<ListScreenData>) : ListScreenUIState()
    data class Error(val message: String) : ListScreenUIState()
    data object Loading : ListScreenUIState()
    data object Nothing : ListScreenUIState()
}