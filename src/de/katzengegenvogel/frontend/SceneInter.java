package de.katzengegenvogel.frontend;

import javafx.scene.Scene;
/**
 * <pre>
 *     Definiert die grundlegenden Methoden, die jede Szene der Anwendung
 *     implementieren muss.
 *     Über dieses Interface werden die Initialisierung, Darstellung
 *     und das Aufräumen einer Szene sowie der Zugriff auf die zugehörige
 *     JavaFX-Szene einheitlich festgelegt.
 *</pre>
 */
public interface SceneInter {
    /**
     * Initialisiert die Benutzeroberfläche der Registrierungs- und Anmeldeszene.
     *
     * <p>Hier werden die Textfelder, Buttons und weitere Elemente
     * für die Registrierung und Anmeldung erstellt.</p>
     */
    void init();

    /**
     * Gibt nicht mehr benötigte Ressourcen der Registrierungs- und
     * Anmeldeszene frei.
     *
     */
    void destroy();

    /**
     * Aktualisiert die Darstellung der Registrierungs- und Anmeldeszene.
     *
     */
    void render();


    /**
     * Gibt die JavaFX  Szene zurück
     * @return Scene
     */
    Scene getScene();
}
