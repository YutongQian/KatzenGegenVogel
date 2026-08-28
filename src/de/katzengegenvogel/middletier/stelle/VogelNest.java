package de.katzengegenvogel.middletier.stelle;

import de.katzengegenvogel.middletier.SpielZustand;
import de.katzengegenvogel.middletier.Tier;
import de.katzengegenvogel.middletier.vogel.Vogel;
import de.katzengegenvogel.middletier.vogel.VogelPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Der Ort, an dem die Vögel ihren Angriff starteten.
 */
public class VogelNest extends Stelle {
    /**
     * Spielplan: Wie viele Vögel greifen zu welchem Zeitpunkt an?
     */
    private VogelPlan vogelPlan;
    /**
     *
     */
    private List<Vogel> vogeln;

    private Random random;
    /**
     * Die Zeit oder Runde, in der der Vogel schließlich wegfliegt
     */
    private int mindetenSpielrund;


    public VogelNest(VogelPlan plan) {
        super("VogelNest");

        this.istVogelNest = true;

        this.vogelPlan = plan;

        this.vogeln = new ArrayList<>();
        this.random = new Random();
        mindetenSpielrund = plan.getMindestSpielrund();

    }

    /**
     * Suche in der Liste der Stelle, die unter spielzustand generiert wurde,
     * nach dem Ausgang von „Vogelnest“ und füge ihn der Liste „ausgang“ hinzu.
     * Die von Vogelplan generierten Vögel werden zufällig in den „Ausgang“ geleitet.
     *
     * @param spielZustand
     */
    public void strategie(SpielZustand spielZustand) {

        List<Stelle> ausgang = new ArrayList<>();
        for (Stelle place : spielZustand.getSpielStelle().values()) {
            if (place.getEingang() == this) {
                ausgang.add(place);
            }
        }
        if (ausgang.isEmpty()) {
            throw new IllegalStateException(
                    "Keine Vogel-Eingänge gefunden!"
            );
        }


        int spielZeit = spielZustand.getBildWiederholrate();
        Vogel vogel = vogelPlan.get(spielZeit);
        Stelle ziel = ausgang.get(random.nextInt(ausgang.size()));
        if (vogel != null) {
            vogel.gehenZu(ziel);
            vogel.setX(ziel.getX() + ziel.getWidth());
            spielZustand.addVogel(vogel);

        }


    }

    @Override
    public void addTiere(Tier tier) {

        super.addTiere(tier);
    }


    public int getMindetenSpielrund() {
        return mindetenSpielrund;
    }
}
