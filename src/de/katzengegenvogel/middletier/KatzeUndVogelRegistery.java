package de.katzengegenvogel.middletier;

import de.katzengegenvogel.middletier.katze.*;
import de.katzengegenvogel.middletier.katze.behaelter.BodyguardKatze;
import de.katzengegenvogel.middletier.katze.behaelter.TankKatze;
import de.katzengegenvogel.middletier.katze.werfer.KurzWerferKatze;
import de.katzengegenvogel.middletier.katze.werfer.LangWerferKatze;
import de.katzengegenvogel.middletier.katze.werfer.QueenKatze;
import de.katzengegenvogel.middletier.katze.werfer.WerferKatze;
import de.katzengegenvogel.middletier.vogel.Adler;
import de.katzengegenvogel.middletier.vogel.Schwan;
import de.katzengegenvogel.middletier.vogel.Vogel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Registry für die verfügbaren Katzen- und Vogeltypen des Spiels.
 *
 * <pre>
 *     Die Klasse stellt eine zentrale Übersicht über alle im Spiel
 *     verwendeten Katzen- und Vogelklassen bereit. Dadurch können die
 *     verschiedenen Typen über ihre Klassenobjekte abgerufen werden.
 *</pre>
 *

 */
public class KatzeUndVogelRegistery {
    /**
     * Gibt alle verfügbaren Katzentypen zurück.
     * Jeder Katzentyp wird unter seinem Namen zusammen mit dem
     * entsprechenden Klassenobjekt in einer Map gespeichert.
     *
     * @return Map mit den Namen und Klassen der verfügbaren Katzentypen
     */
    public static Map<String, Class<? extends Katze>> getAlleKatzeTypen() {
        HashMap<String, Class<? extends Katze>> map = new HashMap<>();

        map.put("WerferKatze", WerferKatze.class);
        map.put("KurzWerferKatze", KurzWerferKatze.class);
        map.put("LangWerferKatze", LangWerferKatze.class);
        map.put("QueenKatze", QueenKatze.class);
        map.put("FeuerKatze", FeuerKatze.class);
        map.put("HarvesterKatze", HarvesterKatze.class);
        map.put("HungerKatze", HungerKatze.class);
        map.put("WandKatze", WandKatze.class);
        map.put("BodyguardKatze", BodyguardKatze.class);
        map.put("TankKatze", TankKatze.class);


        return map;
    }
    /**
     * Gibt alle verfügbaren Vogeltypen des Spiels zurück.
     *
     * @return Liste mit den Klassen der verfügbaren Vogeltypen
     */
    public static List<Class<? extends Vogel>> getAlleVogelType() {
        return List.of(
                Vogel.class,
                Schwan.class,
                Adler.class

        );
    }
}
