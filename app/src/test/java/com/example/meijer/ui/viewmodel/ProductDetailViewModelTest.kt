package com.example.meijer.ui.viewmodel

import android.content.Context
import com.example.meijer.data.model.ProductDetail
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.util.LocationHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Unit tests for ProductDetailViewModel.
 * Tests the ViewModel's state management, data loading, and share text generation.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {
    
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var mockContext: Context
    
    @Before
    fun setup() {
        repository = mock()
        mockContext = mock()
        viewModel = ProductDetailViewModel(repository)
    }
    
    @Test
    fun `initial state is loading`() = runTest {
        // Given: ViewModel is initialized
        // When: Checking initial state
        val state = viewModel.uiState.value
        
        // Then: State should be loading
        assertTrue(state.isLoading)
        assertNull(state.productDetail)
        assertNull(state.error)
    }
    
    @Test
    fun `loadProductDetail success updates state with product detail`() = runTest {
        // Given: Repository returns successful product detail
        val mockProductDetail = ProductDetail(
            id = 0,
            title = "Bananas",
            summary = "Fresh bananas",
            description = "Fresh bananas, perfect for a healthy snack.",
            price = "$0.59/lb",
            imageUrl = "https://example.com/banana.jpg"
        )
        whenever(repository.getProductDetail(0)).thenReturn(Result.success(mockProductDetail))
        
        // When: Loading product detail
        viewModel.loadProductDetail(0, mockContext)
        
        // Wait for state update
        kotlinx.coroutines.delay(100)
        
        // Then: State should contain product detail
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.productDetail)
        assertEquals("Bananas", state.productDetail?.title)
        assertEquals("$0.59/lb", state.productDetail?.price)
        assertNull(state.error)
    }
    
    @Test
    fun `loadProductDetail failure updates state with error`() = runTest {
        // Given: Repository returns error
        val errorMessage = "Product not found"
        whenever(repository.getProductDetail(0)).thenReturn(Result.failure(Exception(errorMessage)))
        
        // When: Loading product detail
        viewModel.loadProductDetail(0, mockContext)
        
        // Wait for state update
        kotlinx.coroutines.delay(100)
        
        // Then: State should contain error
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.productDetail)
        assertNotNull(state.error)
        assertEquals(errorMessage, state.error)
    }
    
    @Test
    fun `getShareableText returns correct format`() = runTest {
        // Given: Product detail is loaded with city name
        val mockProductDetail = ProductDetail(
            id = 0,
            title = "Bananas",
            summary = "Fresh bananas",
            description = "Fresh bananas",
            price = "$0.59 / lb.",
            imageUrl = "https://example.com/banana.jpg"
        )
        whenever(repository.getProductDetail(0)).thenReturn(Result.success(mockProductDetail))
        
        viewModel.loadProductDetail(0, mockContext)
        kotlinx.coroutines.delay(100)
        
        // When: Getting shareable text
        val shareText = viewModel.getShareableText()
        
        // Then: Text should be in correct format
        assertTrue(shareText.contains("Bananas"))
        assertTrue(shareText.contains("$0.59 / lb."))
        assertTrue(shareText.contains("added to list"))
    }
}

