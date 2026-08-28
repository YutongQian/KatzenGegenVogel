package de.katzengegenvogel.middletier;

import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.stelle.VogelNest;
import de.katzengegenvogel.middletier.vogel.Adler;
import de.katzengegenvogel.middletier.vogel.Schwan;
import de.katzengegenvogel.middletier.vogel.Vogel;
import de.katzengegenvogel.middletier.vogel.VogelPlan;

import java.util.List;
import java.util.Map;

public class KatzeSpielTest {
    public static void main(String[] args) {


        Map<String,Class <? extends Katze>> katzeType=KatzeUndVogelRegistery.getAlleKatzeTypen();
        List<Class<? extends Vogel>> vogelType=KatzeUndVogelRegistery.getAlleVogelType();
        int dimension=2;
        int originalFutter=200;


        VogelPlan plan=new VogelPlan();

        plan.addWelle(Vogel.class,10);
        plan.addWelle(Schwan.class,5);

        plan.addWelle(Adler.class,5);

        VogelNest neuNest=new VogelNest(plan);
        SpielZustand neuSpiel=new SpielZustand(neuNest,katzeType,dimension,originalFutter);
        neuSpiel.einsetzenKazte("1feld6","WerferKatze");
        neuSpiel.einsetzenKazte("0feld5","WerferKatze");
        neuSpiel.einsetzenKazte("1feld7", "HungerKatze");
        neuSpiel.einsetzenKazte("0feld7","WerferKatze");
        neuSpiel.einsetzenKazte("0feld8","WerferKatze");
        neuSpiel.einsetzenKazte("1feld8","WerferKatze");
        neuSpiel.einsetzenKazte("1feld5","WerferKatze");
        neuSpiel.einsetzenKazte("0feld6","WerferKatze");
        neuSpiel.einsetzenKazte("0feld4","HarvesterKatze");
        neuSpiel.einsetzenKazte("1feld4","HarvesterKatze");

        neuSpiel.simulieren();



    }
}
