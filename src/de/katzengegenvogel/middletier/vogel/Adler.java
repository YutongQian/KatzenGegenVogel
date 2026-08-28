package de.katzengegenvogel.middletier.vogel;

/**
 * Der Anführer der Vögel. Der Schaden, den der Boss durch beliebige Angriffe erleidet, ist begrenzt.
 * Es kann höchstens 0.4 Schadenspunkte verursachen.
 */
public class Adler extends Vogel {
    /** Maximaler Schaden */
    protected  double schadenKapazitaet;
    public Adler() {
        this.name="Adler";
        this.schaden=0.03;
        this.schadenKapazitaet=0.4;
        this.lebenspunkte=5;
        this.type=3;

    }
    public Adler(int lebenspunkte) {
        super(lebenspunkte);
        this.name="Adler";
        this.schaden=0.03;
        this.lebenspunkte=lebenspunkte;
        this.type=3;

        this.schadenKapazitaet=0.4;
    }

    /**
     * Wenn der verursachte Schaden höher ist als die Kapazität,
     * wird nur die Kapazität Lebensenergie verringert.
     * @param menge Anzahl, das Tier Lebenspunkte reduziert wird
     */
    @Override
    public void reduzierenlebenspunkte(double menge) {
        if(menge>schadenKapazitaet) {
            menge=schadenKapazitaet;

        }

        super.reduzierenlebenspunkte(menge);
    }
}
