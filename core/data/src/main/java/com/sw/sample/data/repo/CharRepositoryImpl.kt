package com.sw.sample.data.repo

import com.sw.sample.data.source.remote.CharRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharRepositoryImpl @Inject constructor (private val charRemoteDataSource: CharRemoteDataSource,): CharRepository {
    override suspend fun getChars() = charRemoteDataSource.getChars().flowOn(Dispatchers.IO)
}