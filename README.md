# Password Manager - Android App

Eine sichere Android-App zum Verwalten Ihrer Passwörter, ähnlich wie Proton Pass.

## 📱 Funktionen

✅ **Master-Passwort-Schutz** - Alle Passwörter werden mit einem Hauptpasswort gesichert
✅ **Passwörter hinzufügen/bearbeiten/löschen** - Volle Kontrolle über Ihre Passwörter
✅ **Passwort-Generator** - Erstellt sichere, zufällige Passwörter
✅ **Passwort-Stärke-Meter** - Zeigt an, wie sicher Ihr Passwort ist
✅ **Schnelle Suche** - Finden Sie Passwörter schnell
✅ **Kopieren in Zwischenablage** - Ein-Klick-Kopieren von Benutzername/Passwort
✅ **Material Design UI** - Moderne, benutzerfreundliche Oberfläche
✅ **Lokale Verschlüsselung** - Alle Daten werden lokal verschlüsselt gespeichert

## 🚀 Schnellstart

### Mit Android Studio

1. Repository klonen:
   ```bash
   git clone https://github.com/b3taisadonna/KeepAwake.git
   cd KeepAwake
   ```

2. In Android Studio öffnen
3. Auf **Build > Build APK** klicken
4. APK wird generiert unter: `app/build/outputs/apk/debug/app-debug.apk`

### Mit Gradle (Command Line)

1. Repository klonen
2. Gradle Wrapper ausführbar machen:
   ```bash
   chmod +x gradlew
   ```
3. APK bauen:
   ```bash
   ./gradlew assembleDebug
   ```
4. APK finden:
   ```bash
   ls app/build/outputs/apk/debug/
   ```

## 📦 Abhängigkeiten

- Android SDK 34 (Android 14)
- Minimum SDK: 24 (Android 7.0)
- Kotlin 1.9.0
- Room Database 2.5.2
- Material Components

## 🔒 Sicherheitshinweise

- **Alle Passwörter werden lokal gespeichert** - Keine Daten werden in die Cloud gesendet
- **Master-Passwort** wird als SHA-256 Hash gespeichert
- **AES-256 Verschlüsselung** für alle Passwortdaten
- **Kein Internetzugriff** erforderlich

## 🎨 Screenshots

- Login-Bildschirm mit Master-Passwort
- Passwortliste mit Suchfunktion
- Passwort hinzufügen/bearbeiten
- Passwort-Details mit Copy-Funktion

## 🤝 Mitwirken

Pull Requests sind willkommen! Für größere Änderungen bitte zuerst ein Issue erstellen.

## 📄 Lizenz

MIT License - Freie Nutzung für alle Zwecke.
