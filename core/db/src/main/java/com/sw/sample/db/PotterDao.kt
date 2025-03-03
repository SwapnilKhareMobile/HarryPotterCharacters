package com.sw.sample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sw.sample.common.TABLE_NAME
import com.sw.sample.db.model.DBDataResult

@Dao
interface PotterDao {

    @Insert
    suspend fun addCharsInDB(result: List<DBDataResult>)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getCharsFromDB():List<DBDataResult>
}