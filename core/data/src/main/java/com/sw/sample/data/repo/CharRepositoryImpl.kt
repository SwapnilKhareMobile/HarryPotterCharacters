package com.sw.sample.data.repo

import com.sw.sample.data.source.local.CharLocalDataSource
import com.sw.sample.data.source.remote.CharRemoteDataSource
import com.sw.sample.db.model.DBDataResult
import com.sw.sample.model.CharDataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class CharRepositoryImpl @Inject constructor(
    private val charRemoteDataSource: CharRemoteDataSource,
    private val charLocalDataSource: CharLocalDataSource
) : CharRepository {
    override suspend fun getChars(): Flow<Response<CharDataResult>> {
        return charRemoteDataSource.getChars().flowOn(Dispatchers.IO)
    }

    override suspend fun getLocalCharList(): Flow<List<DBDataResult>> {
        return charLocalDataSource.getLocalCharList().flowOn(Dispatchers.IO)
    }

    override suspend fun addIntoLocalCharList(list: List<DBDataResult>) {
       charLocalDataSource.addIntoLocalCharList(list)
    }
}