package com.sw.sample.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sw.sample.db.model.DBDataResult
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PotterDaoTest {
        private lateinit var potterDatabase: PotterDatabase
        private lateinit var potterDao: PotterDao
        private val result = DBDataResult(
            "1",
            "Harry Potter",
            "Daniel Radcliffe",
            "human",
            "Gryffindor",
            "31-07-1980",
            "https://ik.imagekit.io/hpapi/harry.jpg",
            true
        )
        private val list:List<DBDataResult> = listOf(result)

        @Before
        fun setUp(){
            potterDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                PotterDatabase::class.java
            ).allowMainThreadQueries().build()

            potterDao = potterDatabase.potterDao()
        }
        @Test
        fun insertPlanet() = runBlocking {
            potterDao.addCharsInDB(list)
            val result = potterDao.getCharsFromDB()
            Assert.assertEquals(1,result.size)
            Assert.assertEquals("1", result[0].id)
        }
        @After
        fun tearDown(){
            potterDatabase.close()
        }
    }