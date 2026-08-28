package de.katzengegenvogel.middletier.vogel;

/**
 * es hat hoher Schaden
 */
public class Schwan extends Vogel {
    public Schwan() {
        this.schaden=0.02;
        this.name = "Schwan";
        this.lebenspunkte=2;
        this.type=2;

    }
    public Schwan(int lebenspunkte) {
        super(lebenspunkte);
        this.schaden=0.02;
        this.name="Schwan";
        this.type=2;
    }
}
