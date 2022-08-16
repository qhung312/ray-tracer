package main;

import java.security.SecureRandom;

public class Globals {
    private static final SecureRandom rng = new SecureRandom();
    public static final double PI = 3.1415926536;

    public static double randomDouble(double min, double max) {
        return rng.nextDouble() * (max - min) + min;
    }

    public static Vector3 randomInUnitSquare() {
        double p = randomDouble(0, 1);
        double theta = randomDouble(0, 2 * PI);
        double phi = randomDouble(0, PI);
        return new Vector3(
                p * Math.sin(phi) * Math.cos(theta),
                p * Math.sin(phi) * Math.sin(theta),
                p * Math.cos(phi)
        );
    }
}
