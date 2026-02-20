package com.example.meijer.ui.viewmodel

import com.example.meijer.data.model.Product
import com.example.meijer.data.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Unit tests for ProductListViewModel.
 * Tests the ViewModel's state management and data loading logic.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {
    
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductListViewModel
    
    @Before
    fun setup() {
        repository = mock()
        viewModel = ProductListViewModel(repository)
    }
    
    @Test
    fun `initial state is loading`() = runTest {
        // Given: ViewModel is initialized
        // When: Checking initial state
        val state = viewModel.uiState.value
        
        // Then: State should be loading
        assertTrue(state.isLoading)
        assertNull(state.products)
        assertNull(state.error)
    }
    
    @Test
    fun `loadProducts success updates state with products`() = runTest {
        // Given: Repository returns successful product list
        val mockProducts = listOf(
            Product(0, "Bananas", "Fresh bananas", "https://example.com/banana.jpg"),
            Product(1, "Apples", "Fresh apples", "https://example.com/apple.jpg")
        )
        whenever(repository.getProducts()).thenReturn(Result.success(mockProducts))
        
        // When: Loading products
        viewModel.loadProducts()
        
        // Wait for state update
        kotlinx.coroutines.delay(100)
        
        // Then: State should contain products
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.products)
        assertEquals(2, state.products?.size)
        assertEquals("Bananas", state.products?.get(0)?.title)
        assertNull(state.error)
    }
    
    @Test
    fun `loadProducts failure updates state with error`() = runTest {
        // Given: Repository returns error
        val errorMessage = "Network error"
        whenever(repository.getProducts()).thenReturn(Result.failure(Exception(errorMessage)))
        
        // When: Loading products
        viewModel.loadProducts()
        
        // Wait for state update
        kotlinx.coroutines.delay(100)
        
        // Then: State should contain error
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.products)
        assertNotNull(state.error)
        assertEquals(errorMessage, state.error)
    }
    
    @Test
    fun `retry calls loadProducts again`() = runTest {
        // Given: Initial load fails
        whenever(repository.getProducts())
            .thenReturn(Result.failure(Exception("Error")))
            .thenReturn(Result.success(emptyList()))
        
        viewModel.loadProducts()
        kotlinx.coroutines.delay(100)
        
        // When: Retrying
        viewModel.retry()
        kotlinx.coroutines.delay(100)
        
        // Then: Repository should be called again
        val state = viewModel.uiState.value
        assertNotNull(state.products)
    }
}

