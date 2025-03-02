package com.sw.sample.potterchar.ui.list

import com.sw.sample.potterchar.ui.list.model.ListScreenData

sealed class ListScreenUIState {
    class Success(val data: List<ListScreenData>) : ListScreenUIState()
    class Error(val message: String) : ListScreenUIState()
    data object Loading : ListScreenUIState()
    data object Nothing : ListScreenUIState()

}