package main;

public class Intersection {
    private final Vector3 point, normal;
    private final double t;

    public Intersection(Vector3 point, Vector3 normal, double t) {
        this.point = point;
        this.normal = normal;
        this.t = t;
    }

    public Vector3 getPoint() {
        return point;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public double getT() {
        return t;
    }
}
