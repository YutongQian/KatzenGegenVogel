package de.katzengegenvogel.middletier.katze;

import de.katzengegenvogel.middletier.stelle.Stelle;
import de.katzengegenvogel.middletier.Tier;

/**
 * <pre>
 *     Der Spieler fügt der Kolonie eine Katze hinzu, indem er aus den oben auf dem Bildschirm angezeigten Katzenarten eine auswählt.
 *     Jede Katzenart führt eine andere Aktion aus und benötigt zum Einsetzen eine unterschiedliche Menge an Koloniefutter.
 *     Die beiden grundlegendsten Katzenarten sind die „Harvester-Katze“, die der Kolonie in jedem Zug eine Einheit Futter hinzufügt,
 *     und die „Werfer katzen“, die in jedem Zug einen Wollknäuel auf einen Vogel wirft.
 *
 * </pre>
 */
public abstract class Katze extends Tier {



    /**Kosten für die Anschaffung von Futter für eine neue Katze*/
    protected int futterKosten;
    /** Ist es ein Behälter?*/
    protected boolean istContainer=false;
    /** Wird der Schaden verdoppelt?*/
    protected boolean istDoppelte =false;
    /** Wenn katze eine Container ist,kann eine Katze speichern*/



    public Katze(){
        super(1);
        this.name="Katze";

        this.futterKosten=2;

    }


    /**
     * Kann es andere Katzen schützen?
     * @param andereKatze ziel Katze
     * @return true:
     */
    public boolean kannEnthalten(Katze andereKatze){
        return false;

    }

    public void speichernKatze(Katze katze){
        throw new UnsupportedOperationException();
    }

    public void removeKatze(){
        this.removeFrom(this.place);

    }

    /**
     * Wenn „Katze“ eine Container-Katze ist, dann ist die Katze an dieser Position ebenfalls eine Container-Katze.
     * Wenn die Katze im Container stirbt, gilt die Katze an dieser Position als geschützte Katze.
     * @param place Stelle, an der tier hinzugefügt werden soll
     */
    @Override
    public void addTo(Stelle place) {


        if(place.getKatze()==null){
            this.place=place;
            if(!(place.getTiere().contains(this))){
                place.setKatze(this);
            }



        }
        else if(this.istContainer
                && this.kannEnthalten(place.getKatze())){
            place.setKatze(this);

        }
         else if((place.getKatze().istContainer&&place.getKatze().kannEnthalten(this))
                ){
             this.place.getKatze().speichernKatze(this);



        }else{
                throw new UnsupportedOperationException();
        }

    }

    /**
     * Als Erstes wurde die „Container-Katze“ getroffen.
     * @param place Stelle, an der Tier entfernen werden soll
     */
    @Override
    public void removeFrom(Stelle place) {
        if(place.getKatze()==this){

            place.removeTiere(this);
        }else if(place.getKatze()!=null){
            place.removeTiere(place.getKatze());
            place.setKatze(this);
        }

    }

    /**
     * Fügt doppelten Schaden hinzu, kann nur einmal angewendet werden
     */
    public void doppelnSchaden(){
        if(!istDoppelte){
            this.schaden*=2;
            this.istDoppelte =true;
        }
    }

    public String getName() {
        return name;
    }

    public int getFutterKosten() {
        return futterKosten;
    }

    public void setFutterKosten(int futterKosten) {
        this.futterKosten = futterKosten;
    }
}
