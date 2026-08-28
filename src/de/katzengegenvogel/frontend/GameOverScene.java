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
 *     Stellt  die Game Over Szene des Spiel dar.
 *     Diese Szene wird angezeigt , wenn der Spieler das Spiel verliert.
 *     Sie informiert den Spieler über das Spielende und bietet die Möglichkeit,
 *     wieder zum Menü zurückzukehren oder das Spiel erneut zu starten.
 * </pre>
 */
public class GameOverScene implements SceneInter {
    private Scene scene;
    /**
     * Initialisiert die Benutzeroberfläche der Game Over Szene.
     * Hier werden der Hintergrund, die Game-Over-Meldung
     * und die benötigten Buttons erstellt.
     */
    @Override
    public void init() {
        StackPane root = new StackPane();

        ImageView hintergrund=new ImageView(HINTERGRUND.getImage());
        hintergrund.setFitHeight(1000);
        hintergrund.setFitWidth(800);
        hintergrund.setPreserveRatio(true);

        Label gameOver = new Label("GAME OVER");
        gameOver.setStyle("-fx-text-fill: #e1d2c1;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, #24152F, 2, 1.0, 0, 0);");

        Button nochmal = new Button("Nochmal spielen");
        Button zuruck = new Button("Zurück");

        nochmal.setOnAction(e -> {

            SpielScene spiel =
                    (SpielScene) SceneManager.getSceneObkect("Spiel");

            spiel.loadLevel(1);

            SceneManager.changeScene("Spiel");
        });
        zuruck.setOnAction(event -> {
            SceneManager.changeScene("Menu");

        });
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        box.getChildren().addAll(gameOver, nochmal,zuruck);

        root.getChildren().addAll(hintergrund,box);

        scene = new Scene(root, 1000, 800);

    }

    @Override
    public void destroy() {

    }

    @Override
    public void render() {

    }
    @Override

    public Scene getScene() {
        return scene;
    }
}
