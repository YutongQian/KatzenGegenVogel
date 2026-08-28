package de.katzengegenvogel.middletier;

import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.stelle.VogelNest;
import de.katzengegenvogel.middletier.vogel.Adler;
import de.katzengegenvogel.middletier.vogel.Schwan;
import de.katzengegenvogel.middletier.vogel.Vogel;
import de.katzengegenvogel.middletier.vogel.VogelPlan;
import javafx.scene.image.Image;

import java.util.Map;

import static asset.Bild.*;
/**
 * <pre>
 *  Erstellt und konfiguriert die verschiedenen Spiellevel.
 *
 *  Die Klasse legt für jedes Level die Spielfeldgröße,
 *  die verfügbare Futtermenge und die auftretenden Vogelwellen
 *  fest. Anschließend wird daraus ein neuer Spielzustand erstellt
 *  und mit den verfügbaren Katzeninformationen verbunden.
 * </pre>
 */
public class SpielLevel {


    private int zeile=5;
    private int originalFutter;
    private VogelPlan plan;
    private int spalte=10;
    private SpielZustand spielZustand;
    private String []katzename={"WerferKatze","HarvesterKatze", "KurzWerferKatze","LangWerferKatze","HungerKatze",
            "WandKatze","FeuerKatze","QueenKatze"};
    private int [] katzeFutterkosten ={3,2,2,2,4,4,5,7};
    private Image[] katzeImage= new Image[]{WERFERKATZE.getImage(),HARVESTERKATZE.getImage(),KURZWERFERKATZE.getImage(),
            LANGWERFERKATZE.getImage(),HUNGERKATZE.getImage(),WANDKATZE.getImage(), FEUERKATZE.getImage(),QUEENKATZE.getImage()};
    private Map<String,Class <? extends Katze>> katzeType=KatzeUndVogelRegistery.getAlleKatzeTypen();



    public SpielLevel() {

    }

    /**
     * Erstellt den Spielzustand für das erste Level.
     *
     * Für das erste Level werden die Spielfeldgröße, die anfängliche
     * Futtermenge und die verschiedenen Vogelwellen festgelegt.
     * Anschließend wird ein neuer Spielzustand mit diesen Einstellungen
     * erstellt und zurückgegeben.
     *
     * @return der konfigurierte Spielzustand für Level 1
     */
    public SpielZustand level1(){

        originalFutter=30;
        zeile=5;
        spalte=10;
        this.plan=new VogelPlan();
        plan.addWelle(Vogel.class,10);
        plan.addWelle(Schwan.class,2);

        plan.addWelle(Adler.class,1);
        VogelNest neuNest=new VogelNest(plan);
        spielZustand=new SpielZustand(neuNest,katzeType,zeile,originalFutter);
        spielZustand.setKatzeImage(katzeImage);
        spielZustand.setKatzename(katzename);
        spielZustand.setKatzeFutterKosten(katzeFutterkosten);
        return spielZustand;

    }
    /**
     * Erstellt den Spielzustand für das zweite Level.
     * Für das erste Level werden die Spielfeldgröße, die anfängliche
     * Futtermenge und die verschiedenen Vogelwellen festgelegt.
     * Anschließend wird ein neuer Spielzustand mit diesen Einstellungen
     * erstellt und zurückgegeben.
     *
     * @return der konfigurierte Spielzustand für Level 1
     */
    public SpielZustand level2(){

        originalFutter=30;
        zeile=5;
        spalte=10;


        this.plan=new VogelPlan();

        plan.addWelle(Vogel.class,30);
        plan.addWelle(Schwan.class,7);

        plan.addWelle(Adler.class,6);
        VogelNest neuNest=new VogelNest(plan);
        spielZustand=new SpielZustand(neuNest,katzeType,zeile,originalFutter);
        spielZustand.setKatzeImage(katzeImage);
        spielZustand.setKatzename(katzename);
        spielZustand.setKatzeFutterKosten(katzeFutterkosten);
        return spielZustand;

    }
    /**
     * Erstellt den Spielzustand für das dritte Level.
     *
     * Für das erste Level werden die Spielfeldgröße, die anfängliche
     * Futtermenge und die verschiedenen Vogelwellen festgelegt.
     * Anschließend wird ein neuer Spielzustand mit diesen Einstellungen
     * erstellt und zurückgegeben.
     *
     * @return der konfigurierte Spielzustand für Level 1
     */
    public SpielZustand level3(){

        originalFutter=40;
        zeile=5;
        spalte=10;
        this.plan=new VogelPlan();
        plan.addWelle(Vogel.class,40);
        plan.addWelle(Schwan.class,10);
        plan.addWelle(Adler.class,8);
        VogelNest neuNest=new VogelNest(plan);
        spielZustand=new SpielZustand(neuNest,katzeType,zeile,originalFutter);
        spielZustand.setKatzeImage(katzeImage);
        spielZustand.setKatzename(katzename);
        spielZustand.setKatzeFutterKosten(katzeFutterkosten);
        return spielZustand;

    }





    public int getZeile() {
        return zeile;
    }

    public int getOriginalFutter() {
        return originalFutter;
    }

    public VogelPlan getPlan() {
        return plan;
    }

    public void setSpalte(int spalte) {
        this.spalte = spalte;
    }

    public SpielZustand getSpielZustand() {
        return spielZustand;
    }





    public int getSpalte() {
        return spalte;
    }
}
