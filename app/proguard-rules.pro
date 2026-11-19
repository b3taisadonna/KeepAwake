-ignorewarnings

# Keep all Activities, Services, and BroadcastReceivers
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

# Keep all classes in com.example.keepawake
-keep class com.example.keepawake.** { *; }

# Keep all ViewModels
-keep public class * extends androidx.lifecycle.ViewModel

# Keep all Composable functions
-keep class *Kt {
    public * compose(...);
}

# Keep R classes
-keep class **.R$* { *; }

# Keep all annotations
-keepattributes *Annotation*
