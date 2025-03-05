package com.sw.sample.potterchar.ui.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sw.sample.domain.CharUseCase
import com.sw.sample.domain.model.ListScreenData
import com.sw.sample.potterchar.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailScreenViewModelTest {

    @get:Rule
    val dispatcherRule = TestCoroutineRule()
    private lateinit var viewModel: DetailScreenViewModel
    private val mockUseCase: CharUseCase = mockk()
    private val savedStateHandle = SavedStateHandle(mapOf("id" to "1"))

    @Before
    fun setUp() {
        coEvery { mockUseCase.getCharById("1") } returns flowOf(
            ListScreenData(
                "1",
                "Harry Potter",
                "Daniel Radcliffe",
                "human",
                "Gryffindor",
                "31-07-1980",
                "https://ik.imagekit.io/hpapi/harry.jpg",
                true
            )
        )
        viewModel = DetailScreenViewModel(mockUseCase, savedStateHandle)
    }

    @Test
    fun `fetch character details updates uiState with success`() = runTest {
        viewModel.uiState.test {
            assert(awaitItem() is DetailScreenUIState.Loading)
            val successState = awaitItem() as DetailScreenUIState.Success
            assertEquals("Harry Potter", successState.data.charName)
            cancelAndIgnoreRemainingEvents()
        }
    }
}