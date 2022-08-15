package main;

public class Vector3 {
    private final double x, y, z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector3 addInv() {
        return new Vector3(-getX(), -getY(), -getZ());
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(
                getX() + other.getX(),
                getY() + other.getY(),
                getZ() + other.getZ()
        );
    }

    public Vector3 scale(double magnitude) {
        return new Vector3(
                getX() * magnitude,
                getY() * magnitude,
                getZ() * magnitude
        );
    }

    public double magnitude_squared() {
        return getX() * getX() + getY() * getY() + getZ() * getZ();
    }

    public double magnitude() {
        return Math.sqrt(magnitude_squared());
    }

    public Vector3 normalize() {
        double l = magnitude();
        return scale(1.0f / l);
    }

    public double dot(Vector3 other) {
        return getX() * other.getX() + getY() * other.getY() + getZ() * other.getZ();
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(
                getY() * other.getZ() - getZ() * other.getY(),
                getZ() * other.getX() - getX() * other.getZ(),
                getX() * other.getY() - getY() * other.getX()
        );
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", getX(), getY(), getZ());
    }
}
