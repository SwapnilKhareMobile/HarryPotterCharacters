package com.sw.sample.potterchar.ui.list

import app.cash.turbine.test
import com.sw.sample.domain.CharUseCase
import com.sw.sample.domain.model.ListScreenData
import com.sw.sample.potterchar.TestCoroutineRule
import com.sw.sample.potterchar.util.NetworkConnectivityObserver
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListScreenViewModelTest {
    @get:Rule
    val dispatcherRule = TestCoroutineRule()

    private lateinit var viewModel: ListScreenViewModel

    private val mockUseCase: CharUseCase = mockk()
    private val mockConnectivityObserver:NetworkConnectivityObserver = mockk()
    private val mockData = listOf(
        ListScreenData(
            "1",
            "Harry Potter",
            "Daniel Radcliffe",
            "human",
            "Gryffindor",
            "31-07-1980",
            "https://ik.imagekit.io/hpapi/harry.jpg",
            true
        ),
        ListScreenData(
            "2",
            "Hermione Granger",
            "Emma Watson",
            "human",
            "Gryffindor",
            "19-09-1979",
            "https://ik.imagekit.io/hpapi/hermione.jpeg",
            true
        )
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { mockUseCase() } returns flowOf(mockData) // Ensure this mock is properly set
        viewModel = ListScreenViewModel(mockUseCase,mockConnectivityObserver)
    }

    @Test
    fun `fetchCharsList should update uiState with success when data is available`() = runTest {
        viewModel.fetchCharsList()
        viewModel.uiState.test {
            assertEquals(ListScreenUIState.Loading, awaitItem())
            val expected = ListScreenUIState.Success(mockData)
            val actual = awaitItem()
            assertTrue(actual is ListScreenUIState.Success && actual.data == expected.data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateSearchQuery should filter and update uiState`() = runTest {
        coEvery { mockUseCase() } returns flowOf(mockData)
        viewModel.fetchCharsList()
        advanceUntilIdle()

        viewModel.uiState.test {
            assertEquals(ListScreenUIState.Success(mockData), awaitItem()) // Full List
            viewModel.updateSearchQuery("Harry")
            assertEquals(
                ListScreenUIState.Success(listOf(mockData[0])),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `clearSearch should reset searchQuery and show full list`() = runTest {
        coEvery { mockUseCase() } returns flowOf(mockData)
        viewModel.fetchCharsList()
        advanceUntilIdle()

        viewModel.uiState.test {
            assertEquals(ListScreenUIState.Success(mockData), awaitItem()) // Full List
            viewModel.updateSearchQuery("Harry")
            viewModel.clearSearch()
            assertEquals(
                ListScreenUIState.Success(listOf(mockData[0])),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchCharsList error`() = runTest {
        coEvery { mockUseCase() } returns flowOf(emptyList())
        viewModel.fetchCharsList()
        advanceUntilIdle()
        val uiState = viewModel.uiState.value
        assertEquals(ListScreenUIState.Error("Something went wrong"), uiState)
    }

}
