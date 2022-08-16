package main;

import java.io.PrintWriter;

public class Main {
    Sphere[] a;
    final Vector3 white = new Vector3(1, 1, 1);
    final Vector3 blue = new Vector3(0.5, 0.7, 1);
    final Vector3 red = new Vector3(1, 0, 0);

    final PrintWriter out = new PrintWriter(System.out);

    void run() {
        a = new Sphere[3];
        a[0] = new Sphere(new Vector3(-0.5, 0, -1), 0.5, Globals.SURFACE_MATTE);
        a[1] = new Sphere(new Vector3(0, -100.5, -1), 100, Globals.SURFACE_MATTE);
        a[2] = new Sphere(new Vector3(0.5, 0, -1), 0.5, Globals.SURFACE_METAL_FUZZY);
        out.printf("P3\n%d %d\n255\n", Globals.imgWidth, Globals.imgHeight);

        // Render each pixel
        for (int i = 0; i < Globals.imgHeight; i++) {
            for (int j = 0; j < Globals.imgWidth; j++) {
                Vector3 fullColor = new Vector3(0, 0, 0);
                for (int s = 0; s < Globals.SAMPLES; s++) {
                    double y = (Globals.imgHeight - i - 1 + Globals.randomDouble(0, 1)) / (Globals.imgHeight - 1);
                    double x = (j + Globals.randomDouble(0, 1)) / (Globals.imgWidth - 1);

                    Ray r = new Ray(
                            Globals.origin,
                            Globals.lowerLeft.add(Globals.vertical.scale(y)).add(Globals.horizontal.scale(x)).normalize()
                    );
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
        int idx = -1;
        for (int i = 0; i < a.length; i++) {
            Intersection d = a[i].intersects(r);
            if (d == null) continue;
            if (d.getT() <= 0.001) continue;
            if (c == null || c.getT() > d.getT()) {
                c = d;
                idx = i;
            }
        }
        if (c != null) {
            var mat = a[idx].getMaterial();
            var albedo = mat.getAlbedo();
            var nray = mat.scatter(r, c);
            return albedo.scaleVector(projectRay(nray, depth - 1));
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
