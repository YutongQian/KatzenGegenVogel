package de.katzengegenvogel.frontend;

public class AngriffAnimation {
    private double x;
    private double y;

    private final double zielX;
    private final double zielY;

    private final double dx;
    private final double dy;

    private boolean fertig = false;

    public AngriffAnimation(
            double startX,
            double startY,
            double zielX,
            double zielY) {

        this.x = startX;
        this.y = startY;

        this.zielX = zielX;
        this.zielY = zielY;

        double distance = Math.hypot(
                zielX - startX,
                zielY - startY
        );

        dx = (zielX - startX) / distance * 5;
        dy = (zielY - startY) / distance * 5;
    }

    public void update() {
        x += dx;
        y += dy;

        if (Math.hypot(zielX - x, zielY - y) < 10) {
            fertig = true;
        }
    }

    public boolean istFertig() {
        return fertig;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }



    }
