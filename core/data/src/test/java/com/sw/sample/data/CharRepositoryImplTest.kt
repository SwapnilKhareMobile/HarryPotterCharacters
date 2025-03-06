package com.sw.sample.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sw.sample.data.repo.CharRepositoryImpl
import com.sw.sample.data.source.local.CharLocalDataSource
import com.sw.sample.data.source.remote.CharRemoteDataSource
import com.sw.sample.db.model.DBDataResult
import com.sw.sample.model.CharDataResult
import com.sw.sample.model.CharDataResultItem
import com.sw.sample.model.Wand
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CharRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var charRepositoryImpl: CharRepositoryImpl
    private lateinit var charRemoteDataSource: CharRemoteDataSource
    private lateinit var charLocalDataSource: CharLocalDataSource

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        charRemoteDataSource = mockk(relaxed = true)
        charLocalDataSource = mockk(relaxed = true)
        charRepositoryImpl = CharRepositoryImpl(charRemoteDataSource, charLocalDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getChars - api success - returns api data`() = runTest(testDispatcher) {
        val charDataResultItem = CharDataResult()
        val apiData = CharDataResultItem(
            actor = "Daniel Radcliffe",
            alive = true,
            alternate_actors = emptyList(),
            alternate_names = emptyList(),
            ancestry = "half-blood",
            dateOfBirth = "31-07-1980",
            eyeColour = "green",
            gender = "male",
            hairColour = "black",
            hogwartsStaff = true,
            hogwartsStudent = true,
            house = "Gryffindor",
            id = "1",
            image = "https://ik.imagekit.io/hpapi/harry.jpg",
            name =  "Harry Potter",
            patronus = "",
            species = "",
            wand =  Wand("",11.0,""),
            wizard = true,
            yearOfBirth = 1980,
        )
        charDataResultItem.add(apiData)
        coEvery { charRemoteDataSource.getChars() } returns flowOf(Response.success(charDataResultItem))
        val result = charRepositoryImpl.getChars().toList()
        assertEquals(1, result.size)
    }

    @Test
    fun `getChars - api failure - returns error response`() = runTest(testDispatcher) {
        val errorResponse = Response.error<CharDataResult>(404, "Not Found".toResponseBody())
        coEvery { charRemoteDataSource.getChars() } returns flowOf(errorResponse)
        val result = charRepositoryImpl.getChars().toList()
        assertEquals(1, result.size)
        assertEquals(errorResponse.code(), result[0].code())
    }

    @Test
    fun `getLocalCharList - returns local data`() = runTest(testDispatcher) {
        val localData = listOf(
            DBDataResult(
                id = "1",
                charName = "Harry Potter",
                actorName = "Daniel Radcliffe",
                species = "human",
                house = "Gryffindor",
                dateOfBirth = "31-07-1980",
                image = "image1",
                isAlive = true
            )
        )
        coEvery { charLocalDataSource.getLocalCharList() } returns flowOf(localData)
        val result = charRepositoryImpl.getLocalCharList().toList()
        assertEquals(1, result.size)
        assertEquals(localData, result[0])
    }

    @Test
    fun `addIntoLocalCharList - adds data to local data source`() = runTest(testDispatcher) {
        val localData = listOf(
            DBDataResult(
                id = "1",
                charName = "Harry Potter",
                actorName = "Daniel Radcliffe",
                species = "human",
                house = "Gryffindor",
                dateOfBirth = "31-07-1980",
                image = "image1",
                isAlive = true
            )
        )

        charRepositoryImpl.addIntoLocalCharList(localData)
        coVerify { charLocalDataSource.addIntoLocalCharList(localData) }
    }
}

