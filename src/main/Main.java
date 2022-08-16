package main;

import java.io.PrintWriter;
import java.security.SecureRandom;

public class Main {
    final double ratio = 16.0 / 9.0;
    final int imgHeight = 400;
    final int imgWidth = (int) (imgHeight * ratio);

    final double viewportHeight = 2;
    final double viewportWidth = viewportHeight * ratio;
    final double viewportDist = -1;
    final int SAMPLES = 10;

    Sphere[] a;
    final Vector3 white = new Vector3(1, 1, 1);
    final Vector3 blue = new Vector3(0.5, 0.7, 1);
    final Vector3 red = new Vector3(1, 0, 0);

    final PrintWriter out = new PrintWriter(System.out);
    SecureRandom rng = new SecureRandom();
    final int MAX_DEPTH = 10;

    void run() {
        a = new Sphere[2];
        a[0] = new Sphere(new Vector3(0, 0, -1), 0.5);
        a[1] = new Sphere(new Vector3(0, -100.5, -1), 100);
        out.printf("P3\n%d %d\n255\n", imgWidth, imgHeight);

        Vector3 origin = new Vector3(0, 0, 0);
        Vector3 horizontal = new Vector3(viewportWidth, 0, 0);
        Vector3 vertical = new Vector3(0, viewportHeight, 0);
        Vector3 lowerLeft = new Vector3(
                -viewportWidth / 2,
                -viewportHeight / 2,
                viewportDist
        );

        // Render each pixel
        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                Vector3 fullColor = new Vector3(0, 0, 0);
                for (int s = 0; s < SAMPLES; s++) {
                    double y = (imgHeight - i - 1 + randomDouble(0, 1)) / (imgHeight - 1);
                    double x = (j + randomDouble(0, 1)) / (imgWidth - 1);

                    Ray r = new Ray(origin, lowerLeft.add(vertical.scale(y)).add(horizontal.scale(x)));
                    fullColor = fullColor.add(projectRay(r, MAX_DEPTH));
                }
                writeColor(fullColor);
            }
        }

        out.flush();
    }

    Vector3 projectRay(Ray r, int depth) {
        if (depth == 0) {
            return new Vector3(0, 0, 0);
        }
        Intersection c = null;
        for (var s : a) {
            Intersection d = s.intersects(r);
            if (d == null) continue;
            if (c == null || c.getT() > d.getT()) {
                c = d;
            }
        }
        if (c != null) {
            var p = c.getPoint();
            var n = c.getNormal();
            var newDirection = n.add(random_in_unit_square()); // Diffuse material
            Ray d = new Ray(p, newDirection);
            return projectRay(d, depth - 1).scale(0.5);
        }

        double yScaled = 0.5 * (r.getDirection().normalize().getY() + 1.0); // a value between 0.0 and 1.0
        return white.scale(1 - yScaled).add(blue.scale(yScaled));
    }

    void writeColor(Vector3 color) {
        // map [0,1] to [0,255]
        double r = color.getX() / SAMPLES;
        double g = color.getY() / SAMPLES;
        double b = color.getZ() / SAMPLES;
        r = Math.sqrt(r);
        g = Math.sqrt(g);
        b = Math.sqrt(b);

        int fr = (int) (255.999 * r);
        int fg = (int) (255.999 * g);
        int fb = (int) (255.999 * b);
        out.printf("%d %d %d\n", fr, fg, fb);
    }

    Vector3 random_in_unit_square() {
        while (true) {
            var ans = new Vector3(randomDouble(-1, 1), randomDouble(-1, 1), randomDouble(-1, 1));
            if (ans.magnitude_squared() < 1) return ans;
        }
    }

    double randomDouble(double min, double max) {
        return rng.nextDouble() * (max - min) + min;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}
