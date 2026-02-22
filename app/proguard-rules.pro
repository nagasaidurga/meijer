# R8 rules for Meijer App - Aggressive Optimizations

# Keep main application and activities
-keep public class com.example.meijer.MeijerApplication
-keep public class com.example.meijer.MainActivity { *; }

# Keep custom views
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# Keep data models (Parcelable and Serializable)
-keep class com.example.meijer.data.model.** { *; }
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Retrofit and OkHttp
-if class retrofit2.Retrofit
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keepattributes Signature
-keepattributes *Annotation*
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Gson
-keep class com.google.gson.Gson { *; }
-dontwarn sun.misc.**

# Coil
-keep class coil.** { *; }
-keep interface coil.** { *; }
-dontwarn coil.**

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# Google Play Services Location
-keep class com.google.android.gms.location.** { *; }
-dontwarn com.google.android.gms.**

# Jetpack Compose
-keepclasseswithmembers public class * {
    @androidx.compose.runtime.Composable <methods>;
}
-keepclassmembers public class * {
    @androidx.compose.runtime.Composable <fields>;
}
-keepclassmembers class * {
    @androidx.compose.ui.tooling.preview.Preview <methods>;
}
-assumenosideeffects class androidx.compose.runtime.ComposerKt {
    public static int sourceInformation(androidx.compose.runtime.Composer,java.lang.String);
    public static int sourceInformationMarkerStart(androidx.compose.runtime.Composer,int,java.lang.String);
    public static void sourceInformationMarkerEnd(androidx.compose.runtime.Composer);
}
-keepnames @androidx.compose.runtime.Composable fun *(..)

# Aggressive code shrinking
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    public static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
}
-assumenosideeffects class java.lang.System {
    public static void exit(int);
}

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}

# Keep line numbers for crash reports
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
