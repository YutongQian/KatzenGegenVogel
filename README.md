# Katzen gegen Vögel

Ein vollständiges 2D-Rundenspiel in **Java und JavaFX**, entstanden als Abschlussprojekt
der Java-Weiterbildung (alfatraining, 320 UE, Zertifikat mit ausgezeichnetem Erfolg).

Vögel rücken in Wellen über ein Raster vor. Die Spielerin setzt Katzen auf Felder, die sie
abwehren. Jede Katzenart kostet Koloniefutter und verhält sich anders. Gewonnen ist das Spiel,
wenn alle Wellen abgewehrt sind, verloren, wenn Vögel das Raster durchbrechen.

## Tech Stack

- **Java 17**, JavaFX (Scene, Stage, StackPane, VBox, ImageView, Button)
- Objektorientiertes Design: abstrakte Klassen, Vererbung über zwei Ebenen, Interfaces, Enums
- **Reflection** zur dynamischen Instanziierung von Spielfiguren über eine Registry
- **Serialisierung** (`java.io.Serializable`) zur Persistenz der Spielerdaten
- **Eigene Exception-Klassen** zur Steuerung des Spielendes
- JavaDoc für öffentliche Klassen und Methoden, Git für die Versionsverwaltung

## Architektur

Vier Schichten mit klarer Richtung: `frontend → middletier → backend/dto`.
Die Spiellogik kennt keine UI-Komponenten; aus JavaFX wird dort nur `Image` als
Datenobjekt verwendet (keine Controls, keine Szenen).

| Paket | Rolle | Inhalt |
|---|---|---|
| `main` | Einstieg | `SpielApp` |
| `frontend` | Oberfläche | `SceneManager`, `SceneInter`, `RegisterScene`, `MenueScene`, `SpielScene`, `GameOverScene`, `GameGewinnenScene`, `AngriffAnimation` |
| `middletier` | Spiellogik | `SpielZustand`, `SpielLevel`, `Tier`, `Katze`, `Vogel`, `Stelle`, `VogelPlan`, `KatzeUndVogelRegistery`, `AngriffEvent` |
| `backend` | Persistenz | `Dateizugriff` |
| `dto` | Datenobjekt | `Person` |
| `asset` | Ressourcen | `Bild` (Enum) und Bilddateien |

### main

| Klasse | Aufgabe |
|---|---|
| `SpielApp` | `extends Application`; setzt Titel und Fenstergröße, initialisiert den `SceneManager`, registriert alle Szenen und zeigt die Startszene |

### frontend (Oberfläche)

| Klasse | Aufgabe |
|---|---|
| `SceneInter` | Interface mit `init()`, `destroy()`, `render()`, `getScene()` – einheitlicher Vertrag für alle Szenen |
| `SceneManager` | Zentrale Szenenverwaltung; hält die `Stage` und wechselt zwischen registrierten Szenen |
| `RegisterScene` | Registrierung/Anmeldung, Validierung der Eingaben, Anlegen des `Person`-Objekts |
| `MenueScene` | Hauptmenü |
| `SpielScene` | Spielfeld als Raster, Platzieren der Katzen, Futter- und Wellenanzeige (646 Zeilen, größte Klasse) |
| `GameOverScene` / `GameGewinnenScene` | Endzustände mit Neustart- bzw. Menü-Option |
| `AngriffAnimation` | Animierte Darstellung eines Angriffs |
| `SpielScene.FutterDrop` | Innere Klasse für das Einsammeln von Futter |

### middletier (Spiellogik)

**Spielsteuerung**

| Klasse | Aufgabe |
|---|---|
| `SpielZustand` | Zentraler Spielzustand: Setzen von Katzen, Zugberechnung, Wellen, Prüfung von Sieg/Niederlage (424 Zeilen) |
| `SpielLevel` | Levelkonfiguration, Auswahl der verfügbaren Katzentypen |
| `VogelPlan` | Erzeugt Vogelwellen; instanziiert die Vogeltypen per Reflection |
| `KatzeUndVogelRegistery` | Registry: `Map<String, Class<? extends Katze>>` und `List<Class<? extends Vogel>>` |
| `AngriffEvent` | Beschreibt einen Angriff (Angreifer, Ziel, Schaden) |

**Figuren – Vererbungshierarchie**

```
Tier (abstract)
├── Katze (abstract)
│   ├── WerferKatze (abstract)
│   │   ├── KurzWerferKatze
│   │   ├── LangWerferKatze
│   │   └── QueenKatze
│   ├── BehaelterKatze
│   │   ├── TankKatze
│   │   └── BodyguardKatze
│   ├── FeuerKatze
│   ├── HarvesterKatze
│   ├── HungerKatze
│   └── WandKatze
└── Vogel
    ├── Schwan
    └── Adler
```

**Spielfeld**

| Klasse | Aufgabe |
|---|---|
| `Stelle` | Ein Rasterfeld mit Belegung und Nachbarschaftslogik (224 Zeilen) |
| `BaumStelle` | Feld mit Baum – Sonderregeln für Vögel |
| `VogelNest` | Feld, aus dem Vögel nachrücken |

**Exceptions – Spielende als Ausnahme**

| Klasse | Bedeutung |
|---|---|
| `KatzenVerlorenException` | Vögel haben das Raster durchbrochen |
| `KatzeGewinnenException` | Alle Wellen abgewehrt |
| `GameOverException` | Generalfall |

`KatzeSpielTest` ist ein manueller Smoke-Test (Programm mit `main`-Methode). Die
automatisierten Tests liegen in `test/de/katzengegenvogel/middletier/SpielZustandTest.java`
(siehe Abschnitt „Tests").

### backend und dto (Daten)

| Klasse | Aufgabe |
|---|---|
| `Dateizugriff` | Laden und Speichern der Spielerdaten mit `ObjectOutputStream` / `ObjectInputStream` |
| `Person` | Serialisierbares Datenobjekt (`implements Serializable`) |

### asset

| Klasse | Aufgabe |
|---|---|
| `Bild` | Enum der verwendeten Bilddateien (Katzen, Vögel, Feld, Hintergrund) |

## Design-Entscheidungen

1. **Schichtentrennung statt direkter Kopplung.** Die Spiellogik arbeitet mit `Stelle`- und
   `Tier`-Objekten; aus JavaFX wird lediglich `Image` als Datenobjekt verwendet.
2. **Neue Figurentypen ohne Änderung der Spiellogik.** Die Registry bildet Namen auf
   `Class`-Objekte ab; `SpielZustand` und `VogelPlan` erzeugen Instanzen über
   `getDeclaredConstructor().newInstance()`. Eine neue Katzenart erfordert nur eine neue
   Klasse und einen Eintrag in der Registry.
3. **Spielende über eigene Exceptions** statt Rückgabecodes: `KatzenVerlorenException` und
   `KatzeGewinnenException` werden bis zur Oberfläche propagiert und dort in den passenden
   Szenenwechsel übersetzt.
4. **Persistenz über Serialisierung** mit einem expliziten DTO, damit Spielerdaten nicht an
   die internen Figurenklassen gekoppelt sind.
5. **Ein Vertrag für alle Szenen** (`SceneInter`): `init` / `render` / `destroy` macht
   Ressourcenfreigabe und Szenenwechsel einheitlich.

## Starten

1. JDK 17 oder neuer und ein JavaFX-SDK (oder IntelliJ mit JavaFX-Unterstützung)
2. Projekt in IntelliJ öffnen und JavaFX als Library einbinden
3. `de.katzengegenvogel.main.SpielApp` starten

## Tests

Automatisierte Tests mit **JUnit 5** für die Spiellogik
(`test/de/katzengegenvogel/middletier/SpielZustandTest.java`, 6 Tests):

| Test | Prüft |
|---|---|
| `registryEnthaeltZehnKatzentypen` | Registry liefert alle 10 Katzentypen |
| `einsetzenZiehtFutterkostenAb` | Futter sinkt um genau die Kosten der Katze |
| `ohneFutterKeinEinsetzen` | Ohne Futter wird keine Katze gesetzt (Rückgabe `null`) |
| `eingesetzteKatzenSindAuffindbar` | Gesetzte Katzen sind im Zustand auffindbar |
| `feldnameIstGrossgeschrieben` | Felder heißen `"1Feld6"`, nicht `"1feld6"` (dokumentiert eine Falle) |
| `simulierenLaeuftOhneFehler` | Ein Simulationsschritt auf frischem Spiel läuft durch |

Ausführen in IntelliJ: Ordner `test/` als **Test Sources Root** markieren, JUnit 5 zum
Classpath hinzufügen, dann `SpielZustandTest` starten.

## Bekannte Grenzen

- Getestet ist bisher die **Spiellogik**. Die UI-Schicht (`SpielScene`, 646 Zeilen) ist
  nicht automatisiert abgedeckt.
- Spielschleife und Darstellung laufen im **selben Thread**; sinnvoll wäre die Trennung von
  Logikaktualisierung und Rendering.
- Die Bilder liegen als Ressourcen im Quellordner statt unter `resources/`.
- Die Registry verwendet **String-Schlüssel**; ein Tippfehler fällt erst zur Laufzeit auf
  (besser: Enum oder Konstanten).
- Gespeichert werden nur die Spielerdaten, **nicht der Spielstand** selbst.

## Autorin

Yutong Qian · M.Sc. RWTH Aachen · [github.com/YutongQian](https://github.com/YutongQian)
