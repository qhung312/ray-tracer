package main;

import java.util.Random;

public class Globals {
    public static final String FILENAME = "raytracing.ppm";
    private static final Random rng = new Random();
    public static final double PI = 3.1415926536;
    public static final double EPS = 1e-7;

    public static final double ratio = 16.0 / 9.0;
    public static final int imgHeight = 1080;
    public static final int imgWidth = (int) (imgHeight * ratio);

    public static final double viewportHeight = 3;
    public static final double viewportWidth = viewportHeight * ratio;
    public static final double viewportDist = -1;
    public static Vector3 origin = new Vector3(0, 3, 3.5);
    public static Vector3 horizontal = new Vector3(viewportWidth, 0, 0);
    public static Vector3 vertical = new Vector3(0, viewportHeight, 0);
    public static Vector3 lowerLeft = new Vector3(
            -viewportWidth / 2,
            -viewportHeight / 2,
            viewportDist
    );

    public static final int SAMPLES = 50;
    public static final int MAX_DEPTH = 50;

    // Define some surfaces
    public static final Vector3 ONES = new Vector3(1, 1, 1);
    public static final Material GROUND_MATERIAL = new Matte(new Vector3(0.5, 0.5, 0.5));
    public static final Material METAL_MATERIAL = new Metal(ONES, 0);
    public static final Material GLASS_MATERIAL = new Glass(ONES, 1.333); // water!!

    public static final double AIR_REFRACTION_INDEX = 1.0;

    public static double randomDouble(double min, double max) {
        return rng.nextDouble() * (max - min) + min;
    }

    public static int randomInt(int min, int max) {
        return min + rng.nextInt(max - min + 1);
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
