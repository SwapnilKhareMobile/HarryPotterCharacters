package com.sw.sample.potterchar.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sw.sample.domain.CharUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(private val getCharUseCase: CharUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<ListScreenUIState?>(ListScreenUIState.Nothing)
    val uiState: StateFlow<ListScreenUIState?> = _uiState

    init {
        fetchCharsList()
    }

    fun fetchCharsList() {
        viewModelScope.launch {
            getCharUseCase().map {
                if (it.isNullOrEmpty()) ListScreenUIState.Error("Something went wrong")
                else ListScreenUIState.Success(it)
            }.catch { ListScreenUIState.Error("Something went wrong") }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    ListScreenUIState.Loading
                ).collect {
                    _uiState.value = it
                }
        }
    }

}