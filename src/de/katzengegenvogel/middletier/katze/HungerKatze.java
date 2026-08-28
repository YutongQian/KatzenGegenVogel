package de.katzengegenvogel.middletier.katze;

import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.stelle.Stelle;
import de.katzengegenvogel.middletier.vogel.Vogel;

import java.util.Random;
/**
 * Repräsentiert eine hungrige Katze, die Vögel in ihrer Umgebung fressen kann.
 *
 * Die Katze sucht nach einem Vogel auf ihrem eigenen Feld oder
 * auf dem benachbarten Feld und kann diese Vögel in bestimmten
 * rund fressen.
 *

 */
public class HungerKatze extends Katze {
    /**
     * Anzahl der rund, die nach dem Fressen eines Vogels
     * bis zur nächsten Fressaktion gewartet werden muss.
     */
    protected int chewAbklingzeit=300;
    /**
     * speichert die Anzahl der rund, die ein Hunger Katze zum Kauen benötigt
     */
    protected int rundZuChew;
    /**
     * Ist die Katze gerade am Kauen?
     */
    protected boolean istChew;

    public HungerKatze() {
        super();
        this.name = "Hunger";
        this.futterKosten = 4;
        this.lebenspunkte = 4;
        /** da es zu Beginn noch nichts gefressen hat.*/
        this.rundZuChew=0;

    }

    /**
     * suchen ein Vogel in ihre Stelle oder neachste stelle
     *
     * @return ziel Vogel
     */
    public Vogel suchenGelegenenVogel() {
        Stelle current = this.place;

        if (!current.getVogeln().isEmpty()) {
            return current.getVogeln().get(new Random().nextInt(current.getVogeln().size()));
        } else {
            current = current.getEingang();
            if (!current.getVogeln().isEmpty()) {
                return current.getVogeln().get(new Random().nextInt(current.getVogeln().size()));
            }


        }


        return null;
    }

    /**
     * Wenn es gerade kaut, verringere die Abklingzeit.
     * Andernfalls fresse stattdessen einen zufälligen Vogel,
     * indem dessen Lebenspunkte auf 0 reduziert wird.
     * Lege die Abklingzeit fest, wenn ein Vogel gefressen wird!
     *
     * @param spielZustand
     */
    @Override
    public void action(SpielZustand spielZustand) {
        if (istChew) {
            rundZuChew--;

        } else {
            Vogel zielVogel = suchenGelegenenVogel();
            if (zielVogel != null) {
                zielVogel.setLebenspunkte(0);
                this.rundZuChew= chewAbklingzeit;
            }

        }

    }

}
