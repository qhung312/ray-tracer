package main;

import java.security.SecureRandom;

public class Globals {
    private static final SecureRandom rng = new SecureRandom();
    public static final double PI = 3.1415926536;

    public static final double ratio = 16.0 / 9.0;
    public static final int imgHeight = 400;
    public static final int imgWidth = (int) (imgHeight * ratio);

    public static final double viewportHeight = 2;
    public static final double viewportWidth = viewportHeight * ratio;
    public static final double viewportDist = -1;
    public static final int SAMPLES = 10;

    public static final int MAX_DEPTH = 10;

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
