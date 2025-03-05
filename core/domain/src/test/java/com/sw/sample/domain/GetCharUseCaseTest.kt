package com.sw.sample.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sw.sample.data.repo.CharRepository
import com.sw.sample.db.model.DBDataResult
import io.mockk.coEvery
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getCharUseCase: GetCharUseCase
    private lateinit var charRepository: CharRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        charRepository = mockk(relaxed = true)
        getCharUseCase = GetCharUseCase(charRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke - local data present - returns local data`() = runTest(testDispatcher) {
        // Arrange
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
        coEvery { charRepository.getLocalCharList() } returns flowOf(localData)

        // Act
        val result = getCharUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(1, result[0]?.size)
        assertEquals("Harry Potter", result[0]?.first()?.charName)
    }

    @Test
    fun `invoke - local data empty - api failure - returns empty list`() = runTest(testDispatcher) {
        // Arrange
        coEvery { charRepository.getLocalCharList() } returns flowOf(emptyList())
        coEvery { charRepository.getChars() } returns flowOf(Response.error(404, "Not Found".toResponseBody()))

        // Act
        val result = getCharUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertTrue(result[0].isNullOrEmpty())
    }

    @Test
    fun `getCharById - char found - returns char`() = runTest(testDispatcher) {
        // Arrange
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
        coEvery { charRepository.getLocalCharList() } returns flowOf(localData)

        // Act
        val result = getCharUseCase.getCharById("1").toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Harry Potter", result[0].charName)
    }
}
