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
        double a = r.getDirection().magnitude_squared();
        double b = r.getDirection().scale(2).dot(r.getOrigin().add(center.addInv()));
        double c = r.getOrigin().magnitude_squared() -
                   r.getOrigin().dot(center) +
                   center.magnitude_squared() -
                   radius * radius;

        double delta = b * b - 4 * a * c;
        if (delta < 0) {
            return null;
        }
        double sqrtDelta = Math.sqrt(delta);
        double x1 = (-b + sqrtDelta) / (2 * a);
        double x2 = (-b - sqrtDelta) / (2 * a);
        if (x1 < 0) {
            return null;
        }
        double t = (x2 >= 0 ? x2 : x1);

        var p = r.getOrigin().add(r.getDirection().scale(t));
        return new Intersection(
                p,
                p.add(center.addInv()),
                t
        );
    }
}
