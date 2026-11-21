# 📦 Build Anleitung - Password Manager APK

Hier findest du **drei Methoden**, um die APK zu bauen und auf deinem Android-Gerät zu testen.

---

## 🔧 **Methode 1: Mit Android Studio (Empfohlen für Anfänger)**

### Schritt 1: Android Studio installieren
- **Windows/Mac/Linux:** [Download Android Studio](https://developer.android.com/studio)
- **Mindestens 4GB RAM** empfohlen
- **Installationsanleitung:** [Offizielle Doku](https://developer.android.com/studio/install)

### Schritt 2: Repository klonen

**Option A: Mit GitHub Desktop**
1. [GitHub Desktop installieren](https://desktop.github.com/)
2. Datei → Clone Repository → URL: `https://github.com/b3taisadonna/KeepAwake.git`
3. Pfad auswählen und klonen

**Option B: Mit Terminal (Command Line)**
```bash
# Git installieren (falls nicht vorhanden)
# Windows: https://git-scm.com/download/win
# Mac: brew install git
# Linux: sudo apt install git

# Repository klonen
git clone https://github.com/b3taisadonna/KeepAwake.git
cd KeepAwake
```

### Schritt 3: Projekt in Android Studio öffnen

1. **Android Studio starten**
2. **"Open an Existing Project"** auswählen
3. Navigiere zu dem geklonten Ordner **`KeepAwake`** und wähle ihn aus
4. Android Studio lädt das Projekt automatisch

⚠️ **Wichtig:** Falls Android Studio fragt, ob es Gradle syncen soll, klicke auf **"Sync Now"**

### Schritt 4: APK bauen

Es gibt zwei Möglichkeiten:

**Option A: Debug APK (für Tests)**
1. Klicke auf **Build → Build APK**
2. Oder drücke **⌘B** (Mac) / **Ctrl+B** (Windows)
3. Warte, bis der Build abgeschlossen ist

**Option B: Über die Toolbar**
1. Wähle das **"app"** Modul in der Dropdown-Liste aus
2. Klicke auf den grünen **"Run"** Button (▶️)
3. Wähle dein Gerät oder einen Emulator aus

### Schritt 5: APK finden

Die APK wird hier gespeichert:
```
KeepAwake/app/build/outputs/apk/debug/app-debug.apk
```

**Auf deinem Gerät installieren:**
- Kopiere die Datei auf dein Android-Gerät
- Öffne die Datei und installiere sie
- Falls blockiert: **Einstellungen → Sicherheit → Unbekannte Quellen aktivieren**

---

## 🐧 **Methode 2: Mit Terminal (Command Line)**

### Voraussetzungen
- **Java JDK 17** (OpenJDK oder Oracle)
- **Git**

### Schritt 1: Java installieren

**Windows:**
```powershell
# Mit Chocolatey
choco install openjdk17

# Oder manuell
Download: https://adoptium.net/temurin/releases/?version=17
JAVA_HOME setzen: Systemumgebungsvariable JAVA_HOME = C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.10+7
```

**Mac:**
```bash
brew install openjdk@17
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### Schritt 2: Repository klonen
```bash
git clone https://github.com/b3taisadonna/KeepAwake.git
cd KeepAwake
```

### Schritt 3: Gradle Wrapper ausführbar machen
```bash
chmod +x gradlew
```

### Schritt 4: APK bauen
```bash
# Debug APK bauen
./gradlew assembleDebug

# Oder Release APK (für Veröffentlichung)
./gradlew assembleRelease
```

### Schritt 5: APK finden
```bash
# Debug APK
ls app/build/outputs/apk/debug/

# Release APK
ls app/build/outputs/apk/release/
```

### Schritt 6: APK installieren
```bash
# Auf dein Gerät kopieren (z.B. mit adb)
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## ☁️ **Methode 3: Mit GitHub Actions (Automatisch)**

### Schritt 1: Workflow auslösen

Der Workflow wird **automatisch** ausgelöst, wenn du Code zu `master` pushst.

### Schritt 2: Build-Status prüfen

1. Gehe zu: [https://github.com/b3taisadonna/KeepAwake/actions](https://github.com/b3taisadonna/KeepAwake/actions)
2. Klicke auf den neuesten **"Android Build"** Run
3. Warte, bis alle Schritte grün sind ✅

### Schritt 3: APK herunterladen

1. Scrolle nach unten zu **"Artifacts"**
2. Klicke auf **"app-debug"** (ZIP-Datei)
3. Lade die ZIP-Datei herunter
4. Entpacke sie und finde die `app-debug.apk`

### Schritt 4: APK installieren

Kopiere die APK auf dein Gerät und installiere sie manuell.

---

## 🐞 **Häufige Probleme & Lösungen**

### Problem 1: Gradle Sync fehlgeschlagen
**Fehlermeldung:** "Gradle project sync failed"

**Lösung:**
1. **File → Invalidate Caches / Restart**
2. **Gradle Wrapper aktualisieren:**
   ```bash
   ./gradlew wrapper --gradle-version 8.1.1
   ```
3. **Internetverbindung prüfen** (Gradle lädt Abhängigkeiten herunter)

### Problem 2: Java Version nicht gefunden
**Fehlermeldung:** "JAVA_HOME is not set"

**Lösung:**
```bash
# Java installieren (siehe Methode 2)
# Dann JAVA_HOME setzen

# Mac/Linux
nano ~/.bashrc  # oder ~/.zshrc
# Füge hinzu:
export JAVA_HOME=$(/usr/libexec/java_home -v 17)  # Mac
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk  # Linux
source ~/.bashrc

# Windows
# Systemumgebungsvariable JAVA_HOME setzen
```

### Problem 3: SDK nicht gefunden
**Fehlermeldung:** "Android SDK not found"

**Lösung:**
1. **Android Studio öffnen**
2. **Tools → SDK Manager**
3. **Android SDK 34** installieren
4. **Build Tools** installieren
5. **Project Structure → SDK Location** prüfen

### Problem 4: Keine Berechtigung für gradlew
**Fehlermeldung:** "Permission denied"

**Lösung:**
```bash
chmod +x gradlew
```

### Problem 5: Out of Memory Error
**Fehlermeldung:** "Java heap space"

**Lösung:**
```bash
# In gradle.properties (im Projektverzeichnis) hinzufügen:
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m
```

---

## 📱 **Erste Schritte nach der Installation**

1. **App öffnen** → Master-Passwort festlegen
2. **Passwort hinzufügen** (➕ Button)
3. **Passwörter verwalten** (Liste anzeigen, bearbeiten, löschen)
4. **Passwort kopieren** (Copy-Button)

---

## 🔒 **Sicherheitshinweise**

- **Master-Passwort nicht vergessen!** Ohne es kannst du keine Passwörter entschlüsseln
- **Backup erstellen:** Kopiere die APK und die Datenbank-Datei (`password_manager_db`)
- **Keine Cloud-Sync:** Alle Daten bleiben lokal auf deinem Gerät

---

## 🆘 **Hilfe & Support**

Falls du Probleme hast:
1. **Prüfe die Logs** in Android Studio (Build-Tab)
2. **Google die Fehlermeldung** + "Android Studio"
3. **Erstelle ein Issue** auf GitHub: [https://github.com/b3taisadonna/KeepAwake/issues](https://github.com/b3taisadonna/KeepAwake/issues)

---

## 📊 **Build-Informationen**

| Info | Wert |
|------|------|
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 34 (Android 14) |
| **Kotlin Version** | 1.9.0 |
| **Gradle Version** | 8.1.0 |
| **Build Tools** | 34.0.0 |
| **APK-Größe** | ~5-10 MB |

---

**Viel Erfolg!** 🎉

Falls du Fragen hast oder Hilfe brauchst, erstelle einfach ein Issue auf GitHub oder kontaktiere mich.
