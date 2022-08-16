package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    Sphere[] a;
    final Vector3 white = new Vector3(1, 1, 1);
    final Vector3 blue = new Vector3(0.5, 0.7, 1);

    PrintWriter out;

    void run() throws IOException {
        generateWorld();
        out = new PrintWriter(new FileWriter(Globals.FILENAME));
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
        out.close();
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

    void generateWorld() {
        a = new Sphere[14];
        a[0] = new Sphere(new Vector3(0, -1000, 0), 1000, Globals.GROUND_MATERIAL); // Make the ground
        a[1] = new Sphere(new Vector3(10, 3, -4), 3, Globals.GLASS_MATERIAL);
        a[2] = new Sphere(new Vector3(0.5, 5, -6), 5, Globals.METAL_MATERIAL);
        a[3] = new Sphere(new Vector3(-10, 3.5, -5), 3.5, new Matte(new Vector3(0.44, 0.33, 0.23)));

        final double h = 0.6;
        int idx = 4;
        for (int x = -4; x <= 4; x += 2) {
            for (int z = -1; z <= 1; z += 2) {
                double dx = Globals.randomDouble(-0.2, 0.2);
                double dz = Globals.randomDouble(-0.2, 0.2);

                int type = Globals.randomInt(0, 2);
                if (type == 0) {
                    // Matte
                    Vector3 color = new Vector3(Globals.randomDouble(0, 1),
                                                Globals.randomDouble(0, 1),
                                                Globals.randomDouble(0, 1));
                    a[idx++] = new Sphere(new Vector3(x + dx, h, z + dz), h, new Matte(color));
                } else if (type == 1) {
                    // Metal
                    double f = Globals.randomDouble(0, 0.5);
                    a[idx++] = new Sphere(new Vector3(x + dx, h, z + dz), h, new Metal(Globals.ONES, f));
                } else {
                    // Glass
                    double r = Globals.randomDouble(1.1, 1.5);
                    a[idx++] = new Sphere(new Vector3(x + dx, h, z + dz), h, new Glass(Globals.ONES, r));
                }
            }
        }
    }

    void writeColor(Vector3 color) throws IOException {
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

    public static void main(String[] args) throws IOException {
        Main app = new Main();
        app.run();
    }
}
