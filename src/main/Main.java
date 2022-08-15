package main;

import java.io.PrintWriter;

public class Main {
    final double ratio = 16.0 / 9.0;
    final int imgHeight = 400;
    final int imgWidth = (int) (imgHeight * ratio);

    final double viewportHeight = 2;
    final double viewportWidth = viewportHeight * ratio;
    final double viewportDist = -1;

    Sphere[] a;
    final Vector3 white = new Vector3(1, 1, 1);
    final Vector3 blue = new Vector3(0.5, 0.7, 1);
    final Vector3 red = new Vector3(1, 0, 0);

    final PrintWriter out = new PrintWriter(System.out);
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
                double y = (double) (imgHeight - i - 1) / (imgHeight - 1);
                double x = (double) j / (imgWidth - 1);

                Ray r = new Ray(origin, lowerLeft.add(vertical.scale(y)).add(horizontal.scale(x)));

                // Get color for this ray
                var color = projectRay(r, MAX_DEPTH);
                writeColor(color);
            }
        }

        out.flush();
    }

    Vector3 projectRay(Ray r, int depth) {
        if (depth == 0) {
            return new Vector3(0, 0, 0);
        }
        Intersection c = null;
        for (var circle : a) {
            Intersection d = circle.intersects(r);
            if (d == null) continue;
            if (c == null || c.getT() > d.getT()) {
                c = d;
            }
        }
        if (c != null) {
            var n = c.getNormal().normalize();
            Ray d = new Ray(
                    c.getPoint(),
                    r.getDirection().add(n.scale(-2 * n.dot(r.getDirection())))
            );
            return projectRay(d, depth - 1).scale(0.5);
        }

        double yScaled = 0.5 * (r.getDirection().normalize().getY() + 1.0); // a value between 0.0 and 1.0
        return white.scale(1 - yScaled).add(blue.scale(yScaled));
    }

    void writeColor(Vector3 color) {
        // map [0,1] to [0,255]
        int r = (int) (255.999 * color.getX());
        int g = (int) (255.999 * color.getY());
        int b = (int) (255.999 * color.getZ());
        out.printf("%d %d %d\n", r, g, b);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}
