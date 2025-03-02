package com.sw.sample.data.source.remote

import com.sw.sample.model.CharDataResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CharRemoteDataSource {
    suspend fun getChars(): Flow<Response<CharDataResult>>

}