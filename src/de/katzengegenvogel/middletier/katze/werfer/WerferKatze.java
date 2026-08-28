package de.katzengegenvogel.middletier.katze.werfer;

import de.katzengegenvogel.middletier.AngriffEvent;
import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.stelle.Stelle;
import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.vogel.Vogel;

import java.util.Random;

/**
 * <pre>
 *
 * </pre>
 */
public class WerferKatze extends Katze {

    protected int upperBound = 0;
    protected int lowerBound = 0;
//    protected boolean istAngriff;
    protected int angriffAbkuelen;


    public WerferKatze() {
        this.name = "Werfer";
        this.lebenspunkte = 1;

        this.schaden = 0.01;
        this.futterKosten = 3;
    }

    /**
     * Suche nach der Stelle im Schrittbereich
     * @param schitte Schrittbereiche
     * @return Ziel Stelle
     */
    protected Stelle getBoundPlace(int schitte) {
        Stelle naechsteStelle = this.place;
    for (int i = 0; i < lowerBound; i++) {
        if (naechsteStelle.istVogelNest() || naechsteStelle.getEingang() == null) {
            break;
        }
        naechsteStelle = naechsteStelle.getEingang();
    }
    return naechsteStelle;
}

    /**
     * Der stelle, an dem die Minimalwerte liegt
     * @param lowerBound Minimalwert
     * @return ziel stelle
     */
    protected Stelle getLowerBoundPlace(int lowerBound) {

        if(lowerBound==0
        ){
            return this.place;
        }else{
            Stelle lowerBoundPlace = getBoundPlace(lowerBound);
            return lowerBoundPlace;
        }

    }

    /**
     * Suche nach der größten Angriffsreichweite
     * @param upperBound maximal werte
     * @return ziel stelle
     */
    protected Stelle getUpperBoundPlace(int upperBound) {
        Stelle naechsteStelle = this.place;

        if (upperBound == 0) {

            while (true) {
                if (naechsteStelle.istVogelNest()) {
                    return naechsteStelle;
                }
                naechsteStelle = naechsteStelle.getEingang();

            }

        } else {
            Stelle upperBoundPlace = getBoundPlace(upperBound);
            return upperBoundPlace;
        }



    }

    /**
     * Wirf einen Fellball auf den Ziel-„Vogel“, um dessen Lebenspunkte zu verringern
     *
     * @param ziel ziel Vogel
     */

    public void werfenNach(Vogel ziel) {
        if (ziel != null) {
            ziel.reduzierenlebenspunkte(this.schaden);
        }
    }

    /**
     * Wirf einen Ball auf den nächstgelegenen Vogel in Reichweite.
     *
     * @param spielZustand
     */
    @Override
    public void action(SpielZustand spielZustand) {
        if(angriffAbkuelen==0){
            Vogel zielVogel = suchenNaechstgelegenenVogel(spielZustand);
            if (zielVogel != null) {
                werfenNach(zielVogel);
                spielZustand.addAngriff(new AngriffEvent(
                        this.place.getX()+(this.place.getWidth()/2),
                        this.place.getY()+(this.place.getHeight()/2),
                        zielVogel.getX(),
                        zielVogel.getY()
                ));
            }


            angriffAbkuelen=60;

        }
        else{
            angriffAbkuelen--;
        }

    }

    /**
     * erlaubt es ihm jedoch nur, Vogeln zu treffen, die sich an derselben Position befinden.
     * Diese Implementierung ermöglicht es dem „Werfer katze“, das Wollknäuel auf der nächstgelegener Vogel direkt vor ihm zu werfen,
     * die sich noch nicht im Vogelnest befindet.
     * Dazu gehören auch Vogel, die sich an derselben Position wie der „Werfer katzen“ befinden.
     *
     * @return ziel Vogel
     */

    public Vogel suchenNaechstgelegenenVogel(SpielZustand spielZustand) {

        Stelle lower = getLowerBoundPlace(lowerBound);
        Stelle upper = getUpperBoundPlace(upperBound);

        Stelle naechsteStelle = lower;
        if (naechsteStelle != null && !naechsteStelle.istVogelNest()) {
            while (naechsteStelle != upper) {
                if (!naechsteStelle.getVogeln().isEmpty()) {
                    return naechsteStelle.getVogeln().get(new Random().nextInt(naechsteStelle.getVogeln().size()));
                }
                naechsteStelle = naechsteStelle.getEingang();
            }
        }
        return null;
    }


}
