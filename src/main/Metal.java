package main;

public class Metal implements Material {
    private final Vector3 albedo;
    private final double fuzz;

    Metal(Vector3 albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz;
    }

    @Override
    public Vector3 getAlbedo() {
        return albedo;
    }

    @Override
    public Ray scatter(Ray in, Intersection c) {
        var v = in.getDirection();
        var n = c.getNormal();
        var newDirection = v.add(n.scale(-2 * v.dot(n))).add(Globals.randomInUnitSquare().scale(fuzz));
        if (newDirection.magnitude() < Globals.EPS) {
            newDirection = v.add(n.scale(-2 * v.dot(n)));
        }
        newDirection = newDirection.normalize();
        return new Ray(
                c.getPoint(),
                newDirection
        );
    }
}
