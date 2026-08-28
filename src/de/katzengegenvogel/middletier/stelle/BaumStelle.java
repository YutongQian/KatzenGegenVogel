package de.katzengegenvogel.middletier.stelle;

import de.katzengegenvogel.middletier.KatzenVerlorenException;
import de.katzengegenvogel.middletier.Tier;
import de.katzengegenvogel.middletier.katze.Katze;

/**
 * <pre>
 *     Der Baum ist das Ende des Weges.
 *     Die Katze darf nicht auf dem Baum platziert werden.
 *     Wenn ein Vogel hereinfliegt, ist das Spiel vorbei und
 *     die Katze hat verloren.
 *
 * </pre>
 */
public class BaumStelle extends Stelle {
    public BaumStelle(String name) {
        super(name);
    }

    /**
     * Füge diesem Platz ein Tier hinzu.
     * Wenn der Vogel auf den Baum fliegt, hat die Katze verloren und der Vogel gewonnen.
     *
     * @param tier
     */
    @Override
    public void addTiere(Tier tier) {
        if((tier instanceof Katze)){
            throw new IllegalArgumentException("Nur ein Vogel darf auf den Baum kommen.");
        }
        throw new KatzenVerlorenException("Game Over, Katze verloren!");

    }

}
