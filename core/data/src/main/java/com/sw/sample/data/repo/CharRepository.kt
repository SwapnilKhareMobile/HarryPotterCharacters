package com.sw.sample.data.repo

import com.sw.sample.model.CharDataResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CharRepository {
    suspend fun getChars(): Flow<Response<CharDataResult>>
}