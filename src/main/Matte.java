package main;

public class Matte implements Material {
    private final Vector3 albedo;

    public Matte(Vector3 albedo) {
        this.albedo = albedo;
    }

    @Override
    public Vector3 getAlbedo() {
        return albedo;
    }

    @Override
    public Ray scatter(Ray in, Intersection c) {
        var p = c.getPoint();
        var newDirection = c.getNormal().add(Globals.randomInUnitSquare());
        if (newDirection.magnitude() < Globals.EPS) {
            newDirection = c.getNormal();
        }
        newDirection = newDirection.normalize();
        return new Ray(
                p,
                newDirection
        );
    }
}
