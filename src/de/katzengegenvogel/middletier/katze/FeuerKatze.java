package de.katzengegenvogel.middletier.katze;

import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.vogel.Vogel;

import java.util.ArrayList;

/**
 * <pre>
 *     Die Klasse „FireAnt“ fügt ihrer Umgebung Schaden zu, wenn sie selbst Schaden erleidet.
 *     Genauer gesagt: Wenn sie eine bestimmte Anzahl an Lebenspunkten Schaden erleidet,
 *     fügt sie allen Vögeln an ihrem Standort ebenfalls diese Anzahl an Schaden zu
 *     (dies wird als „reflektierter Schaden“ bezeichnet).
 *     Wenn sie stirbt, verursacht sie zusätzlich eine bestimmte Anzahl an Schaden,
 *     deren Wert durch ihre Eigenschaft „damage“ bestimmt wird, deren Standardwert in der Klasse „Feuerkatze“ 3 beträgt.
 * </pre>
 */
public class FeuerKatze extends Katze {
    public FeuerKatze() {
        this.name = "Feuer";
        this.lebenspunkte=3;

        this.futterKosten = 5;
        this.schaden = 3;
    }

    /**
     * Verringert die Lebenspunkte um menge Punkte.
     * Wenn die Feuerkatze keine Lebenspunkte mehr hat, wird sie von ihrer aktuellen Position entfernt.
     * Verringert die Lebenspunkte jedes Vogels an der aktuellen Position und verursacht zusätzlichen Schaden, wenn die Feuerameise stirbt.
     * @param menge Anzahl, das Tier Lebenspunkte reduziert wird
     */
    @Override
    public void reduzierenlebenspunkte(double menge) {
        boolean stirbt=this.lebenspunkte<=menge;
        if(this.place!=null){
            double vogelschade=stirbt ? menge + this.lebenspunkte : menge;
            for (Vogel vogel:new ArrayList<Vogel>(this.place.getVogeln()) ) {
                vogel.reduzierenlebenspunkte(vogelschade);

            }
        }
        super.reduzierenlebenspunkte(menge);
    }

    @Override
    public void action(SpielZustand spielZustand) {

    }


}
