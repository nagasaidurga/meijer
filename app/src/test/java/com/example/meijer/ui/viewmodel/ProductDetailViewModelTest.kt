package com.example.meijer.ui.viewmodel

import app.cash.turbine.test
import com.example.meijer.data.model.ProductDetail
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.util.LocationProvider
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
 * Unit tests for ProductDetailViewModel.
 * Tests the ViewModel's state management, data loading, and share text generation.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var repository: ProductRepository
    private lateinit var locationProvider: LocationProvider
    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setup() {
        repository = mock()
        locationProvider = mock()
        viewModel = ProductDetailViewModel(repository, locationProvider)
    }

    @Test
    fun `initial state is loading`() = runTest {
        assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `loadProductDetail success updates state with product detail`() = runTest {
        val mockProductDetail = ProductDetail(0, "Title", "Summary", "Desc", "Price", "url")
        whenever(repository.getProductDetail(0)).thenReturn(Result.success(mockProductDetail))

        viewModel.loadProductDetail(0)

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(mockProductDetail, state.productDetail)
            assertNull(state.error)
        }
    }

    @Test
    fun `loadProductDetail failure updates state with error`() = runTest {
        val errorMessage = "Product not found"
        whenever(repository.getProductDetail(0)).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.loadProductDetail(0)

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertNull(state.productDetail)
            assertEquals(errorMessage, state.error)
        }
    }

    @Test
    fun `getShareableText returns correct format`() = runTest {
        val mockProductDetail = ProductDetail(0, "Bananas", "Summary", "Desc", "$0.59 / lb.", "url")
        whenever(repository.getProductDetail(0)).thenReturn(Result.success(mockProductDetail))

        viewModel.loadProductDetail(0)

        viewModel.uiState.test {
            val state = awaitItem()
            val shareText = viewModel.getShareableText()
            assertTrue(shareText.contains("Bananas"))
            assertTrue(shareText.contains("$0.59 / lb."))
            assertTrue(shareText.contains("added to list"))
        }
    }
}
