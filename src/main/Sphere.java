package main;

public class Sphere {
    private final Vector3 center;
    private final double radius;

    public Sphere(Vector3 center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    Intersection intersects(Ray r) {
         /*Finds intersection of this sphere with the ray r by solving quadratic equation
         - Returns null if no root
         - Returns an Intersection object describing the intersection if there's at least one root*/
        Vector3 ac = r.getOrigin().add(center.addInv());

        double a = r.getDirection().magnitude_squared();
        double b = r.getDirection().scale(2).dot(ac);
        double c = ac.magnitude_squared() - radius * radius;

        double delta = b * b - 4 * a * c;
        if (delta < 0) {
            return null;
        }
        double sqrtDelta = Math.sqrt(delta);
        double x1 = (-b + sqrtDelta) / (2 * a);
        double x2 = (-b - sqrtDelta) / (2 * a);
        if (x1 < 0 && x2 < 0) {
            return null;
        }

        double t;
        if (x1 >= 0 && x2 >= 0) t = Math.min(x1, x2);
        else if (x1 >= 0) t = x1;
        else t = x2;

        var p = r.getOrigin().add(r.getDirection().scale(t));
        return new Intersection(
                p,
                p.add(center.addInv()).normalize(),
                t
        );
    }
}
