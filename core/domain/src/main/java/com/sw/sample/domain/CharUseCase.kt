package com.sw.sample.domain

import com.sw.sample.potterchar.ui.list.model.ListScreenData
import kotlinx.coroutines.flow.Flow

interface CharUseCase {
    suspend operator fun invoke(): Flow<List<ListScreenData>?>
}