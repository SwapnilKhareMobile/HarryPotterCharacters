package com.sw.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sw.sample.db.model.DBDataResult

@Database(entities = [DBDataResult::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class PotterDatabase: RoomDatabase() {
    abstract fun potterDao(): PotterDao

}