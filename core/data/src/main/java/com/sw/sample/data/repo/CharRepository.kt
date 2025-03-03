package com.sw.sample.data.repo

import com.sw.sample.db.model.DBDataResult
import com.sw.sample.model.CharDataResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CharRepository {
    suspend fun getChars(): Flow<Response<CharDataResult>>
    suspend fun getLocalCharList():Flow<List<DBDataResult>>
    suspend fun addIntoLocalCharList(list:List<DBDataResult>)
}