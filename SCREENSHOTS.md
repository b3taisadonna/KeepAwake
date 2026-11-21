# 📸 Schritt-für-Schritt Anleitung mit Screenshots

Hier findest du eine **visuelle Anleitung** für den Build-Prozess.

---

## 🖥️ Android Studio - Schritt für Schritt

### Schritt 1: Android Studio öffnen

```
┌─────────────────────────────────────────────────────────────┐
│  Android Studio                                             │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  Welcome to Android Studio                            │ │
│  │                                                           │ │
│  │  [Open an Existing Project]                           │ │
│  │  [New Project]                                         │ │
│  │  [Get from Version Control]                           │ │
│  └───────────────────────────────────────────────────────┘ │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**→ Klicke auf "Open an Existing Project"**

---

### Schritt 2: Projekt auswählen

```
┌─────────────────────────────────────────────────────────────┐
│  Select File or Directory to Import                        │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  /Users/deinname/                                      │ │
│  │  ├── KeepAwake/  ← WÄHLE DIESEN ORDNER                 │ │
│  │  │   ├── app/                                          │ │
│  │  │   ├── build.gradle.kts                              │ │
│  │  │   ├── settings.gradle.kts                           │ │
│  │  │   └── ...                                          │ │
│  │  └───────────────────────────────────────────────────┘ │
│  │                                                           │
│  │  [OK]                                                  │ │
│  └───────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────┘
```

**→ Wähle den `KeepAwake` Ordner und klicke auf "OK"**

---

### Schritt 3: Gradle Sync warten

```
┌─────────────────────────────────────────────────────────────┐
│  Android Studio                                             │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  ✅ Gradle Sync in progress...                         │ │
│  │  ┌─────────────────────────────────────────────────┐ │ │
│  │  │  Loading...  ████████░░░░░░░░  50%             │ │ │
│  │  └─────────────────────────────────────────────────┘ │ │
│  │                                                           │ │
│  │  ⚠️  If you see "Gradle project sync failed",          │ │
│  │     click "Sync Now" or "Try Again"                    │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**→ Warte, bis der Sync abgeschlossen ist (kann 1-5 Minuten dauern)**

---

### Schritt 4: APK bauen

**Option A: Über das Menü**

```
┌─────────────────────────────────────────────────────────────┐
│  Android Studio                                             │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  Build  ▼  Configure  ▼  Run  ▼  Debug  ▼             │ │
│  │                                                           │ │
│  │  [Build APK]  ← KLICKE HIER                            │ │
│  │  [Build Bundle]                                         │ │
│  │  [Generate Signed Bundle]                               │ │
│  │                                                           │ │
│  └───────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────┘
```

**Option B: Über die Toolbar**

```
┌─────────────────────────────────────────────────────────────┐
│  Android Studio                                             │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  [app (debug)]  ▼  [▶️ Run]  [🐞 Debug]              │ │
│  │                                                           │ │
│  │  ⚡ Build Variants: debug                               │ │
│  │  📱 Device: No devices (add emulator or connect phone) │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
│  Status: Build completed successfully! ✅                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### Schritt 5: APK finden

**Im Datei-Explorer:**

```
┌─────────────────────────────────────────────────────────────┐
│  Finder / Datei-Explorer                                   │
│                                                             │
│  KeepAwake/                                                │
│  ├── app/                                                  │
│  │   └── build/                                            │
│  │       └── outputs/                                       │
│  │           └── apk/                                       │
│  │               └── debug/                                 │
│  │                   └── app-debug.apk  ← DIESE DATEI!     │
│  └───────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────┘
```

**In Android Studio:**

```
┌─────────────────────────────────────────────────────────────┐
│  Android Studio - Project View                             │
│                                                             │
│  KeepAwake                                                  │
│  ├── app                                                   │
│  │   ├── src                                               │
│  │   └── build                                             │
│  │       └── outputs                                       │
│  │           └── apk                                        │
│  │               └── debug                                  │
│  │                   └── app-debug.apk  ← RECHTSKLICK →    │
│  │                           "Show in Explorer" /         │
│  │                           "Reveal in Finder"           │
│  └───────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────┘
```

---

## 📱 Auf Android-Gerät installieren

### Schritt 1: APK übertragen

**Option A: Per USB-Kabel**
```
┌─────────────────────────────────────────────────────────────┐
│  Windows/Mac                                                │
│                                                             │
│  1. Gerät per USB anschließen                              │
│  2. Dateiübertragung aktivieren (auf dem Gerät)             │
│  3. app-debug.apk in den Downloads-Ordner kopieren         │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Option B: Per E-Mail/Cloud**
```
┌─────────────────────────────────────────────────────────────┐
│  E-Mail / Google Drive / Dropbox                            │
│                                                             │
│  1. app-debug.apk als Anhang senden                         │
│  2. Auf dem Gerät die E-Mail öffnen                         │
│  3. APK herunterladen                                       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Schritt 2: APK installieren

```
┌─────────────────────────────────────────────────────────────┐
│  Android-Gerät                                              │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  📁 Dateimanager                                        │ │
│  │                                                           │ │
│  │  [Downloads]                                             │ │
│  │  ├── app-debug.apk  ← KLICKE HIER                      │ │
│  │  └── ...                                               │ │
│  │                                                           │ │
│  │  ⚠️  "Installation blocked"                              │ │
│  │  [Settings]                                             │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**→ Falls blockiert:**

```
┌─────────────────────────────────────────────────────────────┐
│  Android-Einstellungen                                     │
│                                                             │
│  Sicherheit                                                │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  Unbekannte Quellen                                     │ │
│  │  ┌─────────────────────────────────────────────────┐ │ │
│  │  │  ✅ Allow installation from unknown sources      │ │ │
│  │  │     (Aktivieren für Chrome/Dateimanager)        │ │ │
│  │  └─────────────────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎯 Erste Schritte in der App

### Schritt 1: Master-Passwort einrichten

```
┌─────────────────────────────────────────────────────────────┐
│  Password Manager                                           │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │        🔒                                                │ │
│  │                                                           │ │
│  │  Set Master Password                                     │ │
│  │                                                           │ │
│  │  ┌─────────────────────────────────────────────────┐ │ │
│  │  │  Master Password: ********                       │ │ │
│  │  └─────────────────────────────────────────────────┘ │ │
│  │                                                           │ │
│  │  [Login]  ← KLICKE HIER (erster Start = Passwort      │ │
│  │            festlegen)                                   │ │
│  │                                                           │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### Schritt 2: Passwort hinzufügen

```
┌─────────────────────────────────────────────────────────────┐
│  Password Manager                                           │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  Your Passwords                                         │ │
│  │                                                           │ │
│  │  No passwords saved yet                                  │ │
│  │                                                           │ │
│  │  ┌─────────┐                                             │ │
│  │  │  ➕     │  ← KLICKE HIER                             │ │
│  │  │Add      │                                             │ │
│  │  └─────────┘                                             │ │
│  │                                                           │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### Schritt 3: Passwort-Details eingeben

```
┌─────────────────────────────────────────────────────────────┐
│  Add Password                                               │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  Title:                                                │ │
│  │  ┌─────────────────────────────────────────────────┐ │ │
│  │  │  Google Account                                   │ │ │
│  │  └─────────────────────────────────────────────────┘ │ │
│  │                                                           │ │
│  │  Username:                                             │ │
│  │  ┌─────────────────────────────────────────────────┐ │ │
│  │  │  user@gmail.com                                   │ │ │
│  │  └─────────────────────────────────────────────────┘ │ │
│  │                                                           │ │
│  │  Password:                                             │ │
│  │  ┌─────────────────────────────────────────────────┐ │ │
│  │  │  ••••••••••••••••••••                              │ │ │
│  │  └─────────────────────────────────────────────────┘ │ │
│  │  Password Strength: Strong ✅                          │ │
│  │                                                           │ │
│  │  [Generate Password]  [Save]                         │ │
│  │                                                           │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

### Schritt 4: Passwort kopieren

```
┌─────────────────────────────────────────────────────────────┐
│  Password Details                                           │
│                                                             │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  Title: Google Account                                  │ │
│  │  Username: user@gmail.com                              │ │
│  │  Password: ••••••••••••••••••••                          │ │
│  │  URL: https://google.com                               │ │
│  │                                                           │ │
│  │  [👁️ Show]  [📋 Copy Username]  [📋 Copy Password]   │ │
│  │                                                           │ │
│  │  [Edit]  [Delete]                                      │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**→ Klicke auf "Copy Password" und das Passwort wird in die Zwischenablage kopiert!**

---

## 💡 Tipps & Tricks

### Tipp 1: Passwort generieren
```
┌─────────────────────────────────────────────────────────────┐
│  [Generate Password]  ← Klicke hier                    │
│                                                             │
│  Generiertes Passwort: xK9#pL2@qR7!mN4$                │
│  Strength: Very Strong ✅                                │
└─────────────────────────────────────────────────────────────┘
```

### Tipp 2: Passwörter suchen
```
┌─────────────────────────────────────────────────────────────┐
│  Your Passwords                                         │
│  ┌───────────────────────────────────────────────────────┐ │
│  │  🔍 Search passwords...                                │ │
│  │  (Tippe z.B. "Google" oder "gmail")                   │ │
│  └───────────────────────────────────────────────────────┘
│                                                             │
│  Ergebnisse:                                             │
│  - Google Account                                       │
│  - Gmail Backup                                         │
└─────────────────────────────────────────────────────────────┘
```

### Tipp 3: Master-Passwort ändern
```
┌─────────────────────────────────────────────────────────────┐
│  ⚠️  WICHTIG: Master-Passwort ändern                      │
│                                                             │
│  Aktuell nicht möglich in dieser Version.                 │
│  → App deinstallieren und neu installieren,               │
│     um ein neues Master-Passwort zu setzen.              │
│                                                             │
│  (In zukünftigen Versionen geplant)                      │
└─────────────────────────────────────────────────────────────┘
```

---

## 🚨 Häufige Fehler & Lösungen

### Fehler 1: "App not installed"
```
┌─────────────────────────────────────────────────────────────┐
│  ❌ App not installed                                       │
│                                                             │
│  Lösung:                                                    │
│  1. Alte Version deinstallieren                            │
│  2. APK erneut installieren                                │
│  3. Gerät neu starten                                      │
└─────────────────────────────────────────────────────────────┘
```

### Fehler 2: "Parsing the package failed"
```
┌─────────────────────────────────────────────────────────────┐
│  ❌ There was a problem parsing the package                  │
│                                                             │
│  Lösung:                                                    │
│  1. APK wurde nicht korrekt gebaut                          │
│  2. Versuche: ./gradlew clean && ./gradlew assembleDebug   │
│  3. Neue APK bauen und erneut installieren                 │
└─────────────────────────────────────────────────────────────┘
```

### Fehler 3: "Master password incorrect"
```
┌─────────────────────────────────────────────────────────────┐
│  ❌ Wrong master password                                    │
│                                                             │
│  Lösung:                                                    │
│  1. Prüfe Groß-/Kleinschreibung                            │
│  2. Versuche es erneut                                     │
│  3. Falls vergessen: App deinstallieren und neu starten      │
│     (Daten gehen verloren!)                                 │
└─────────────────────────────────────────────────────────────┘
```

---

**Viel Erfolg!** 🎉

Falls du weitere Hilfe brauchst, schau in die [BUILD.md](BUILD.md) oder erstelle ein Issue auf GitHub.
