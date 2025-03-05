package com.sw.sample.potterchar.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sw.sample.domain.CharUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getCharUseCase: CharUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState: StateFlow<DetailScreenUIState> = savedStateHandle
        .getStateFlow<String?>("id", null).filterNotNull()
        .flatMapLatest { id ->
            getCharUseCase.getCharById(id)
        }.map { model ->
            DetailScreenUIState.Success(
                data = model
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailScreenUIState.Loading)


}