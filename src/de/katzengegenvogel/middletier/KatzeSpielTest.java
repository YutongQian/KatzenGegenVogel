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
        neuSpiel.einsetzenKazte("1Feld6","WerferKatze");
        neuSpiel.einsetzenKazte("0Feld5","WerferKatze");
        neuSpiel.einsetzenKazte("1Feld7", "HungerKatze");
        neuSpiel.einsetzenKazte("0Feld7","WerferKatze");
        neuSpiel.einsetzenKazte("0Feld8","WerferKatze");
        neuSpiel.einsetzenKazte("1Feld8","WerferKatze");
        neuSpiel.einsetzenKazte("1Feld5","WerferKatze");
        neuSpiel.einsetzenKazte("0Feld6","WerferKatze");
        neuSpiel.einsetzenKazte("0Feld4","HarvesterKatze");
        neuSpiel.einsetzenKazte("1Feld4","HarvesterKatze");

        neuSpiel.simulieren();



    }
}
