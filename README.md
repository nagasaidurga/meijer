# Meijer Product Information App

A native Android mobile application built with Kotlin and Jetpack Compose that displays product information from the Meijer Firebase Realtime Database API.


## Features

### Product Information API Implementation
- ✅ **Products API**: Fetches and displays a list of products with image, title, and summary
- ✅ **Product Detail API**: Fetches and displays detailed product information including full image, title, description, and price

### UI/UX Implementation
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
  
## UI 

<img  width="300" style="display: block; margin: 0 auto;" alt="Screenshot_20260222_124333" src="https://github.com/user-attachments/assets/4e1ca245-d73e-47d0-bf3c-f7daecf9965b" /> <br/> <br/> <br/>
<img width="300" style="display: block; margin: 0 auto;" alt="Screenshot_20260222_124352" src="https://github.com/user-attachments/assets/680453e8-34e9-48f9-81ca-bac0a1dc7734" />



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
- **Testing**: JUnit, Mockito

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


## Author
Naga Sai Durga Ambati

