package de.katzengegenvogel.frontend;

import de.katzengegenvogel.middletier.*;
import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.stelle.Stelle;
import de.katzengegenvogel.middletier.vogel.Vogel;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

import static asset.Bild.*;
/**
 * <pre>
 * Die Klasse stellt die eigentliche Spielszene des Spiels
 * „Katzen gegen Vogel“ dar.
 *
 * <p>In dieser Szene wird das Spielfeld gezeichnet und das laufende Spiel
 * gesteuert. Dazu gehören unter anderem die Darstellung des Rasens, der
 * Katzen, der Vögel, der Futterkarten und des aktuellen Levels.</p>
 *
 * <p>Die Klasse verarbeitet außerdem die Maussteuerung. Der Spieler kann
 * Katzen aus der Auswahlleiste auswählen und auf freie Felder des Spielfelds
 * setzen. Während des Ziehens wird eine Vorschau der ausgewählten Katze
 * angezeigt.</p>
 *
 * <p>Ein {@link javafx.animation.AnimationTimer} aktualisiert das Spiel
 * kontinuierlich. Dabei werden der Spielzustand simuliert, Futter und Vögel
 * aktualisiert und anschließend das Spielfeld neu gerendert.</p>
 *
 * <p>Wenn der Spieler ein Level gewinnt oder verliert, wird die entsprechende
 * Szene angezeigt. Beim erfolgreichen Abschluss eines Levels wird zum nächsten
 * Level gewechselt. Nach dem letzten Level wird die Game-Win-Szene geöffnet.
 * Bei einer Niederlage wird die Game-Over-Szene angezeigt.</p>
 * </pre>
 */

public class SpielScene implements SceneInter {


    private final int WIDTH = 1000;
    private final int HEIGHT = 700;
    /**
     * Auswahlfeld für Spielaufgaben
     */
    private final double figureStartX = 100;
    private final double figureStartY = 100;
    /**
     * Spielbereich
     */
    private double gridStartX;
    private double gridStartY;
    /**
     * Größe der einzelnen Blöcke im Spiel
     */
    private double gridWidth;
    private double gridHeight;
    /**
     * zeile für Spiele Bereiche
     */
    private int zeile;
    /**
     * Spalte für SpielBereiche
     */
    private int spalte;
    /***/
    private int[][] rasenPlatz;

    private double mouseMovedX;
    private double mouseMovedY;
    private int jetztLevel = 1;
    /** Label mit der aktuellen Levelnummer; wird in loadLevel() aktualisiert. */
    private Label levelText;


    private Scene spielScene;

    private List<FutterDrop> futterDrop = new ArrayList<>();
    private double futterDropSpeed = 1;
    private long futterDropAbstand = 2_000;


    private long letztSpawnZeit;
    private Random random = new Random();
    private final Map<String, Class<? extends Katze>> katzeTypenObjekte = KatzeUndVogelRegistery.getAlleKatzeTypen();
    private boolean isDragging = false;
    private int katzeTypeAusgewahlt = -1;
    private Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private GraphicsContext content;
    private StackPane root;

    private String[] katzenameList;
    private int[] katzeFutterKosten;
    private Image[] katzeImage;

    private SpielLevel spielLevel = new SpielLevel();
    private SpielZustand spielZustand;
    private List<AngriffAnimation> angriffe = new ArrayList<>();
    private AnimationTimer gameTimer;
    private boolean spielBeendet=false;

    public SpielScene() {
        init();
        gameTimer.start();

    }


      /**
       * Initialisiert die Spielszene und richtet die Benutzerinteraktionen
       * sowie den Gametimer ein.
       *
       * <p>Der Gametimer aktualisiert regelmäßig den Spielzustand und
       * zeichnet das aktuelle Spielfeld neu.</p>
       */

    @Override
    public void init() {
        root = new StackPane();

        ImageView hintergrund=new ImageView(HINTERGRUND.getImage());
        hintergrund.setFitHeight(HEIGHT);
        hintergrund.setFitWidth(WIDTH);
        hintergrund.setMouseTransparent(true);
        hintergrund.setPreserveRatio(false);

        content = canvas.getGraphicsContext2D();
//        root.getChildren().addAll(hintergrund,canvas);
        levelText = new Label("Level " + jetztLevel);
        StackPane.setAlignment(levelText, Pos.TOP_CENTER);
        StackPane.setMargin(levelText,new Insets(15,0,0,0));
        levelText.setStyle(
                        "-fx-font-size: 30px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );
        root.getChildren().addAll(hintergrund,canvas);
        root.getChildren().add(levelText);
        this.spielScene = new Scene(root, WIDTH, HEIGHT);
//        Button stoppButton = new Button("Stop");
//        Button startButton = new Button("Start");

        canvas.setOnMouseMoved(event -> {
            if (isDragging) {
                mouseMovedX = event.getX();
                mouseMovedY = event.getY();
            }
        });

        canvas.setOnMouseClicked(event -> {

            futterDropEinsammeln(event);
            if (!isDragging) {
                katzeAuswaehlen(event);
            } else {
                stelleAnklicken(event);
            }

        });
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long jetzt) {
                if (spielZustand == null) {
                    return;
                }
                try {
                    updatefutter();
                    spielZustand.simulieren();
                    neueAngriffeErstellen();
                    updateAngriffe();


                    content.clearRect(0, 0, WIDTH, HEIGHT);
                    render();


                }catch(KatzeGewinnenException ausnahme){
                    if(spielBeendet){
                        return;
                    }
                    spielBeendet = true;
                    gameTimer.stop();
                    gehenZuNaechsteLevel();

                }catch(KatzenVerlorenException ausnahme){
                    gameTimer.stop();
                    SceneManager.changeScene("GameOver");

                }
            }
        };


    }

    public void startGame() {
        if (gameTimer != null) {
            gameTimer.start();
        }
    }


    /**
     * Beendet den Spieltimer und stoppt damit die laufende Spielsimulation.
     */
    @Override
    public void destroy() {
        if (gameTimer != null) {
            gameTimer.stop();
        }

    }

    @Override
    public void render() {
        content.clearRect(0, 0, WIDTH, HEIGHT);
        renderGame(content);

    }


    /**
     * Wenn der Spieler mit der Maus auf die Katzenkarte klickt,
     * wird eine ausgewählte Katze generiert.
     */

    private void katzeAuswaehlen(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        if (y >= figureStartY && y <= (figureStartY + gridHeight)) {
            if (x >= figureStartX && x <= figureStartX + gridWidth * katzenameList.length) {

                katzeTypeAusgewahlt = (int) ((x - figureStartX) / gridWidth);
                isDragging = true;
                ImagePattern neuKatze = erstellenNeuKatzeInstance(katzeTypeAusgewahlt);


            }
        }

    }
    /**
     * Verarbeitet den Mausklick auf eine Stelle des Spielfeldes.
     *
     * Die ausgewählte Katze wird an der angeklickten Stelle platziert,
     * sofern die Position gültig und der entsprechende Platz frei ist.
     *
     * @param event Mausereignis mit der Position des Mausklicks
     */
    private void stelleAnklicken(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        if (isDragging) {
            if (x <= gridStartX && x >= gridStartX - gridWidth * spalte && y >= gridStartY && y <= gridStartY + gridHeight * zeile) {
                int col = (int) ((gridStartX - x) / gridWidth);
                int row = (int) ((y - gridStartY) / gridHeight);
                String stelleName = row + "Feld" + col;

                Stelle dieStelle = spielZustand.getSpielStelle().get(stelleName);

                if (katzeTypeAusgewahlt >= 0 && rasenPlatz[row][col] == 0) {
                    String katzeName = katzenameList[katzeTypeAusgewahlt];

                    Katze eingesetzteKatze = spielZustand.einsetzenKazte(stelleName, katzeName);
                    if (eingesetzteKatze != null) {
                        rasenPlatz[row][col] = katzeTypeAusgewahlt + 1;

                        isDragging = false;
                        katzeTypeAusgewahlt = -1;

                    }

                }
            } else {
                isDragging = false;
                katzeTypeAusgewahlt = -1;
            }
        }

    }
    /**
     * Überprüft, ob ein heruntergefallenes Futter durch einen Mausklick
     * eingesammelt wurde.
     *
     * Wenn sich der Mausklick in der Nähe eines Futter-Drops befindet,
     * wird dieser entfernt und dem Spielzustand ein Futter hinzugefügt.
     *
     * @param event Mausereignis mit der Position des Mausklicks
     */
    private void futterDropEinsammeln(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        for (int i = 0; i < futterDrop.size(); i++) {
            FutterDrop drop = futterDrop.get(i);

            if (Math.abs(drop.x - mouseX) < 50
                    && Math.abs(drop.y - mouseY) < 50) {

                futterDrop.remove(i);
                spielZustand.futterHinzufuegen(1);
                break;
            }
        }
    }
    /**
     * Erstellt neue Angriffsanimationen aus den vorhandenen Angriffsevents.
     *
     * Für jedes Angriffsevent werden die Start- und Zielposition
     * übernommen und eine neue Angriffsanimation erstellt. Diese wird
     * anschließend zur Liste der aktiven Angriffsanimationen hinzugefügt.
     */
    private void neueAngriffeErstellen() {

        for (AngriffEvent event : spielZustand.getAngriffe()) {

            angriffe.add(
                    new AngriffAnimation(
                            event.getStartX(),
                            event.getStartY(),
                            event.getZielX(),
                            event.getZielY()
                    )
            );
        }
    }
    /**
     * Aktualisiert alle laufenden Angriffsanimationen.
     *
     * Jede aktive Angriffsanimation wird aktualisiert.
     * Sobald eine Animation abgeschlossen ist, wird sie aus
     * der Liste der laufenden Angriffsanimationen entfernt.
     */
    private void updateAngriffe() {

        Iterator<AngriffAnimation> iterator =
                angriffe.iterator();

        while (iterator.hasNext()) {

            AngriffAnimation angriff = iterator.next();

            angriff.update();

            if (angriff.istFertig()) {
                iterator.remove();
            }
        }
    }


    /**
     * Über dem Bildschirm wird automatisch Futter generiert;
     * durch Anklicken erhält der Spieler Futter.
     */
    class FutterDrop {
        double x;
        double y;
        double size = 50;

        FutterDrop(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Das Futter fällt mit einer vorgegebenen Geschwindigkeit herunter. Futter, das den Bildschirmrand überschreitet, wird entfernt.
     * Durch erfolgreiches Antippen innerhalb eines bestimmten Bereichs erhöht sich die Gesamtmenge an Futter.
     */
    private void updatefutter() {
        long jetzt = System.currentTimeMillis();
        if (jetzt - letztSpawnZeit > futterDropAbstand) {
            futterDrop.add(new FutterDrop(random.nextInt(WIDTH), 0));
            letztSpawnZeit = jetzt;
        }
        for (int i = 0; i < futterDrop.size(); i++) {
            FutterDrop drop = futterDrop.get(i);
            drop.y = drop.y + futterDropSpeed;
            if (drop.y > HEIGHT) {
                futterDrop.remove(i);
                i--;
            }
        }


    }


    /**
     * Katze ImageView erstellen
     */
    public ImagePattern erstellenNeuKatzeInstance(int katzeType) {
        ImagePattern neuKatzeImage = new ImagePattern(katzeImage[katzeType]);
        return neuKatzeImage;
    }

    /**
     * Rendering des Spielhintergrunds, Katzenkartenleiste, Spielfeld, Futter
     *
     * @param graphics
     */
    private void renderGame(GraphicsContext graphics) {
//        graphics.setFill(Color.WHITE);
//        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        //Katze Karte zeichen
        for (int i = 0; i < katzenameList.length; i++) {
            double x = figureStartX + i * gridWidth;
            double y = figureStartY;
            graphics.setFill(Color.WHITE);
            graphics.fillRect(x, y, gridWidth, gridHeight);
            ImagePattern katzeImagePattern = new ImagePattern(katzeImage[i]);
            graphics.setFill(katzeImagePattern);
            graphics.fillRect(x, y, gridWidth, gridHeight);

            graphics.setFill(Color.web("#FFD966"));
            graphics.setStroke(Color.web("#263238"));
            graphics.setLineWidth(2);

            graphics.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            graphics.fillText(
                    katzenameList[i],
                    x + 5,
                    y - 8
            );
            graphics.setFont(Font.font("Arial",FontWeight.BOLD, 12));
            graphics.fillText(
                    "Futter: " + katzeFutterKosten[i],
                    x + 5,
                    y +gridHeight+12
            );
        }

        //Ein Spielraster zeichnen und Katze Bild in der stelle zeichen
        ImagePattern spielplatz1 = new ImagePattern(SPIELPLATZ.getImage());
        ImagePattern spielplatz2 = new ImagePattern(SPIELPLATZ2.getImage());
        for (int i = 0; i < zeile; i++) {
            for (int j = 0; j < spalte; j++) {
                String stelleName = i + "Feld" + j;

                double cellx = gridStartX - (j + 1) * gridWidth;
                double celly = gridStartY + i * gridHeight;
                graphics.setFill((i + j) % 2 == 0 ? spielplatz1 : spielplatz2);

                graphics.fillRect(cellx, celly, gridWidth, gridHeight);
                Map<String, Stelle> spielstelle = spielZustand.getSpielStelle();
                Stelle stelle = spielstelle.get(stelleName);
                Katze katze = stelle.getKatze();

                if (katze == null) {
                    rasenPlatz[i][j] = 0;
                } else {
                    int type = rasenPlatz[i][j];


                    if (type != 0) {
                        ImagePattern katzeInDerStelle = new ImagePattern(katzeImage[type - 1]);
                        graphics.setFill(katzeInDerStelle);
                        graphics.fillOval(cellx + 5, celly + 5, gridWidth - 10, gridHeight - 10);


                    }
                }
            }

        }
        //Zeichne alle Bäume
        ImagePattern baum=new ImagePattern(BAUM.getImage());
        for (int i = 0; i < zeile; i++) {
            double cellx = 12;
            double celly = 200+gridHeight*i;
            graphics.setFill(baum);
            graphics.fillRect(cellx, celly, gridWidth, gridHeight);


        }
        if (katzeTypeAusgewahlt >= 0 && mouseMovedX <= gridStartX && mouseMovedX >= gridStartX - gridWidth * spalte &&
                mouseMovedY >= gridStartY && mouseMovedY <= gridStartY + gridHeight * zeile) {
            int s = (int) ((gridStartX - mouseMovedX) / gridWidth);
            int z = (int) ((mouseMovedY - gridStartY) / gridHeight);
            if (s < 0 || s >= spalte || z < 0 || z >= zeile) {
                return;
            }
            double previewX = gridStartX - gridWidth * (s + 1);
            double previewY = gridStartY + gridHeight * z;

            if (rasenPlatz[z][s] == 0) {
                //Sets the current stroke paint attribute.
                content.setStroke(Color.DARKGREEN);
                content.setLineWidth(3);
                content.strokeRect(previewX + 5, previewY + 5, gridWidth - 10, gridHeight - 10);
            } else {
                content.setStroke(Color.RED);
                content.setLineWidth(3);
                content.strokeRect(previewX + 5, previewY + 5, gridWidth - 10, gridHeight - 10);

            }

        }
        //Zeichne Futter
        ImagePattern futterBild = new ImagePattern(FUTTER.getImage());
        graphics.setFill(futterBild);
        for (FutterDrop drop : futterDrop) {
            graphics.fillOval(drop.x, drop.y, drop.size, drop.size);
        }

        graphics.setFill(Color.BLACK);
        graphics.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        graphics.fillText("Futter" + spielZustand.getFutter(), WIDTH - 100, 40);
        //
        for (AngriffAnimation angriff : angriffe) {
            ImagePattern ball = new ImagePattern(BALL.getImage());

            graphics.setFill(ball);

            graphics.fillOval(
                    angriff.getX() - 5,
                    angriff.getY() - 5,
                    10,
                    10
            );
        }
        List<Vogel> vogelZustand = spielZustand.getAktiveVogeln();
        for (int i = 0; i < vogelZustand.size(); i++) {
            Vogel vogel = vogelZustand.get(i);

            content.save();
            content.rotate(vogel.getRotation());
            ImagePattern bild = null;
            if (vogel.getType() == 1) {
                bild = new ImagePattern(VOGEL.getImage());
            }
            if (vogel.getType() == 2) {
                bild = new ImagePattern(SCHWAN.getImage());
            }
            if (vogel.getType() == 3) {

                bild = new ImagePattern(ADLER.getImage());
            }
            if (bild != null) {
                content.setFill(bild);
                content.fillOval(vogel.getX() + 10 * i, vogel.getY() + 5, gridWidth - 10, gridHeight - 10);
            }
            content.restore();
        }




    }
    /**
     * Lädt das angegebene Level und setzt den aktuellen Spielzustand.
     * @param level die Nummer des zu ladenden Levels
     */
    public void loadLevel(int level) {
        this.jetztLevel = level;
        if (level == 1) {
            this.spielZustand = spielLevel.level1();
        } else if (level == 2) {
            this.spielZustand = spielLevel.level2();
        } else if (level == 3) {
            this.spielZustand = spielLevel.level3();
        } else {
            System.out.println("Ungültiges Level: " + level);
            return;
        }

        gridStartX = spielZustand.getGridStartX();
        gridStartY = spielZustand.getGridStartY();

        gridWidth = spielZustand.getWidth();
        gridHeight = spielZustand.getHeight();

        zeile = spielZustand.getZeile();
        spalte = spielZustand.getSpalte();

        rasenPlatz = new int[zeile][spalte];

        katzenameList = spielZustand.getKatzename();
        katzeFutterKosten=spielZustand.getKatzeFutterKosten();
        katzeImage = spielZustand.getKatzeImage();

        isDragging = false;
        katzeTypeAusgewahlt = -1;

        futterDrop.clear();


        // =========================
        // Neues Level starten
        // =========================

        if (gameTimer != null) {
            gameTimer.start();
        }

        // Levelanzeige aktualisieren, damit nicht dauerhaft "Level 0" steht
        if (levelText != null) {
            levelText.setText("Level " + jetztLevel);
        }

        System.out.println("Level " + level + " geladen.");
    }

    public void pauseSpiel() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
    public void startSpiel() {
        spielBeendet=false;
        gameTimer.start();
    }
    /**
     * Wechselt nach erfolgreichem Abschluss eines Levels zum nächsten Level.
     *
     * <p>Wenn bereits das letzte Level abgeschlossen wurde, wird die
     * Game-Win-Szene angezeigt.</p>
     */
    private void gehenZuNaechsteLevel() {
        if (jetztLevel < 3) {
            jetztLevel++;
            spielBeendet=false;
            loadLevel(jetztLevel);
            gameTimer.start();
            System.out.println("Level " + jetztLevel);
        } else {
            System.out.println("Herzlichen Glückwunsch zum Durchspielen!");
            gameTimer.stop();
            SceneManager.changeScene("GameWin");
        }

    }
    @Override
    public Scene getScene() {
        return spielScene;
    }
}
