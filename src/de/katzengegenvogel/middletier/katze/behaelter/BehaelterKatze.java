package de.katzengegenvogel.middletier.katze.behaelter;

import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.stelle.Stelle;
import de.katzengegenvogel.middletier.katze.Katze;

/**
 * können sich einen Platz mit anderen Katzen teilen,
 * indem es sie in einem Bereich unterbringt.
 */
public class BehaelterKatze extends Katze {
    /**
     * Die geschützte Katze
     */
    private Katze behaltendKatze;
    /**Ist es ein Behalter? */
    protected boolean istBehalter;

    public BehaelterKatze() {
        super();
        this.name="Behaelter";
        this.istBehalter=true;

    }

    /**
     * Kann es die Zielkatze schützen?
     * @param andereKatze ziel Katze
     * @return
     */
    @Override
    public boolean kannEnthalten(Katze andereKatze) {
        if(this.behaltendKatze==null){
            if(!(andereKatze instanceof BehaelterKatze)){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Wenn es möglich ist, die ziel Katze zu schützen, dann soll sie geschützt werden.
     * @param katze ziel katze
     */
    @Override
    public void speichernKatze(Katze katze) {
        if (kannEnthalten(katze)) {

            this.behaltendKatze = katze;
            katze.setStelle(this.place);
        }


    }

    /**
     * Wenn eine Katze eine andere Katze beschützt, entferne zuerst die beschützte Katze.
     */
    @Override
    public void removeKatze() {
        if (this.behaltendKatze != null) {
            this.behaltendKatze.removeKatze();
            this.behaltendKatze = null;
        }

    }

    /**
     * Wenn es sich um eine geschützte Katze handelt, muss sie nach der Abholung bei der Schutzstelle abgemeldet werden;
     * handelt es sich nicht um eine geschützte Katze, ist dies der normale Ablauf.
     * @param place Stelle, an der Tier entfernen werden soll
     */
    @Override
    public void removeFrom(Stelle place) {
        if (place.getKatze()==this) {
            place.setKatze(this.behaltendKatze);
               
        }
        else{
            super.removeFrom(place);
        }
    }

    //TODO: Diese Methode stellt sicher, dass die Aktion von „katze_contained“ ausgeführt wird,
     // falls unser Behalter „Katze“ derzeit eine „Katze“ enthält.
    @Override
    public void action(SpielZustand spielZustand) {
        if (this.behaltendKatze != null) {
            behaltendKatze.action(spielZustand);

        }

    }

    public Katze getBehaltendKatze() {
        return behaltendKatze;
    }
}
