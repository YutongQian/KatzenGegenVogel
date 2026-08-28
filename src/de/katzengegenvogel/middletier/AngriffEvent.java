package de.katzengegenvogel.middletier;
/**
 * Repräsentiert ein Ereignis eines Angriffs im Spiel.
 *
 * Ein AngriffEvent speichert die Start- und Zielposition
 * eines Angriffs. Die gespeicherten Koordinaten werden verwendet,
 * um die entsprechende Angriffsanimation zu erstellen.
 *
 */
public class AngriffEvent {
    private final double startX;
    private final double startY;
    private final double zielX;
    private final double zielY;

    public AngriffEvent(
            double startX,
            double startY,
            double zielX,
            double zielY) {

        this.startX = startX;
        this.startY = startY;
        this.zielX = zielX;
        this.zielY = zielY;
    }
    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getZielX() {
        return zielX;
    }

    public double getZielY() {
        return zielY;
    }
}
