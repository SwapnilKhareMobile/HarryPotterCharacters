package com.sw.sample.potterchar.ui.detail

import com.sw.sample.domain.model.ListScreenData

sealed class DetailScreenUIState {
    class Success(val data: ListScreenData) : DetailScreenUIState()
    class Error(val message: String) : DetailScreenUIState()
    data object Loading : DetailScreenUIState()
    data object Nothing : DetailScreenUIState()
}