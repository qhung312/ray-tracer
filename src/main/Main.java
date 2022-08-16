package main;

import java.io.PrintWriter;

public class Main {
    Sphere[] a;
    final Vector3 white = new Vector3(1, 1, 1);
    final Vector3 blue = new Vector3(0.5, 0.7, 1);
    final Vector3 red = new Vector3(1, 0, 0);

    final PrintWriter out = new PrintWriter(System.out);

    void run() {
        a = new Sphere[2];
        a[0] = new Sphere(new Vector3(0, 0, -1), 0.5);
        a[1] = new Sphere(new Vector3(0, -100.5, -1), 100);
        out.printf("P3\n%d %d\n255\n", Globals.imgWidth, Globals.imgHeight);

        Vector3 origin = new Vector3(0, 0, 0);
        Vector3 horizontal = new Vector3(Globals.viewportWidth, 0, 0);
        Vector3 vertical = new Vector3(0, Globals.viewportHeight, 0);
        Vector3 lowerLeft = new Vector3(
                -Globals.viewportWidth / 2,
                -Globals.viewportHeight / 2,
                Globals.viewportDist
        );

        // Render each pixel
        for (int i = 0; i < Globals.imgHeight; i++) {
            for (int j = 0; j < Globals.imgWidth; j++) {
                Vector3 fullColor = new Vector3(0, 0, 0);
                for (int s = 0; s < Globals.SAMPLES; s++) {
                    double y = (Globals.imgHeight - i - 1 + Globals.randomDouble(0, 1)) / (Globals.imgHeight - 1);
                    double x = (j + Globals.randomDouble(0, 1)) / (Globals.imgWidth - 1);

                    Ray r = new Ray(origin, lowerLeft.add(vertical.scale(y)).add(horizontal.scale(x)).normalize());
                    fullColor = fullColor.add(projectRay(r, Globals.MAX_DEPTH));
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
            if (d.getT() <= 0.001) continue;
            if (c == null || c.getT() > d.getT()) {
                c = d;
            }
        }
        if (c != null) {
            var p = c.getPoint();
            var n = c.getNormal();
            var newDirection = n.add(Globals.randomInUnitSquare()).normalize(); // Diffuse material
            Ray d = new Ray(p, newDirection);
            return projectRay(d, depth - 1).scale(0.5);
        }

        double yScaled = 0.5 * (r.getDirection().normalize().getY() + 1.0); // a value between 0.0 and 1.0
        return white.scale(1 - yScaled).add(blue.scale(yScaled));
    }

    void writeColor(Vector3 color) {
        // map [0,1] to [0,255]
        double r = color.getX() / Globals.SAMPLES;
        double g = color.getY() / Globals.SAMPLES;
        double b = color.getZ() / Globals.SAMPLES;
        r = Math.sqrt(r);
        g = Math.sqrt(g);
        b = Math.sqrt(b);

        int fr = (int) (255.999 * r);
        int fg = (int) (255.999 * g);
        int fb = (int) (255.999 * b);
        out.printf("%d %d %d\n", fr, fg, fb);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}
