package de.katzengegenvogel.middletier.katze.werfer;

/**
 * <pre>
 *     Ein „Kurzwerfer Katzen“ darf nur auf Vögel werfen,
 *     die er nach höchstens drei Eintrittswechseln gefunden hat.
 *     Er kann nicht auf Vögel werfen, die mehr als drei Positionen vor ihm liegen.
 * </pre>
 */
public class KurzWerferKatze extends WerferKatze {


    public KurzWerferKatze() {
        super();
        this.name = "Kurz Werfer";
        this.futterKosten = 2;
        this.upperBound = 4;
        this.lowerBound = 0;
    }


}
