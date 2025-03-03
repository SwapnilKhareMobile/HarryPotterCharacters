package com.sw.sample.data.source.local

import com.sw.sample.db.PotterDatabase
import com.sw.sample.db.model.DBDataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharLocalDataSourceImpl @Inject constructor(private val database: PotterDatabase) :
    CharLocalDataSource {
    override suspend fun getLocalCharList(): Flow<List<DBDataResult>> = flow{
         emit(database.potterDao().getCharsFromDB())
    }

    override suspend fun addIntoLocalCharList(list: List<DBDataResult>) {
        database.potterDao().addCharsInDB(list)
    }

}