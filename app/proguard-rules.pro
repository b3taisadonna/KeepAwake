# Android ProGuard rules for Password Manager

# Basic ProGuard rules for Android
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn javax.annotation.**

# Room Database
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Keep all entities and DAOs
-keep class com.b3taisadonna.passwordmanager.** { *; }

# Keep R classes
-keep class **.R$* { *; }

# Keep data classes
-keepclassmembers class * {
    @kotlinx.android.Parcelize <methods>;
}

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
    public static final * CREATOR;
}

# Encryption utilities
-keep class javax.crypto.** { *; }
-keep class java.security.** { *; }
