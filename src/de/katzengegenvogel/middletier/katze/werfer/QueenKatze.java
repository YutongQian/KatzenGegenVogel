package de.katzengegenvogel.middletier.katze.werfer;

import de.katzengegenvogel.middletier.KatzenVerlorenException;
import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.stelle.Stelle;

/**
 * Die Königin der Katzen erhöht den Schaden aller Katzen hinter ihr.
 */
public class QueenKatze extends WerferKatze{
    public QueenKatze() {
        this.name="Queen";
        this.lowerBound=0;
        this.upperBound=0;
        this.futterKosten=7;
        this.lebenspunkte=2;

    }

    /**
     * Jedes Mal, wenn eine Queen katze eine Aktion ausführt,
     * verdoppelt sie den Schaden aller Katzen hinter ihr.
     * @param spielZustand
     */
    @Override
    public void action(SpielZustand spielZustand) {

        Stelle current=this.place;
        while(current!=null&&!(current.istVogelNest())) {
            if(current.getKatze()!=null) {
                current.getKatze().doppelnSchaden();

            }
            current = current.getEingang();
        }
        super.action(spielZustand);
    }

    /**
     * Sollte die Lebenspunkte einer Queen jemals auf 0 sinken, haben die Katzen verloren.
     * @param menge Anzahl die Lebenspunkte reduziert wird
     */
    @Override
    public void reduzierenlebenspunkte(double menge) {
        super.reduzierenlebenspunkte(menge);
        if(this.lebenspunkte<=0) {
            throw new KatzenVerlorenException("Game Over");

        }
    }

    public String getName() {
        return name;
    }
}
