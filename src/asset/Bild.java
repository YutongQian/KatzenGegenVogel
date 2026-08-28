package asset;

import javafx.scene.image.Image;

import java.net.URL;

public enum Bild {
    SPIELPLATZ("Spielplatz.png"),
    SPIELPLATZ2("Spielplatz2.png"),
    FUTTER("Futter.png"),
    WERFERKATZE("WerferKatze.png"),
    LANGWERFERKATZE("LangWerferKatze.png"),
    KURZWERFERKATZE("KurzWerferKatze.png"),
    HARVESTERKATZE("HarvesterKatze.png"),
    HUNGERKATZE("HungerKatze.png"),
    FEUERKATZE("FeuerKatze.png"),
    QUEENKATZE("QueenKatze.png"),
    TANKKATZE("TankKatze.png"),
    WANDKATZE("WandKatze.png"),
    SCHWAN("Schwan.png"),
    VOGEL("Vogel.png"),
    ADLER("Adler.png"),
    BALL("Ball.png"),
    BAUM("Baum.png"),
    HINTERGRUND("Hintergrund.png");



    private static final String BASISPFAD="/asset/images/";
    private final Image image;
    Bild(String dateiname){

        URL url=getClass().getResource(BASISPFAD+dateiname);
        this.image=new Image(url.toExternalForm());

    }

    public Image getImage() {
        return image;
    }



}
