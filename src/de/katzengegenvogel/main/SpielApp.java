package de.katzengegenvogel.main;

import de.katzengegenvogel.frontend.*;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Hauptklasse der Anwendung und Einstiegspunkt des Spiels.
 *
 * <pre>
 *     Die Klasse startet die JavaFX-Anwendung, erstellt die
 *     Hauptbühne und initialisiert den SceneManager. Anschließend
 *     werden die verschiedenen Szenen der Anwendung registriert
 *     und die Startszene angezeigt.
 *</pre>
 */
public class SpielApp extends Application {

    /**
     * Startet die JavaFX-Anwendung und initialisiert die Hauptbühne.
     *
     * <p>Die benötigten Szenen werden erstellt und beim
     * SceneManager registriert. Anschließend wird die gewünschte
     * Startszene angezeigt.</p>
     *
     * @param primaryStage Hauptfenster der JavaFX-Anwendung
     */

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Katze VS Vogel");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        SceneManager.init(primaryStage);
        RegisterScene registerScene=new RegisterScene();
        MenueScene menueScene=new MenueScene();
        SpielScene spielScene=new SpielScene();
        GameOverScene gameOverScene=new GameOverScene();
        GameGewinnenScene gameGewinnenScene=new GameGewinnenScene();
        gameOverScene.init();
        registerScene.init();
        menueScene.init();
        spielScene.init();
        gameOverScene.init();

        SceneManager.registiertScene("Register",registerScene,registerScene.getScene());
        SceneManager.registiertScene("Menu",menueScene,menueScene.getScene());
        SceneManager.registiertScene("Spiel",spielScene,spielScene.getScene());
        SceneManager.registiertScene("GameOver",gameOverScene,gameOverScene.getScene());
        SceneManager.registiertScene("GameWin",gameGewinnenScene,gameGewinnenScene.getScene());
        SceneManager.changeScene("Register");



    }
    /**
     * Startpunkt der Anwendung.
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        launch(args);
    }
}
