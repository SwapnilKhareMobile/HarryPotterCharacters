package com.sw.sample.data.source.local

import com.sw.sample.db.model.DBDataResult
import kotlinx.coroutines.flow.Flow

interface CharLocalDataSource {
    suspend fun getLocalCharList(): Flow<List<DBDataResult>>
    suspend fun addIntoLocalCharList(list:List<DBDataResult>)
}