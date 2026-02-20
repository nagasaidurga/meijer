package com.example.meijer.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.meijer.ui.viewmodel.ProductListViewModel

/**
 * Composable screen for displaying the list of products.
 * Shows a scrollable list of products with image, title, and summary.
 * 
 * @param viewModel ViewModel that manages the product list state
 * @param onProductClick Callback function invoked when a product is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onProductClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                // Loading state
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                // Error state
                uiState.error != null -> {
                    ErrorView(
                        errorMessage = uiState.error!!,
                        onRetry = { viewModel.retry() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                // Success state - display product list
                uiState.products != null -> {
                    if (uiState.products!!.isEmpty()) {
                        EmptyStateView(
                            message = "No products available",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        ProductListContent(
                            products = uiState.products!!,
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable that displays the list of products in a LazyColumn.
 * 
 * @param products List of products to display
 * @param onProductClick Callback function invoked when a product is clicked
 */
@Composable
private fun ProductListContent(
    products: List<com.example.meijer.data.model.Product>,
    onProductClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductListItem(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

/**
 * Composable that displays a single product item in the list.
 * Shows product image, title, and summary.
 * 
 * @param product Product data to display
 * @param onClick Callback function invoked when the item is clicked
 */
@Composable
private fun ProductListItem(
    product: com.example.meijer.data.model.Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Product image
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
            
            // Product title and summary
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Composable that displays an error message with a retry button.
 * 
 * @param errorMessage The error message to display
 * @param onRetry Callback function invoked when retry button is clicked
 * @param modifier Modifier for the composable
 */
@Composable
private fun ErrorView(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

/**
 * Composable that displays an empty state message.
 * 
 * @param message The message to display
 * @param modifier Modifier for the composable
 */
@Composable
private fun EmptyStateView(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

