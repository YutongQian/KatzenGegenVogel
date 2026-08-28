package de.katzengegenvogel.middletier.vogel;

import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.Tier;
import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.stelle.Stelle;

/**
 * <pre>
 *     In jeder Runde bewegt sich ein Vogel entweder zum nächsten Platz im Tunnel weiter,
 *     sofern ihm keine Katze im Weg steht, oder er sticht die Katze, die ihm im Weg steht.
 *     Die Vögel gewinnen, wenn mindestens ein Vogel das Ende eines Tunnels erreicht.
 *     Neben den Vögeln gibt es Schwan, die doppelten Schaden verursachen,
 *     sowie einen Adler, der ziemlich schwer zu besiegen ist.
 * </pre>
 */
public class Vogel extends Tier {
    /**
     * name der Vogel
     */
    protected String name;
    protected double x;
    protected double y;
    /**Vorwärtsgeschwindigkeit*/
    protected double geschwindigkeit;
    protected int zeile;
    protected int type;
    /**Befindet sich das System im Angriffszustand?*/
    protected boolean istAngriff;
    protected int angriffAbkuelen;
    protected double rotation=0;
    protected double animationZeit=0;
    public void anmationUpdate(){
        animationZeit+=0.08;
    }



    public Vogel() {
        this.schaden = 0.01;
        this.lebenspunkte = 1;
        this.type = 1;
        if (type == 1) {
            this.geschwindigkeit = 0.1;
        } else if (type == 2) {
            this.geschwindigkeit = 0.2;
        } else if (type == 3) {
            this.geschwindigkeit = 0.3;
        }

        this.name = "Vogel";


    }

    public Vogel(int lebenspunkte) {
        this.schaden = 0.01;
        this.lebenspunkte = lebenspunkte;
        this.name = "Vogel";

        this.istAngriff = true;
        this.type = 1;
        if (type == 1) {
            this.geschwindigkeit = 0.001;
        } else if (type == 2) {
            this.geschwindigkeit = 0.005;
        } else if (type == 3) {
            this.geschwindigkeit = 0.007;
        }
    }
    /**
     * Berechnet den aktuellen Rotationswinkel für die Animation.
     * Der Rotationswert wird mithilfe der Sinusfunktion aus der
     * aktuellen Animationszeit berechnet.
     *
     * @return der aktuelle Rotationswinkel
     */
    public double getRotation(){
        return Math.sin(animationZeit);
    }


    /**
     * Greife eine Katze an und verringere ihre Lebenspunkte um ihre Schade.
     *
     * @param katze ziel Katze
     */
    public void verletzen(Katze katze) {
        katze.reduzierenlebenspunkte(this.schaden);
        istAngriff = true;


    }

    /**
     * In jeder Runde fliegt der Vogel auf den Baum zu;
     * wenn er auf eine Katze trifft, hält er an, bis die Katze stirbt,
     *
     * @param spielZustand
     */
    @Override
    public void action(SpielZustand spielZustand) {
        anmationUpdate();

        if (this.lebenspunkte < 0) return;
        Katze katze = this.place.getKatze();
        if (katze == null || katze.istTot()) {
            istAngriff = false;
            gehenZu(this.place.getAusgang());
            return;
        }
        istAngriff = true;

        if (angriffAbkuelen > 0) {
            angriffAbkuelen--;
            return;
        }


        verletzen(katze);
        angriffAbkuelen = 60;




    }

    /**
     * Der Vogel fliegt auf Ziel zu
     *
     * @param ziel ziel stelle
     */
    public void gehenZu(Stelle ziel) {


        if (!istAngriff) {
            if (this.place != null) {
                this.x = this.x - this.geschwindigkeit;
                this.y = ziel.getY() - 10;
            }
            if (!(ziel.getVogeln().contains(this))) {
                if (this.x <= ziel.getX() + ziel.getWidth()) {
                    if (this.place != null) {
                        removeFrom(this.place);
                    }
                    this.place = ziel;
                    this.addTo(ziel);
                    this.zeile = ziel.getZeile();
                }
            }
        }

    }

    /**
     * add diese Vogel zu stelle
     *
     * @param place Stelle, an der tier hinzugefügt werden soll
     */
    @Override
    public void addTo(Stelle place) {
        if (!(place.getVogeln().contains(this))) {
            place.addTiere(this);
        }

        super.addTo(place);

    }

    /**
     * remove diese Vogel von die Stelle
     *
     * @param place Stelle, an der Tier entfernen werden soll
     */
    @Override
    public void removeFrom(Stelle place) {
        if (place.getVogeln().contains(this)) {

            place.removeTiere(this);
        }

        super.removeFrom(place);

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getZeile() {
        return zeile;
    }

    public double getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public int getType() {
        return type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZeile(int zeile) {
        this.zeile = zeile;
    }

    public void setAngriff(boolean angriff) {
        this.istAngriff = angriff;
    }


    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
