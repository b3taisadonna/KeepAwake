# KeepAwake - Chrome Extension

Eine Chrome-Erweiterung, die deinen Bildschirm wach hält, während du Chrome verwendest.

## Features

✅ **Screen Wake Lock API** - Hält den Bildschirm aktiv (experimentell)
✅ **Fallback-Modus** - Öffnet eine Seite, die den Wake Lock anfordert
✅ **Benachrichtigungen** - Zeigt den Status in Chrome-Benachrichtigungen
✅ **Timeout** - Automatisches Ausschalten nach einer bestimmten Zeit
✅ **Einfache Bedienung** - Ein-Knopf-Steuerung

## Installation

### Methode 1: Entwicklermodus (empfohlen für Tests)

1. **Lade die Extension herunter** oder klone das Repository
2. Öffne Chrome und gehe zu: `chrome://extensions/`
3. Aktiviere den **Entwicklermodus** (oben rechts)
4. Klicke auf **"Entpackte Erweiterung laden"**
5. Wähle den Ordner `chrome-extension` aus

### Methode 2: Als ZIP-Datei

1. Komprimiere den `chrome-extension` Ordner zu einer ZIP-Datei
2. Gehe zu `chrome://extensions/`
3. Aktiviere den Entwicklermodus
4. Ziehe die ZIP-Datei in den Extensions-Bereich

### Methode 3: Veröffentlichen im Chrome Web Store

1. Erstelle ein ZIP-Archiv der Extension
2. Gehe zum [Chrome Web Store Developer Dashboard](https://chrome.google.com/webstore/developer/dashboard)
3. Lade die ZIP-Datei hoch
4. Fülle alle erforderlichen Informationen aus
5. Veröffentliche die Extension

## Verwendung

### Basic Usage

1. Klicke auf das **KeepAwake-Icon** in der Chrome-Leiste
2. Klicke auf **"Start"**, um den Bildschirm wach zu halten
3. Klicke auf **"Stop"**, um den normalen Ruhezustand wiederherzustellen

### Optionen

- **Timeout (Minuten)**: Legt fest, nach wie vielen Minuten die Extension automatisch stoppt (0 = nie)
- **Benachrichtigung anzeigen**: Zeigt eine Benachrichtigung, wenn die Extension aktiv/inaktiv ist

## Technische Details

### Screen Wake Lock API

Die Extension versucht, die **Screen Wake Lock API** zu verwenden, die in modernen Browsern verfügbar ist. Diese API:
- Verhindert, dass der Bildschirm ausgeht
- Funktioniert nur, wenn der Tab aktiv ist
- Wird automatisch freigegeben, wenn der Tab geschlossen wird

### Browser-Kompatibilität

| Browser | Screen Wake Lock API | Fallback-Modus |
|---------|---------------------|----------------|
| Chrome 84+ | ✅ Ja | ✅ Ja |
| Edge 84+ | ✅ Ja | ✅ Ja |
| Firefox | ❌ Nein | ✅ Ja |
| Safari | ❌ Nein | ❌ Nein |

### Einschränkungen

1. **Hintergrund-Tabs**: Die Screen Wake Lock API funktioniert nur, wenn der Tab aktiv ist
2. **Mobile Geräte**: Auf Android funktioniert die API nur in Chrome/Edge
3. **Energiesparmodus**: Manche Geräte ignorieren den Wake Lock im Energiesparmodus
4. **Browser-Einstellungen**: Manche Browser blockieren die API standardmäßig

## Dateistruktur

```
chrome-extension/
├── manifest.json          # Extension-Manifest (V3)
├── keepawake.html         # Fallback-Seite für Wake Lock
├── keepawake.js           # Script für Wake Lock
├── popup/
│   ├── popup.html        # Popup-HTML
│   ├── popup.css         # Popup-Styles
│   └── popup.js          # Popup-Logik
├── background/
│   └── background.js      # Background-Service
├── icons/
│   ├── icon.svg          # SVG-Vorlage
│   ├── icon16.png        # 16x16 Icon (erforderlich)
│   ├── icon32.png        # 32x32 Icon (erforderlich)
│   ├── icon48.png        # 48x48 Icon (erforderlich)
│   └── icon128.png       # 128x128 Icon (erforderlich)
└── README.md
```

## Icons erstellen

Die Extension benötigt PNG-Icons in verschiedenen Größen. Siehe [icons/README.md](icons/README.md) für Anleitungen.

### Schnelllösung

Falls du keine Icons erstellen möchtest, kannst du temporär einfache farbige Quadrate verwenden:
1. Erstelle ein 128x128 Pixel Bild mit Hintergrundfarbe `#6200EE` (Purple)
2. Speichere es als `icon128.png`
3. Skaliere es auf die anderen Größen (16, 32, 48)

## Anpassungen

### Manifest ändern

Edit `manifest.json`:
```json
{
  "name": "Dein Name",
  "version": "1.0.0",
  "description": "Deine Beschreibung"
}
```

### Farben ändern

Edit `popup/popup.css`:
```css
.btn-primary {
    background-color: #FF5722; /* Orange */
}
```

### Timeout-Default ändern

Edit `popup/popup.js`:
```javascript
const timeoutInput = document.getElementById('timeout');
timeoutInput.value = 30; // Default: 30 Minuten
```

## Problembehebung

### Extension wird nicht geladen

1. Stelle sicher, dass der Entwicklermodus aktiviert ist
2. Überprüfe, dass der Pfad korrekt ist
3. Überprüfe die `manifest.json` auf Syntaxfehler

### Wake Lock funktioniert nicht

1. Stelle sicher, dass du Chrome 84+ verwendest
2. Der Tab muss aktiv sein (nicht im Hintergrund)
3. Manche Websites blockieren die API aus Sicherheitsgründen

### Benachrichtigungen werden nicht angezeigt

1. Überprüfe, ob die Berechtigung erteilt wurde
2. Stelle sicher, dass Chrome-Benachrichtigungen aktiviert sind

### Timeout funktioniert nicht

1. Überprüfe, ob der Wert eine Zahl ist
2. Stelle sicher, dass der Wert >= 0 ist

## Berechtigungen

Die Extension benötigt folgende Berechtigungen:

- **`alarms`** - Für den Timeout-Timer
- **`notifications`** - Für Benachrichtigungen
- **`storage`** - Für das Speichern der Einstellungen
- **`tabs`** - Für das Öffnen von Tabs

Diese Berechtigungen werden automatisch angefordert, wenn die Extension installiert wird.

## Lizenz

MIT License - Freie Nutzung für alle Zwecke.
