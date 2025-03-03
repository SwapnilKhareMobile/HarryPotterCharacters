package com.sw.sample.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sw.sample.common.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class DBDataResult (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val charName: String,
    val actorName: String,
    val species: String,
    val house: String,
    val dateOfBirth: String?,
    val image: String,
    val isAlive: Boolean,
)