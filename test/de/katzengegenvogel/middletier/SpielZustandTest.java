package de.katzengegenvogel.middletier;

import de.katzengegenvogel.middletier.katze.Katze;
import de.katzengegenvogel.middletier.stelle.VogelNest;
import de.katzengegenvogel.middletier.vogel.Adler;
import de.katzengegenvogel.middletier.vogel.Schwan;
import de.katzengegenvogel.middletier.vogel.Vogel;
import de.katzengegenvogel.middletier.vogel.VogelPlan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Automatisierte Tests für die Spiellogik ({@link SpielZustand}).
 *
 * Diese Tests ersetzen den manuellen Smoke-Test in {@link KatzeSpielTest}:
 * Statt eines Programms mit main-Methode prüfen sie konkrete Erwartungen
 * und melden PASS/FAIL, ohne dass jemand die Konsole lesen muss.
 */
class SpielZustandTest {

    /** Baut einen frischen Spielzustand mit zwei Reihen und gegebenem Startfutter. */
    private SpielZustand neuesSpiel(int futter) {
        Map<String, Class<? extends Katze>> typen =
                KatzeUndVogelRegistery.getAlleKatzeTypen();

        VogelPlan plan = new VogelPlan();
        plan.addWelle(Vogel.class, 10);
        plan.addWelle(Schwan.class, 5);
        plan.addWelle(Adler.class, 5);

        VogelNest nest = new VogelNest(plan);
        return new SpielZustand(nest, typen, 2, futter);
    }

    @Test
    @DisplayName("Registry liefert alle zehn Katzentypen")
    void registryEnthaeltZehnKatzentypen() {
        assertEquals(10, KatzeUndVogelRegistery.getAlleKatzeTypen().size());
    }

    @Test
    @DisplayName("Einsetzen einer Katze zieht genau ihre Futterkosten ab")
    void einsetzenZiehtFutterkostenAb() {
        SpielZustand spiel = neuesSpiel(100);
        int vorher = spiel.getFutter();

        Katze katze = spiel.einsetzenKazte("1Feld6", "WerferKatze");

        assertNotNull(katze, "Katze muss eingesetzt werden");
        assertEquals(vorher - katze.getFutterKosten(), spiel.getFutter());
    }

    @Test
    @DisplayName("Ohne Futter wird keine Katze eingesetzt (Rückgabe null)")
    void ohneFutterKeinEinsetzen() {
        SpielZustand spiel = neuesSpiel(0);

        assertNull(spiel.einsetzenKazte("1Feld6", "WerferKatze"));
        assertEquals(0, spiel.getFutter());
        assertTrue(spiel.getkatze().isEmpty());
    }

    @Test
    @DisplayName("Eingesetzte Katzen sind im Spielzustand auffindbar")
    void eingesetzteKatzenSindAuffindbar() {
        SpielZustand spiel = neuesSpiel(100);

        spiel.einsetzenKazte("1Feld6", "WerferKatze");
        spiel.einsetzenKazte("0Feld5", "HarvesterKatze");

        assertEquals(2, spiel.getkatze().size());
    }

    @Test
    @DisplayName("Feldnamen sind groß geschrieben: '1feld6' ist KEIN gültiges Feld")
    void feldnameIstGrossgeschrieben() {
        SpielZustand spiel = neuesSpiel(100);

        // Dokumentiert eine Falle: die Felder heißen "1Feld6", nicht "1feld6".
        // Ein falscher Name führt derzeit zu einer RuntimeException statt zu
        // einer verständlichen Fehlermeldung.
        assertThrows(RuntimeException.class,
                () -> spiel.einsetzenKazte("1feld6", "WerferKatze"));
    }

    @Test
    @DisplayName("simulieren() läuft auf einem frischen Spiel ohne Fehler")
    void simulierenLaeuftOhneFehler() {
        SpielZustand spiel = neuesSpiel(200);

        assertDoesNotThrow(spiel::simulieren);
    }
}
