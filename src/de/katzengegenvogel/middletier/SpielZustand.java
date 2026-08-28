package de.katzengegenvogel.middletier;

import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.stelle.BaumStelle;
import de.katzengegenvogel.middletier.stelle.Stelle;
import de.katzengegenvogel.middletier.stelle.VogelNest;
import de.katzengegenvogel.middletier.vogel.Vogel;
import javafx.scene.image.Image;

import java.util.*;

/**
 * Spielidee
 * Der Spieler muss einen großen Baum 🌳 vor angreifenden Vögeln 🐦 schützen.
 * Die Vögel bewegen sich auf den Baum zu. Der Spieler platziert Katzen 🐱, die die Vögel angreifen.
 * Ein „Katze“-Kollektiv, das den globalen Spielzustand verwaltet und die Zeit simuliert.
 */

public class SpielZustand {
    /**
     * vergangene Zeit
     */
    private int bildWiederholrate =0;

    private int mindestSpielRund;


    private boolean gameRunging=true;
    private boolean istPause=false;
    /**
     * Gesamtmenge an verfügbaren Futtermitteln
     */
    private int futter;
    /**
     * Eine Liste aller Stelle in der Kolonie incl Vogel Nest
     */
    private Map<String, Stelle> spielStelle = new LinkedHashMap<>();
    /***/
    private List<Vogel> aktiveVogeln;
    /** ein map von Katzenklassen */
    private Map<String, Class<? extends Katze>> katzenTypen;
    /**
     * Vogel Nest mit viele Vogeln, wo die Vogeln anfang
     */
    private VogelNest vogelNest;

    /**
     * ein Paar, das die Abmessungen des Spielfelds enthält
     */
    private int zeile;
    private int spalte;
    private int width=88;
    private int height=75;
    private double gridStartX=980;
    private double gridStartY=200;
    /**
     * eine Liste von Stellen, an denen Vögel eindringen können
     */
    private List<Stelle> vogelEingang;

    private String []katzename;
    private int []katzeFutterKosten;
    private Image[] katzeImage;
    private List<AngriffEvent> angriffe=new ArrayList<>();

    /** konstruktur */
    public SpielZustand(VogelNest vogelNest, Map<String, Class<? extends Katze>> katzenType, int dimensions, int originalNahrung) {
        this.bildWiederholrate = 0;
        this.vogelNest = vogelNest;
        this.futter = originalNahrung;
        this.katzenTypen = katzenType;
        this.mindestSpielRund=vogelNest.getMindetenSpielrund();
//        for (Class<? extends Katze> katze : katzenType.values()) {
//            try {
//                Katze instance = katze.getDeclaredConstructor().newInstance();
//                this.katzenTypen.put(instance.getName(), katze);
//
//            } catch (Exception ausnahme) {
//                throw new RuntimeException(ausnahme);
//            }
//        }
        this.spalte=10;
        this.zeile = dimensions;

        konfigurieren();
    }

    /**
     * Konfigurieren die Stelle in der Kolonie.
     */
    private void konfigurieren() {

        this.spielStelle = new LinkedHashMap<>();
        this.vogelEingang = new ArrayList<>();
        registierenPlatz(this.vogelNest, false);
        erstellenStelle();


    }

    /**
     * Erstellt die Stellen des Spielfelds.
     *
     * <p>Das Spielfeld besteht aus mehreren Zeilen. Jede Zeile beginnt
     * am Vogelnest und führt über mehrere Felder zu einer Baumstelle.
     * Die Anzahl der Zeilen wird durch {@code dimensions} bestimmt,
     * während {@code anzahlFelder} die Anzahl der Felder pro Zeile angibt.</p>
     *
     * <p>Die erste Stelle jeder Zeile wird als Eingang für Vögel
     * registriert. Zwischen den Stellen werden die Beziehungen
     * {@code Eingang} und {@code Ausgang} gesetzt.</p>
     *
     * <pre>
     * VogelNest
     *     ├── 0Feld0 (Vogel Eingang) ── 0Feld1 ── 0Feld2 ──... 0Feld9 ──Baum
     *     ├── 1Feld0 (Vogel Eingang) ── 1Feld1 ── 1Feld2 ── ...1Feld9 ──Baum
     *     ├── 2Feld0 (Vogel Eingang) ── 2Feld1 ── 2Feld2 ── ...2Feld9 ──Baum
     *     ...                                                         ──Baum
     *     └──nFeld0 (Vogel Eingang)    ...       ...        ...       ──Baum
     * </pre>
     *
     *
     */

    public void erstellenStelle() {

        int zeile= this.zeile;
        int spalte = this.spalte;
        for (int row = 0; row < zeile; row++) {
            Stelle vorher = vogelNest;
            for (int column = 0; column < spalte; column++) {


                Stelle feld = new Stelle(row + "Feld" + column);
                feld.setZeile(row);
                feld.setSpalte(column);
                feld.setX(gridStartX-width*(column+1));
                feld.setY(gridStartY+height*row);

                feld.setEingang(vorher);
                vorher.setAusgang(feld);
                registierenPlatz(feld, column == 0);
                vorher = feld;

            }
            BaumStelle baum = new BaumStelle(("Baum" + row));
            vorher.setAusgang(baum);
            baum.setEingang(vorher);
            registierenPlatz(baum, false);


        }


    }

    /** Registriert eine Stelle im Spiel und fügt sie bei Bedarf zu den
    * Eingängen des Vogelnests hinzu.
    *
    * <p>Jede Stelle wird zunächst in {@code spielStelle} gespeichert,
    * damit sie über ihren Namen gefunden werden kann. Wenn die Stelle
    * als Vogel-Eingang gekennzeichnet ist, wird das Vogelnest als ihr
    * Eingang gesetzt und die Stelle zur Liste der Vogel-Eingänge hinzugefügt.</p>
    *
    * @param stelle die zu registrierende Stelle
    * @param istvogelEingang gibt an, ob die Stelle ein Eingang für Vögel ist
    */
    private void registierenPlatz(Stelle stelle, boolean istvogelEingang) {
        spielStelle.put(stelle.getName(), stelle);
        if (istvogelEingang) {
            stelle.setEingang(vogelNest);
            vogelEingang.add(stelle);
        }
    }

    /**
     * Fordere jeder Katze auf, Aktion ausführen
     */
    public void katzenAktionAusfuehren() {
        for (Katze katze : getkatze()) {
            if (katze.lebenspunkte > 0) {
                katze.action(this);
            }
        }
    }

    /**
     * Foder jeder Vogel, Aktion ausführen
     *
     * @param anzahlVogelen
     * @return
     */
    public int vogelnActionAusfuehren(int anzahlVogelen) {
        Iterator<Vogel> iterator = aktiveVogeln.iterator();

        while (iterator.hasNext()) {
            Vogel vogel = iterator.next();

            if (vogel.getLebenspunkte() > 0) {
                vogel.action(this);
            }

            if (vogel.getLebenspunkte() <= 0) {
                iterator.remove();
            }
        }
        if (aktiveVogeln.isEmpty()&&mindestSpielRund<bildWiederholrate) {
            katzeGewinnwn();
        }
        return anzahlVogelen;
    }

    /**
     * Stellen eine Katze hin, wenn genügend Futter vorhanden ist.
     *
     * @param stelleName die Stelle Name
     * @param katzeTypeName der Name von Class der Katze
     * @return die Katze, die eingesetzt wird
     */
    public Katze einsetzenKazte(String stelleName, String katzeTypeName) {

        Class<? extends Katze> katzeName = katzenTypen.get(katzeTypeName);

        try {
            Katze katze = katzeName.getDeclaredConstructor().newInstance();
            if (katze.getFutterKosten() > futter) {

                return null;
            }
            Stelle stelle = spielStelle.get(stelleName);



            stelle.addTiere(katze);
            futter = futter - katze.getFutterKosten();
            return katze;
        } catch (Exception ausnahme) {
            throw new RuntimeException(ausnahme);
        }

    }

    public void removeKatze(String stelleName) {
        Stelle stelle = spielStelle.get(stelleName);
        if (stelle.getKatze() != null) {
            stelle.removeTiere(stelle.getKatze());
        }

    }


    public Map<String, Stelle> getSpielStelle() {
        return spielStelle;
    }

    public List<Katze> getkatze() {
        List<Katze> katze = new ArrayList<>();
        for (Stelle stelle : spielStelle.values()) {
            if (stelle.getKatze() != null) {
                katze.add(stelle.getKatze());
            }
        }
        return katze;

    }

    /**
     * Simuliere einen Angriff auf die Katzenkolonie.
     * Dies wird von der GUI aufgerufen, um das Spiel zu starten.
     *
     * @return Gewinnen (true) verlieren(False)
     */
    public void simulieren() throws KatzeGewinnenException, KatzenVerlorenException {
        int anzahlVogelen = getAktiveVogeln().size();

            if(istPause||!gameRunging) {return ;}
                bildWiederholrate++;
                //Die Vögel strömen aus dem Nest
                vogelNest.strategie(this);
                katzenAktionAusfuehren();

                anzahlVogelen = vogelnActionAusfuehren(anzahlVogelen);

//        } catch (KatzeGewinnenException ausnahme) {
//            System.out.println(ausnahme.getMessage());
//            gameRunging = false;
//
//
//        } catch (KatzenVerlorenException ausnahme) {
//            System.out.println(ausnahme.getMessage());
//            gameRunging = false;


    }

    /**
     * Erhöht die verfügbare Futtermenge um den angegebenen Wert.
     *
     * @param menge Anzahl des hinzuzufügenden Futters
     */
    public void futterHinzufuegen(int menge) {
        futter += menge;

    }

    /**
     * Wenn die Katze gewinnen, wird diese Methode aufgerufen.
     */
    public void katzeGewinnwn() {
        throw new KatzeGewinnenException("Katze Gewinnen");

    }

    /**
     * Wenn die Katze verliert, wird diese Methode aufgerufen.
     */
    public void katzenVerlieren() {

        throw new KatzenVerlorenException("Katzen Verloren, Game over!");
    }

    /**
     *
     * @return eine List, die alle noch lebenden Vögel auf dem Spielfeld
     */
    public List<Vogel> getAktiveVogeln() {
        aktiveVogeln = new ArrayList<>();
        for (Stelle stelle : spielStelle.values()) {
            aktiveVogeln.addAll(stelle.getVogeln());
        }
        return aktiveVogeln;
    }

    public void addVogel(Vogel vogel) {
        this.aktiveVogeln.add(vogel);
    }


    public int getBildWiederholrate() {
        return bildWiederholrate;
    }

    public int getFutter() {
        return futter;
    }

    public void setFutter(int futter) {
        this.futter = futter;
    }

    public int getSpalte() {
        return spalte;
    }
    public void pauseSpiel(){
        this.istPause = true;
    }
    public void SpielGehenWeiter(){
        this.istPause = false;
    }
    public boolean istPause(){
        return istPause;
    }

    public int getZeile() {
        return zeile;
    }

    public Image[] getKatzeImage() {
        return katzeImage;
    }

    public void setKatzeImage(Image[] katzeImage) {
        this.katzeImage = katzeImage;
    }

    public String[] getKatzename() {
        return katzename;
    }

    public void setKatzename(String[] katzename) {
        this.katzename = katzename;
    }

    public double getGridStartX() {
        return gridStartX;
    }

    public double getGridStartY() {
        return gridStartY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public List<AngriffEvent> getAngriffe() {
        List<AngriffEvent> result = new ArrayList<>(angriffe);
        angriffe.clear();
        return result;
    }

    public void addAngriff(AngriffEvent angriff) {
        angriffe.add(angriff);
    }

    public boolean isIstPause() {
        return istPause;
    }

    public void setIstPause(boolean istPause) {
        this.istPause = istPause;
    }

    public int[] getKatzeFutterKosten() {
        return katzeFutterKosten;
    }

    public void setKatzeFutterKosten(int[] katzeFutterKosten) {
        this.katzeFutterKosten = katzeFutterKosten;
    }
}
