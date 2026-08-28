package de.katzengegenvogel.frontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static asset.Bild.HINTERGRUND;

/**
 * <pre>
 *   Stellt die Gewinnszene des Spiels dar.
 *
 *   <p>Diese Szene wird angezeigt, wenn der Spieler das Spiel erfolgreich
 *   abgeschlossen hat. Sie informiert den Spieler über den erfolgreichen
 *   Abschluss und bietet die Möglichkeit, wieder zum Menü zurückzukehren.</p>
 *
 *   <p>Die Oberfläche der Szene besteht aus einem Hintergrund, einer
 *   Gewinnmeldung und einem Button zum Zurückkehren ins Menü.</p>
 * </pre>
 */
public class GameGewinnenScene implements SceneInter {
    private Scene scene;

    public GameGewinnenScene() {
        init();
    }
    /**
     * Initialisiert die Benutzeroberfläche der Gewinnszene.
     *
     * <p>Hier werden der Hintergrund, die Gewinnmeldung und die
     * benötigten Buttons erstellt und dem Root-Element hinzugefügt.</p>
     */
    @Override
    public void init() {

        StackPane root = new StackPane();

        ImageView hintergrund=new ImageView(HINTERGRUND.getImage());
        hintergrund.setFitHeight(1000);
        hintergrund.setFitWidth(800);
        hintergrund.setPreserveRatio(true);

        Label titel = new Label("Herzlichen Glückwunsch!");
        titel.setStyle(
                "-fx-text-fill: #c1e1c8;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, #24152F, 2, 1.0, 0, 0);"
        );

        Label text = new Label("Du hast alle Level geschafft!");
        text.setStyle("-fx-text-fill: #c1e1c8;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, #24152F, 2, 1.0, 0, 0);");
        Button zurueckMenu = new Button("Zurück zum Menü");
        zurueckMenu.setPrefSize(200, 40);

        Button nochmalSpielen = new Button("Nochmal spielen");
        nochmalSpielen.setPrefSize(200, 40);

        // Zurück zum Menü
        zurueckMenu.setOnAction(event -> {
            SceneManager.changeScene("Menu");
        });

        // Spiel von Level 1 neu starten
        nochmalSpielen.setOnAction(event -> {

            SpielScene spielScene =
                    (SpielScene) SceneManager.getSceneObkect("Spiel");

            spielScene.loadLevel(1);

            SceneManager.changeScene("Spiel");
        });

        VBox box = new VBox(20);

        box.setAlignment(Pos.CENTER);

        box.getChildren().addAll(
                titel,
                text,
                nochmalSpielen,
                zurueckMenu
        );

        root.getChildren().addAll(hintergrund,box);

        scene = new Scene(root, 1000, 800);
    }

    /**
     * Beendet bzw. bereinigt die Gewinnszene.
     * Es sind keine zusätzlichen Ressourcen zu entfernen.
     */
    @Override
    public void destroy() {

    }

    /**
     * Aktualisiert die Darstellung der Gewinnszene.
     * In der Gewinnszene ist keine laufende Aktualisierung erforderlich,
     *
     */
    @Override
    public void render() {

    }

    /**
     * Gibt die JavaFX dieser Gewinnen Szene zurueck
     * @return Scene
     */
    @Override
    public Scene getScene() {
        return scene;
    }

    }
