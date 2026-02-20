package com.example.meijer.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Splash screen that displays the Meijer "Shop Smarter" promotional design.
 * Shows for a brief moment before navigating to the main app.
 * 
 * @param onNavigateToMain Callback invoked when splash screen should navigate to main screen
 */
@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit
) {
    // Animation for fade-in effect
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 800),
        label = "splash_alpha"
    )
    
    // Navigate to main screen after delay
    LaunchedEffect(Unit) {
        delay(2500) // Show splash for 2.5 seconds
        onNavigateToMain()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A3A6B)) // Dark blue background
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Section: Text and Icons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                
                // "Shop Smarter" text
                Text(
                    text = "Shop Smarter",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // "with the Meijer App." text
                Text(
                    text = "with the Meijer App.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                // Three circular icons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Delivery Van Icon
                    SplashIcon(
                        iconContent = {
                            // Van icon representation
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFF1A3A6B), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                // Simplified van shape
                                Text(
                                    text = "🚚",
                                    fontSize = 32.sp
                                )
                            }
                        },
                        label = "Delivery"
                    )
                    
                    // Shopping Bag Icon
                    SplashIcon(
                        iconContent = {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFF1A3A6B), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🛍️",
                                    fontSize = 32.sp
                                )
                            }
                        },
                        label = "Pickup"
                    )
                    
                    // Mobile Shopping Icon
                    SplashIcon(
                        iconContent = {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFF1A3A6B), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "📱",
                                    fontSize = 32.sp
                                )
                            }
                        },
                        label = "Mobile"
                    )
                }
            }
            
            // Bottom Section: Grocery Bag with Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                contentAlignment = Alignment.Center
            ) {
                // Grocery Bag with items
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Groceries spilling out
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Baguette (left)
                        Text("🥖", fontSize = 32.sp, modifier = Modifier.padding(end = 4.dp))
                        
                        // Apple
                        Text("🍎", fontSize = 28.sp)
                        
                        // Milk
                        Text("🥛", fontSize = 28.sp)
                        
                        // Pear
                        Text("🍐", fontSize = 28.sp)
                        
                        // Broccoli
                        Text("🥦", fontSize = 28.sp)
                        
                        // Bananas (right)
                        Text("🍌", fontSize = 32.sp, modifier = Modifier.padding(start = 4.dp))
                    }
                    
                    // Grocery Bag (brown paper bag)
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(250.dp)
                            .background(
                                color = Color(0xFFD4A574), // Light brown paper bag color
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp,
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Meijer Logo on bag
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "meijer",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE31837), // Meijer red
                                textAlign = TextAlign.Center
                            )
                            // Blue dots above 'i' - represented with a Row
                            Row(
                                modifier = Modifier.offset(x = 0.dp, y = (-8).dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color(0xFF1A3A6B), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color(0xFF1A3A6B), CircleShape)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable for individual splash screen icons with circular background.
 * 
 * @param iconContent The icon content to display
 * @param label Optional label text below the icon
 */
@Composable
private fun SplashIcon(
    iconContent: @Composable () -> Unit,
    label: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Circular icon container with white border
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            iconContent()
        }
        
        // Optional label
        if (label != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

