package main;

public class Ray {
    private final Vector3 origin, direction;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Vector3 at(double t) {
        return origin.add(direction.scale(t));
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f. %.3f) + t(%.3f, %.3f, %.3f)",
                origin.getX(), origin.getY(), origin.getZ(),
                direction.getX(), direction.getY(), direction.getZ());
    }
}
