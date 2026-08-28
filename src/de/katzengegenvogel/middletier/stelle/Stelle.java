package de.katzengegenvogel.middletier.stelle;

import de.katzengegenvogel.middletier.Tier;
import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.vogel.Vogel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     Eine Stelle ist mit einem anderen Ort verbunden, sodass ein Tunnel entsteht.
 *     Der Spieler kann an jeden Stelle eine einzelne Katze setzen.
 *     An einem einzelnen Stelle können sich jedoch mehrere Vogeln befinden.
 * </pre>
 */
public class Stelle {
    /**
     * Name diese Stelel mit String
     */
    protected String name;
    /**
     * ob diese Stelle VogelNest ist
     */
    protected boolean istVogelNest = false;
    /**
     * eine List von allen Tiere
     */
    protected List<Tier> tiere = new ArrayList<>();
    /**
     * links Stelle von dieser stelle
     */
    protected Stelle ausgang;
    /**
     * rechte Stelle von dieser Stelle
     */
    protected Stelle eingang;

    /**Diese Position befindet sich auf der x-Achse */
    protected double x;
    /**Diese Position befindet sich auf der y-Achse */
    protected double y;
    /**Die Breite dieser Stelle*/
    protected double width = 80;
    /**Die Höhe dieser Stelle*/
    protected double height = 60;
    /**Die Zeile, in der sich diese Stelle befindet*/
    protected int zeile;
    /** Die Spalte, in der sich diese Stelle befindet*/
    protected int spalte;
    /**
     * Eine Stelle kann eine Katze behalten
     */
    protected Katze katze = null;

    public Stelle(String name) {
        this.name = name;

    }

    /**
     * Ein neu erstellter Stelle beginnt immer damit, dass sein Eingang auf „Keine“ gesetzt ist.
     * Verfügt der Ort über einen Ausgang, wird der Eingang des Ausgangs auf diesen Ort gesetzt.
     *
     * @param name name diese Stelle
     * @param exit Ausgang diese stelle
     */
    public Stelle(String name, Stelle exit) {
        this.name = name;
        this.ausgang = exit;
        if (exit != null) {
            exit.eingang = this;
        }
    }


    /**
     * Das Tier zur Tierliste hinzufügen
     *
     * @param tier ziel tier
     */
    public void addTiere(Tier tier) {
        if (!tier.istTot()) {
            if (tier.getPlace() != this) {
                tier.addTo(this);
            }

            if (!tiere.contains(tier)) {

                tiere.add(tier);
            }

        }
    }

    /**
     * Dieses Tier aus der Tiere liste löschen
     *
     * @param tier ziel objekt
     */
    public void removeTiere(Tier tier) {

        boolean removed = tiere.remove(tier);

        if (tier instanceof Katze && this.katze == tier) {
            this.katze = null;
        }

        if (tier.getPlace() == this) {
            tier.removeFrom(this);


        }


    }

    public Stelle getEingang() {
        return eingang;
    }

    public Stelle getAusgang() {
        return ausgang;
    }

    public void setEingang(Stelle eingang) {
        this.eingang = eingang;
    }

    public String getName() {
        return name;
    }

    public Katze getKatze() {
        if (katze != null) {
            if (katze.istTot()) {
                removeTiere(katze);
            }

        }

        return katze;
    }

    public void setKatze(Katze katze) {

        this.katze = katze;

    }

    public List<Vogel> getVogeln() {
        List<Vogel> alleVogeln = new ArrayList<>();

        for (Tier tier : tiere) {
            if (tier instanceof Vogel&&(!tier.istTot())) {
                alleVogeln.add((Vogel) tier);
            }
            else if (tier.istTot()) {
                removeTiere(tier);
            }
        }
        return alleVogeln;
    }


    public void setAusgang(Stelle ausgang) {
        this.ausgang = ausgang;
    }

    public boolean istVogelNest() {
        return istVogelNest;
    }

    public List<Tier> getTiere() {
        return Collections.unmodifiableList(tiere);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;


    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getZeile() {
        return zeile;
    }

    public void setZeile(int zeile) {
        this.zeile = zeile;
    }

    public int getSpalte() {
        return spalte;
    }

    public void setSpalte(int spalte) {
        this.spalte = spalte;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public boolean isIstVogelNest() {
        return istVogelNest;
    }
}
