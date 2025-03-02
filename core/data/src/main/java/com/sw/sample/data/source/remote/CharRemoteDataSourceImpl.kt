package com.sw.sample.data.source.remote

import com.sw.sample.network.APIService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharRemoteDataSourceImpl @Inject constructor(private val apiService: APIService) : CharRemoteDataSource {
    override suspend fun getChars() = flow {
        emit(apiService.getCharacters())
    }

}