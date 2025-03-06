package com.sw.sample.data.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sw.sample.model.CharDataResult
import com.sw.sample.model.CharDataResultItem
import com.sw.sample.model.Wand
import com.sw.sample.network.APIService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CharRemoteDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var charRemoteDataSourceImpl: CharRemoteDataSourceImpl
    private lateinit var apiService: APIService

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        apiService = mockk(relaxed = true)
        charRemoteDataSourceImpl = CharRemoteDataSourceImpl(apiService)
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
        coEvery { apiService.getCharacters() } returns Response.success(charDataResultItem)
        val result = charRemoteDataSourceImpl.getChars().toList()

        assertEquals(1, result.size)
        assertEquals(apiData, result[0].body()?.get(0))
    }

    @Test
    fun `getChars - api failure - returns error response`() = runTest(testDispatcher) {
        val errorResponse = Response.error<CharDataResult>(404, "Not Found".toResponseBody())
        coEvery { apiService.getCharacters() } returns errorResponse

        val result = charRemoteDataSourceImpl.getChars().toList()

        assertEquals(1, result.size)
        assertEquals(errorResponse.code(), result[0].code())
    }
}