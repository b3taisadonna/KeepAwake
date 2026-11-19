# KeepAwake - Android App

Eine einfache Android-App, die dein Handy wach hält und verhindert, dass der Bildschirm ausgeschaltet wird.

## Features

✅ **WakeLock** - Hält den Bildschirm aktiv
✅ **Einfache Bedienung** - Ein Knopfdruck zum Aktivieren/Deaktivieren
✅ **Benachrichtigung** - Zeigt den Status in der Benachrichtigungsleiste
✅ **Kein Root** - Funktioniert ohne Root-Zugriff
✅ **Leichtgewichtig** - Minimaler Ressourcenverbrauch

## Verwendung

1. **App starten** - Installiere und öffne die App
2. **Aktivieren** - Tippe auf "Start" um den Bildschirm wach zu halten
3. **Deaktivieren** - Tippe auf "Stop" um den normalen Ruhezustand wiederherzustellen

## Technische Details

### Berechtigungen

- **WAKE_LOCK** - Erlaubt der App, den Bildschirm wach zu halten
- **FOREGROUND_SERVICE** - Ermöglicht den Hintergrunddienst (ab Android 9)
- **POST_NOTIFICATIONS** - Zeigt Benachrichtigungen an (ab Android 13)

### WakeLock-Typen

Die App verwendet:
- `SCREEN_BRIGHT_WAKE_LOCK` - Bildschirm bleibt hell
- `ACQUIRE_CAUSES_WAKEUP` - Weckt das Gerät auf, falls es schläft
- `ON_AFTER_RELEASE` - Bildschirm bleibt nach Release kurz an

### Android-Versionen

- **Minimum SDK**: 21 (Android 5.0 Lollipop)
- **Target SDK**: 34 (Android 14)

## Bauen

### Mit Android Studio

1. Projekt in Android Studio öffnen
2. Auf "Build > Build APK" klicken
3. APK wird generiert unter: `app/build/outputs/apk/debug/app-debug.apk`

### Mit Gradle

```bash
cd KeepAwake
./gradlew assembleDebug
```

## Problembehebung

### Berechtigungen werden nicht gewährt

1. Gehe zu Einstellungen > Apps > KeepAwake
2. Tippe auf "Berechtigungen"
3. Gewähre alle benötigten Berechtigungen manuell

### App funktioniert nicht

- Stelle sicher, dass du Android 5.0 oder höher hast
- Überprüfe, ob die Berechtigungen gewährt wurden
- Starte die App neu

### Bildschirm geht trotzdem aus

- Manche Geräte haben aggressive Energieeinstellungen
- Überprüfe die Energieeinstellungen deines Geräts
- Manche Hersteller (Xiaomi, Huawei, etc.) haben zusätzliche Energieoptimierungen

## Code-Struktur

```
KeepAwake/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/keepawake/
│   │   │   ├── KeepAwakeApp.kt      # Application-Klasse
│   │   │   ├── KeepAwakeService.kt  # Service mit WakeLock
│   │   │   └── MainActivity.kt      # Haupt-Aktivität
│   │   └── res/                      # Ressourcen
│   │       ├── layout/               # XML-Layouts
│   │       ├── drawable/             # Icons
│   │       ├── values/               # Strings, Colors, Themes
│   │       └── mipmap/               # App-Icons
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Anpassungen

### App-Name ändern

Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Dein App-Name</string>
```

### Farben ändern

Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="colorPrimary">#6200EE</color>
<color name="power_green">#4CAF50</color>
<color name="power_red">#F44336</color>
```

### WakeLock-Timeout ändern

Edit `KeepAwakeService.kt`:
```kotlin
wakeLock?.acquire(10 * 60 * 1000L /* 10 Minuten */)
```

Ändere den Wert auf die gewünschte Zeit in Millisekunden.

## Lizenz

MIT License - Freie Nutzung für alle Zwecke.
