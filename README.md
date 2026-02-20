# Meijer Product Information App

A native Android mobile application built with Kotlin and Jetpack Compose that displays product information from the Meijer Firebase Realtime Database API.

## Features

### Task 1: Product Information API Implementation
- ✅ **Products API**: Fetches and displays a list of products with image, title, and summary
- ✅ **Product Detail API**: Fetches and displays detailed product information including full image, title, description, and price

### Task 2: UI/UX Implementation
- ✅ **Product List Screen**: Displays products in a scrollable list with:
  - Product image thumbnail
  - Product title
  - Product summary
  - Clickable items that navigate to detail screen
  
- ✅ **Product Detail Screen**: Displays comprehensive product information with:
  - Full product image
  - Product title
  - Detailed description
  - Product price
  - "Add to List" share functionality

- ✅ **Add to List Feature**: 
  - Generates shareable text in format: "{product title} - {price} from {city name} added to list"
  - Uses device location to get current city name
  - Shares through Android's native share intent (supports text messages, email, social media, etc.)

## Architecture

The app follows **MVVM (Model-View-ViewModel) Clean Architecture** principles:

### Layers

1. **Data Layer**
   - `data/model/`: Data models (Product, ProductDetail)
   - `data/api/`: Retrofit API service and client configuration
   - `data/repository/`: Repository pattern for data management

2. **Domain Layer** (implicit)
   - Business logic handled in ViewModels

3. **Presentation Layer**
   - `ui/viewmodel/`: ViewModels for state management
   - `ui/state/`: UI state data classes
   - `ui/screen/`: Jetpack Compose UI screens
   - `navigation/`: Navigation graph setup

4. **Utility Layer**
   - `util/LocationHelper`: Location services for getting city name
   - `util/PermissionHandler`: Runtime permission handling

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Clean Architecture
- **Networking**: Retrofit 2.9.0 with OkHttp
- **Image Loading**: Coil 2.5.0
- **Navigation**: Jetpack Navigation Compose
- **Coroutines**: Kotlin Coroutines for asynchronous operations
- **Location Services**: Google Play Services Location API
- **Testing**: JUnit, Mockito, Turbine

## Project Structure

```
app/src/main/java/com/example/meijer/
├── data/
│   ├── api/
│   │   ├── ProductApiService.kt      # Retrofit API interface
│   │   └── RetrofitClient.kt         # Retrofit client configuration
│   ├── model/
│   │   ├── Product.kt                # Product data model
│   │   └── ProductDetail.kt          # Product detail data model
│   └── repository/
│       └── ProductRepository.kt      # Repository for data operations
├── navigation/
│   └── NavGraph.kt                   # Navigation graph setup
├── ui/
│   ├── screen/
│   │   ├── ProductListScreen.kt      # Product list UI
│   │   └── ProductDetailScreen.kt    # Product detail UI
│   ├── state/
│   │   ├── ProductListUiState.kt     # Product list UI state
│   │   └── ProductDetailUiState.kt   # Product detail UI state
│   ├── theme/                        # Material Design theme
│   └── viewmodel/
│       ├── ProductListViewModel.kt   # Product list ViewModel
│       └── ProductDetailViewModel.kt # Product detail ViewModel
├── util/
│   ├── LocationHelper.kt             # Location services helper
│   └── PermissionHandler.kt          # Permission handling utility
└── MainActivity.kt                   # Main activity entry point
```

## API Endpoints

### Products API
- **URL**: `https://meijer-maui-test-default-rtdb.firebaseio.com/products.json`
- **Method**: GET
- **Response**: Array of Product objects

### Product Detail API
- **URL**: `https://meijer-maui-test-default-rtdb.firebaseio.com/product-details/{productId}.json`
- **Method**: GET
- **Parameters**: `productId` (integer)
- **Response**: ProductDetail object

## Permissions

The app requires the following permissions (declared in `AndroidManifest.xml`):
- `INTERNET`: For API calls
- `ACCESS_FINE_LOCATION`: For getting precise location to determine city name
- `ACCESS_COARSE_LOCATION`: For getting approximate location to determine city name

**Note**: Location permissions are requested at runtime. The app gracefully handles cases where location permission is not granted by using "Unknown" as the city name.

## Setup Instructions

1. **Clone the repository** (if applicable)

2. **Open in Android Studio**
   - Open the project in Android Studio
   - Sync Gradle files

3. **Build and Run**
   - Connect an Android device or start an emulator (API 30+)
   - Click "Run" or press Shift+F10

4. **Grant Permissions** (when prompted)
   - Allow location permissions when the app requests them for the "Add to List" feature

## Testing

Unit tests are included for:
- `ProductListViewModelTest`: Tests product list state management
- `ProductDetailViewModelTest`: Tests product detail state management and share text generation
- `ProductRepositoryTest`: Tests repository data operations (structure provided)

Run tests using:
```bash
./gradlew test
```

## Key Implementation Details

### State Management
- Uses Kotlin Flow (`StateFlow`) for reactive state management
- UI automatically updates when state changes
- Proper loading, success, and error states

### Error Handling
- Network errors are caught and displayed to the user
- Retry functionality available on error screens
- Graceful degradation when location is unavailable

### Image Loading
- Uses Coil library for efficient image loading and caching
- Images are loaded asynchronously with crossfade animation
- Placeholder handling for failed image loads

### Navigation
- Type-safe navigation with arguments
- Proper back stack management
- Product ID passed as navigation argument

### Share Functionality
- Uses Android's native share intent
- Supports all apps that can handle text sharing (SMS, Email, Social Media, etc.)
- Format: "{Product Title} - {Price} from {City Name} added to list"

## Code Quality

- ✅ Comprehensive code comments explaining logic and purpose
- ✅ Clean architecture with separation of concerns
- ✅ MVVM pattern for testability
- ✅ Kotlin best practices
- ✅ Material Design 3 components
- ✅ Proper error handling
- ✅ Unit test structure

## Future Enhancements (Optional)

- Dependency injection with Hilt/Koin
- Caching with Room database
- Offline support
- Pull-to-refresh functionality
- Search and filter capabilities
- Product favorites/bookmarks
- Enhanced error messages with retry strategies
- Loading skeletons instead of progress indicators

## Author

Built as part of the Meijer Interview Coding Challenge.

