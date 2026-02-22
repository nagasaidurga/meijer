package com.example.meijer.data.repository

import com.example.meijer.data.api.ProductApiService
import com.example.meijer.data.model.Product
import com.example.meijer.data.model.ProductDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

/**
 * Unit tests for ProductRepository.
 * Tests the repository's data fetching and error handling logic.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryTest {

    private lateinit var apiService: ProductApiService
    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        apiService = mock()
        repository = ProductRepository(apiService)
    }

    @Test
    fun `getProducts success returns list of products`() = runTest {
        val mockProducts = listOf(
            Product(0, "Bananas", "Fresh bananas", "https://example.com/banana.jpg"),
            Product(1, "Apples", "Fresh apples", "https://example.com/apple.jpg")
        )
        whenever(apiService.getProducts()).thenReturn(Response.success(mockProducts))

        val result = repository.getProducts()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getProductDetail success returns product detail`() = runTest {
        val mockProductDetail = ProductDetail(0, "Bananas", "Summary", "Desc", "Price", "url")
        whenever(apiService.getProductDetail(0)).thenReturn(Response.success(mockProductDetail))

        val result = repository.getProductDetail(0)

        assertTrue(result.isSuccess)
        assertEquals("Bananas", result.getOrNull()?.title)
    }
}
