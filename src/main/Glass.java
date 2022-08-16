package main;

public class Glass implements Material {
    private final Vector3 albedo;
    private final double refInd;

    public Glass(Vector3 albedo, double refInd) {
        this.albedo = albedo;
        this.refInd = refInd;
    }

    @Override
    public Vector3 getAlbedo() {
        return albedo;
    }

    @Override
    public Ray scatter(Ray in, Intersection c) {
        Vector3 R = in.getDirection();
        Vector3 n = c.getNormal();
        double ind_ratio = 0;
        if (n.dot(R) >= 0) {
            // Ray is inside the sphere
            n = n.addInv();
            ind_ratio = refInd / Globals.AIR_REFRACTION_INDEX;
        } else {
            ind_ratio = Globals.AIR_REFRACTION_INDEX / refInd;
        }

        double cos_theta = Math.min(R.addInv().dot(n), 1.0);
        double sin_theta = Math.sqrt(1 - cos_theta);
        new Vector3(0, 0, 0);
        Vector3 newDirection;

        if (sin_theta * ind_ratio > 1.0) {
            // Total internal reflection
            newDirection = R.add(n.scale(-2 * R.dot(n)));
        } else {
            Vector3 perp = R.add(n.scale(cos_theta)).scale(ind_ratio);
            Vector3 parallel = n.scale(-Math.sqrt(Math.abs(1 - perp.magnitude_squared())));
            newDirection = perp.add(parallel);
        }

        newDirection = newDirection.normalize();
        return new Ray(
                c.getPoint(),
                newDirection
        );
    }

    private double schlickApprox(double cos_theta, double ind_ratio) {
        double r0 = (1 - ind_ratio) / (1 + ind_ratio);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow(1 - cos_theta, 5);
    }
}
