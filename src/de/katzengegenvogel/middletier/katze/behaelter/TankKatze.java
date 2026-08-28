package de.katzengegenvogel.middletier.katze.behaelter;

import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.stelle.Stelle;
import de.katzengegenvogel.middletier.vogel.Vogel;

import java.util.ArrayList;

/**
 * schützt andere Katzen und fügt Vögeln in ihrer Stelle einen Punkt Schaden zu.
 */
public class TankKatze extends BehaelterKatze{
    public TankKatze() {
        this.name = "Tank Katze";
        this.futterKosten=6;
        this.lebenspunkte=2;
    }

    /**
     * Fügt in jeder Runde allen Vögeln an dieser Position ein Schadenspunkt zu.
     * @param spielZustand
     */
    @Override
    public void action(SpielZustand spielZustand) {

        Stelle current=this.place;
        if(current!=null && !current.istVogelNest()){
            if(!current.getVogeln().isEmpty()){
                for (Vogel vogel : new ArrayList<> (current.getVogeln())) {
                    vogel.lebenspunkte-=1;

                }

            }
        }

    }
}
