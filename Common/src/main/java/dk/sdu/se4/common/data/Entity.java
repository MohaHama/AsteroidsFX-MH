package dk.sdu.se4.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID id = UUID.randomUUID();
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;
    private int health = 3;
    private long immuneUntil = 0;
    private long flashUntil = 0;

    public String getID() {
        return id.toString();
    }

    public void setPolygonCoordinates(double... coordinates) {
        this.polygonCoordinates = coordinates;
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public long getImmuneUntil() {
        return immuneUntil;
    }

    public void setImmuneUntil(long immuneUntil) {
        this.immuneUntil = immuneUntil;
    }

    public long getFlashUntil() {
        return flashUntil;
    }

    public void setFlashUntil(long flashUntil) {
        this.flashUntil = flashUntil;
    }
}