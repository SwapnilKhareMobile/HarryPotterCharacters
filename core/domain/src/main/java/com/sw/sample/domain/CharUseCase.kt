package com.sw.sample.domain

import com.sw.sample.domain.model.ListScreenData
import kotlinx.coroutines.flow.Flow

interface CharUseCase {
    suspend operator fun invoke(): Flow<List<ListScreenData>?>
    suspend fun getCharById(id: String): Flow<ListScreenData>
}