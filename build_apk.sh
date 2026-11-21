#!/bin/bash

# =============================================================================
# Password Manager - APK Build Script
# =============================================================================
#
# Dieses Skript baut die Password Manager APK automatisch.
# Vorraussetzung: Java JDK 17 und Git müssen installiert sein.
#
# Verwendung:
#   chmod +x build_apk.sh
#   ./build_apk.sh
#
# =============================================================================

set -e  # Beende bei Fehlern

# Farben für die Ausgabe
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Funktion für farbige Ausgabe
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Schritt 1: Willkommensnachricht
clear
print_status "=========================================="
print_status "  Password Manager - APK Build Script"
print_status "=========================================="
echo ""

# Schritt 2: Prüfe Java Installation
print_status "Prüfe Java Installation..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    print_success "Java gefunden: $JAVA_VERSION"
else
    print_error "Java nicht gefunden!"
    echo ""
    echo "Bitte installiere Java JDK 17:"
    echo "  - Mac: brew install openjdk@17"
    echo "  - Linux: sudo apt install openjdk-17-jdk"
    echo "  - Windows: https://adoptium.net/temurin/releases/?version=17"
    echo ""
    exit 1
fi

# Schritt 3: Prüfe Git Installation
print_status "Prüfe Git Installation..."
if command -v git &> /dev/null; then
    GIT_VERSION=$(git --version)
    print_success "Git gefunden: $GIT_VERSION"
else
    print_error "Git nicht gefunden!"
    echo ""
    echo "Bitte installiere Git:"
    echo "  - Mac: brew install git"
    echo "  - Linux: sudo apt install git"
    echo "  - Windows: https://git-scm.com/download/win"
    echo ""
    exit 1
fi

# Schritt 4: Prüfe, ob wir im richtigen Verzeichnis sind
if [ ! -f "build.gradle.kts" ] || [ ! -f "settings.gradle.kts" ]; then
    print_error "Dieses Skript muss im Projektverzeichnis ausgeführt werden!"
    echo ""
    echo "Wechsle zuerst in das Password Manager Verzeichnis:"
    echo "  cd /pfad/zum/KeepAwake"
    echo ""
    exit 1
fi

print_success "Projektverzeichnis erkannt: $(pwd)"

# Schritt 5: Gradle Wrapper ausführbar machen
print_status "Mache Gradle Wrapper ausführbar..."
chmod +x gradlew
print_success "Gradle Wrapper ist jetzt ausführbar"

# Schritt 6: Gradle Sync durchführen
print_status "Führe Gradle Sync durch..."
if ./gradlew --version &> /dev/null; then
    print_success "Gradle Wrapper funktioniert"
else
    print_error "Gradle Wrapper funktioniert nicht!"
    echo ""
    echo "Versuche, den Gradle Wrapper zu aktualisieren:"
    echo "  ./gradlew wrapper --gradle-version 8.1.1"
    echo ""
    exit 1
fi

# Schritt 7: APK bauen
print_status "Baue Debug APK..."
echo ""

# Build starten
if ./gradlew assembleDebug; then
    print_success "APK erfolgreich gebaut! 🎉"
else
    print_error "Build fehlgeschlagen!"
    echo ""
    echo "Mögliche Lösungen:"
    echo "  1. Internetverbindung prüfen (Gradle lädt Abhängigkeiten herunter)"
    echo "  2. Android SDK 34 installieren"
    echo "  3. Mehr Speicher zuweisen: org.gradle.jvmargs=-Xmx2048m"
    echo ""
    exit 1
fi

# Schritt 8: APK finden und anzeigen
print_status "Suche nach der APK..."
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ -f "$APK_PATH" ]; then
    APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
    print_success "APK gefunden: $APK_PATH"
    print_success "APK Größe: $APK_SIZE"
    echo ""
    echo "=========================================="
    echo "  APK erfolgreich gebaut!"
    echo "=========================================="
    echo ""
    echo "Die APK befindet sich hier:"
    echo "  $(pwd)/$APK_PATH"
    echo ""
    echo "Auf deinem Android-Gerät installieren:"
    echo "  1. APK auf dein Gerät kopieren (USB, E-Mail, Cloud)"
    echo "  2. Auf dem Gerät die APK öffnen"
    echo "  3. Installation bestätigen"
    echo "  4. Falls blockiert: Unbekannte Quellen aktivieren"
    echo ""
else
    print_error "APK nicht gefunden!"
    echo ""
    echo "Suche in anderen Verzeichnissen..."
    find app/build/outputs -name "*.apk" 2>/dev/null || echo "Keine APK gefunden"
    exit 1
fi

# Schritt 9: Option zum direkten Installieren mit adb
if command -v adb &> /dev/null; then
    echo ""
    print_status "adb gefunden! Möchtest du die APK direkt installieren?"
    read -p "(j/n): " -n 1 -r
    echo ""
    if [[ $REPLY =~ ^[Jj]$ ]]; then
        print_status "Installiere APK mit adb..."
        if adb install "$APK_PATH"; then
            print_success "APK erfolgreich installiert!"
        else
            print_error "Installation fehlgeschlagen!"
            print_warning "Stelle sicher, dass:"
            print_warning "  1. Ein Gerät per USB angeschlossen ist"
            print_warning "  2. USB-Debugging aktiviert ist"
            print_warning "  3. adb devices ein Gerät anzeigt"
        fi
    fi
fi

echo ""
print_success "Fertig! 🎉"
echo ""
