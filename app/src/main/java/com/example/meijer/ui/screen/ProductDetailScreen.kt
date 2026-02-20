package com.example.meijer.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.meijer.ui.viewmodel.ProductDetailViewModel

/**
 * Composable screen for displaying detailed product information.
 * Shows full product image, title, description, price, and share functionality.
 * 
 * @param productId The unique identifier of the product to display
 * @param viewModel ViewModel that manages the product detail state
 * @param onBackClick Callback function invoked when back button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    // Load product detail when screen is first displayed
    LaunchedEffect(productId) {
        viewModel.loadProductDetail(productId, context)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
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
                        onRetry = { viewModel.retry(productId, context) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                // Success state - display product details
                uiState.productDetail != null -> {
                    ProductDetailContent(
                        productDetail = uiState.productDetail!!,
                        cityName = uiState.cityName,
                        onShareClick = {
                            shareProduct(context, viewModel.getShareableText())
                        }
                    )
                }
            }
        }
    }
}

/**
 * Composable that displays the product detail content.
 * 
 * @param productDetail Detailed product information
 * @param cityName Current city name for sharing
 * @param onShareClick Callback function invoked when share button is clicked
 */
@Composable
private fun ProductDetailContent(
    productDetail: com.example.meijer.data.model.ProductDetail,
    cityName: String,
    onShareClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Full product image
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(productDetail.imageUrl)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = productDetail.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        
        // Product information section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            Text(
                text = "Title: ${productDetail.title}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Divider()
            
            // Description
            Text(
                text = "Description:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = productDetail.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Divider()
            
            // Price
            Text(
                text = "Price: ${productDetail.price}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Share button
            Button(
                onClick = onShareClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add to List")
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
 * Shares the product information using Android's share intent.
 * Opens the system share dialog with available options (text message, email, social media, etc.).
 * 
 * @param context Application context
 * @param shareText The text to share in the format: "{product title} - {price} from {city name} added to list"
 */
private fun shareProduct(context: android.content.Context, shareText: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share product"))
}

