package com.example.meijer.ui.viewmodel

import app.cash.turbine.test
import com.example.meijer.data.model.Product
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Unit tests for ProductListViewModel.
 * Tests the ViewModel's state management and data loading logic.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setup() {
        repository = mock()
        viewModel = ProductListViewModel(repository)
    }

    @Test
    fun `initial state is not loading`() = runTest {
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `onStart success updates state with products`() = runTest {
        val mockProducts = listOf(Product(0, "Title", "Summary", "url"))
        whenever(repository.getProducts()).thenReturn(Result.success(mockProducts))

        viewModel.onStart()

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(mockProducts, state.products)
            assertNull(state.error)
        }
    }

    @Test
    fun `onStart failure updates state with error`() = runTest {
        val errorMessage = "Network error"
        whenever(repository.getProducts()).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.onStart()

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertNull(state.products)
            assertEquals(errorMessage, state.error)
        }
    }

    @Test
    fun `retry calls loadProducts again`() = runTest {
        whenever(repository.getProducts()).thenReturn(Result.failure(Exception("Error")))
        viewModel.onStart()

        viewModel.uiState.test {
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertNotNull(errorState.error)

            whenever(repository.getProducts()).thenReturn(Result.success(emptyList()))
            viewModel.retry()

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertNotNull(successState.products)
        }
    }
}
