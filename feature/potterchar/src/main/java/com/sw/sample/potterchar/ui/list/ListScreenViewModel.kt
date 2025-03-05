package com.sw.sample.potterchar.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sw.sample.potterchar.util.ConnectivityObserver
import com.sw.sample.domain.CharUseCase
import com.sw.sample.domain.model.ListScreenData
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
class ListScreenViewModel @Inject constructor(
    private val getCharUseCase: CharUseCase,
    private val connectivityObserver: ConnectivityObserver?
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<ListScreenUIState?>(ListScreenUIState.Nothing)
    val uiState: StateFlow<ListScreenUIState?> = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var fullList: List<ListScreenData> = emptyList()

    init {
        fetchCharsList()
    }

    fun fetchCharsList() {
        viewModelScope.launch {
                    getCharUseCase().map {
                        if (it.isNullOrEmpty()) {
                            ListScreenUIState.Error("Something went wrong")
                        } else {
                            fullList = it
                            ListScreenUIState.Success(it)
                        }
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

            fun updateSearchQuery(query: String) {
                _searchQuery.value = query
                filterList(query)
            }

            private fun filterList(query: String) {
                val filteredList = if (query.isBlank()) {
                    fullList
                } else {
                    fullList.filter {
                        it.charName.contains(query, ignoreCase = true) || it.actorName.contains(
                            query,
                            ignoreCase = true
                        )
                    }
                }
                _uiState.value = ListScreenUIState.Success(filteredList)
            }

            fun clearSearch() {
                _searchQuery.value = ""
                _uiState.value = ListScreenUIState.Success(fullList) // Reset to full list
            }

        }