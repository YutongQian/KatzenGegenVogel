package de.katzengegenvogel.middletier.katze;

import de.katzengegenvogel.middletier.SpielZustand;

/**
 * <pre>
 *     „Harvester-Katze“, die der Kolonie eine Einheit Futter hinzufügt,
 * </pre>
 */
public class HarvesterKatze extends Katze {
    /**Die Runden, in denen Futter ausgegraben wird*/
    protected int abkuehlen;

    public HarvesterKatze() {
        super();
        this.name="Harvest Katze";
        this.lebenspunkte=2;
        this.futterKosten=2;
    }

    /**
     * Eine Harvester-Katze ist eine Katzenart,
     * die als Aktion einen Nahrungswert zum Gesamtwert „spielZustand.nahrung“ hinzufügt.
     * @param spielZustand
     */
    @Override
    public void action(SpielZustand spielZustand) {
        if(abkuehlen!=0) {
            abkuehlen--;

        }else{
            spielZustand.futterHinzufuegen(1);
            abkuehlen=300;
        }

    }





}
