package de.katzengegenvogel.middletier.katze.werfer;

/**
 * <pre>
 *     Der Angriffsbereich beginnt ab der fünften Position
 * </pre>
 */
public class LangWerferKatze extends WerferKatze {
    public LangWerferKatze() {
        this.name = "Lang Werfer";
        this.futterKosten=2;
        this.lebenspunkte=2;

        this.upperBound = 0;
        this.lowerBound = 5;

    }



    public LangWerferKatze(int lebenskosten) {
        super();
        this.name = "Lang Werfer";
        this.futterKosten=2;
        this.lebenspunkte=lebenskosten;

        this.upperBound = 0;
        this.lowerBound = 5;
    }


}
