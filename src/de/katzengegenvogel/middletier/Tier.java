package de.katzengegenvogel.middletier;

import de.katzengegenvogel.middletier.stelle.Stelle;
/**
 * Abstrakte Basisklasse für alle Tiere im Spiel.
 *
 * <pre>
 *     Die Klasse enthält die gemeinsamen Eigenschaften und
 *     Verhaltensweisen, die von den verschiedenen Tierarten
 *     verwendet werden. Konkrete Tierarten können von dieser
 *     Klasse erben und ihr eigenes Verhalten implementieren.
 * </pre>
 *
 */
public abstract class Tier {
    protected static int nextId=0;
    /**Jedes Tier erhält eine eindeutige Identifikationsnummer*/
    protected  int id;
    /** Gesundheitswert; 0 bedeutet Tod*/
    public double lebenspunkte;
    /**Der Standort der Tiere */
    protected  Stelle place;
    /**Der durch den Angriff verursachte Schaden verringert die Lebenspunkte des Gegners.*/
    protected String name;
    protected  double schaden;
    protected boolean istTot;
    protected double x;
    protected double y;


    protected  boolean istWasserDicht=false;
    public Tier(){}
    public Tier(int lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
        this.id=nextId++;
    }


    /**
     * Verringere die Lebenspunkte um „menge“ und entferne das Tier von seinem Platz,
     * wenn es keine Lebenspunkte mehr hat.
     * @param menge Anzahl, das Tier Lebenspunkte reduziert wird
     */
    public void reduzierenlebenspunkte(double menge) {

        this.lebenspunkte = lebenspunkte - menge;

        if (this.lebenspunkte <=0) {
            nullLebenspunkteRueckrufe();

        }

    }

    /**
     * Die Angriffe in jeder Runde
     * @param spielZustand
     */
    public abstract void action(SpielZustand spielZustand);

    /**
     * Füge die Stelle von diesem Tier zu „place“ hinzu
     * @param place Stelle, an der tier hinzugefügt werden soll
     */
    public void addTo(Stelle place){
        this.place=place;
        place.addTiere(this);
    }

    /**
     *Die Stelle des Tieres entfernen
     * @param place Stelle, an der Tier entfernen werden soll
     */
    public  void removeFrom(Stelle place){
        if(this.place==place){
            this.place=null;
            place.removeTiere(this);

        }

    }

    /**
     * Wenn die Lebenspunkte auf null sinken, wird diese Methode aufgerufen,
     * damit die Stelle dieses Tier entfernt.
     */
    public void nullLebenspunkteRueckrufe(){
        if (this.place != null&&this.place.getTiere().contains(this)) {
            this.place.removeTiere(this);
        }
    }


    public double getLebenspunkte() {
        return lebenspunkte;
    }

    public void setLebenspunkte(double lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
        if (this.lebenspunkte <=0) {
            nullLebenspunkteRueckrufe();
        }
    }

    public void setStelle(Stelle place) {
        this.place = place;
        this.x=place.getX();
        this.y=place.getY();
    }

    public Stelle getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean istTot() {
        return this.lebenspunkte<=0;
    }

}
