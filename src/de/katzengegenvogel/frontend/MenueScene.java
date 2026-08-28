package de.katzengegenvogel.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static asset.Bild.HINTERGRUND;

/**
 * <pre>
 *     Stellt das Hauptmenü des Spiels dar.
 *     Auf der Spielmenüseite können die Spieler zwischen dem
 *     Freispielmodus und dem Abenteuermodus wählen.
 *     Im Freispielmodus können sie einen Level auswählen,
 *     während der Abenteuermodus mit dem ersten Level beginnt.
 *
 *     Über die Buttons kann der Spieler zu den entsprechenden
 *     Szenen wechseln.
 * </pre>
 */
public class MenueScene implements SceneInter {
    private Scene scene;

    private StackPane root;


    public MenueScene() {
        init();

    }

    /**
     * Initialisiert die Benutzeroberfläche des Hauptmenüs.
     *
     * <p>Hier werden die Buttons, die Levelauswahl und der
     * Hintergrund erstellt und miteinander verbunden.</p>
     */
    @Override
    public void init() {
        root = new StackPane();


        VBox box = new VBox(15);
        ImageView hintergrund = new ImageView(HINTERGRUND.getImage());
        hintergrund.setFitHeight(1000);
        hintergrund.setFitWidth(800);
        hintergrund.setPreserveRatio(true);

        box.setAlignment(Pos.CENTER);

        Button abenteuermodus = new Button("Abenteuer modus");
//        abenteuermodus.setStyle(
//                        "-fx-font-size: 20px; " +
//                        "-fx-background-color: rgba(255,105,172,0.91);" +
//                        " -fx-text-fill: white; " +
//                        "-fx-padding: 10 40 10 40;"
//        );
        abenteuermodus.setPrefSize(300, 40);
        //
        abenteuermodus.setOnAction(event -> {
            SpielScene scene = (SpielScene) SceneManager.getSceneObkect("Spiel");

            scene.loadLevel(1);
            SceneManager.changeScene("Spiel");

        });
        Button freier = new Button("Freiermodus");
//        freier.setStyle(
//                "-fx-font-size: 20px; " +
//                "-fx-background-color: rgba(255,105,172,0.91);" +
//                " -fx-text-fill: white; " +
//                "-fx-padding: 10 40 10 40;"
//        );
        freier.setPrefSize(300, 40);

        ComboBox<String> levelCombobox = new ComboBox<>();

        levelCombobox.getItems().addAll("Level 1", "Level 2", "Level 3");
        levelCombobox.setValue("Level 1");
        levelCombobox.setPrefWidth(200);

        freier.setOnAction(event -> {
            String level = levelCombobox.getValue();
            String numberStr = level.replaceAll("\\D+", "");
            int ausgewahltLevel = Integer.parseInt(numberStr);
            SpielScene scene = (SpielScene) SceneManager.getSceneObkect("Spiel");
            if (ausgewahltLevel == 1) {
                scene.loadLevel(1);
            } else if (ausgewahltLevel == 2) {
                scene.loadLevel(2);
            } else if (ausgewahltLevel == 3) {
                scene.loadLevel(3);
            }
            SceneManager.changeScene("Spiel");

        });
        freier.setPrefSize(200, 40);

        box.getChildren().addAll(abenteuermodus, levelCombobox, freier);
        AnchorPane.setTopAnchor(box, 150.0);
        AnchorPane.setRightAnchor(box, 150.0);

        Button abmeldenButton = new Button("Abmelden");
        abmeldenButton.setOnAction(actionEvent -> {
            SceneManager.changeScene("Register");

        });
        root.getChildren().addAll(hintergrund, box);
        StackPane.setAlignment(abmeldenButton, Pos.TOP_LEFT);
        StackPane.setMargin(abmeldenButton, new Insets(20, 0, 0, 30));

        root.getChildren().add(abmeldenButton);
        scene = new Scene(root, 1000, 800);


    }


    @Override
    public void destroy() {

    }

    @Override
    public void render() {

    }
    /**
     * Gibt die JavaFX dieser Gewinnen Szene zurueck
     * @return Scene
     */
    public Scene getScene() {
        return scene;
    }
}
