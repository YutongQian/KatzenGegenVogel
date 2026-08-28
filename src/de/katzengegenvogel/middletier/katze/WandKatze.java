package de.katzengegenvogel.middletier.katze;

import de.katzengegenvogel.middletier.SpielZustand;

/**
 * Eine Katze, die in jeder Runde nichts tut.
 * Die „Wand Katze“ ist deshalb nützlich, weil sie über sehr viele Lebenspunkte verfügt.
 */
public class WandKatze extends Katze {
    public WandKatze() {
        super();
        this.name = "Wand Katze";
        this.lebenspunkte=4;
        this.futterKosten=4;
        this.schaden=0;

    }

    @Override
    public void action(SpielZustand spielZustand) {

    }





}
