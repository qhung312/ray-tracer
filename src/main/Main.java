package main;

public class Main {
    final double ratio = 16.0 / 9.0;
    final int imgHeight = 400;
    final int imgWidth = (int) (imgHeight * ratio);

    final double viewportHeight = 2;
    final double viewportWidth = viewportHeight * ratio;
    final double viewportDist = -1;

    void run() {
        System.out.printf("P3\n%d %d\n255\n", imgWidth, imgHeight);

        Vector3 origin = new Vector3(0, 0, 0);
        Vector3 horizontal = new Vector3(viewportWidth, 0, 0);
        Vector3 vertical = new Vector3(0, viewportHeight, 0);
        Vector3 lowerLeft = new Vector3(
                -viewportWidth / 2,
                -viewportHeight / 2,
                viewportDist
        );

        var white = new Vector3(1, 1, 1);
        var blue = new Vector3(0.5, 0.7, 1.0);

        // Render each pixel
        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                double y = (double) (imgHeight - i - 1) / (imgHeight - 1);
                double x = (double) j / (imgWidth - 1);

                Vector3 r = lowerLeft.add(vertical.scale(y)).add(horizontal.scale(x));

                // Get color for this ray
                double yScaled = 0.5 * (r.normalize().getY() + 1.0); // a value between 0.0 and 1.0
                Vector3 color = white.scale(1 - yScaled).add(blue.scale(yScaled));

                int redVal = (int) (255.999 * color.getX());
                int greenVal = (int) (255.999 * color.getY());
                int blueVal = (int) (255.999 * color.getZ());

                System.out.printf("%d %d %d\n", redVal, greenVal, blueVal);
            }
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}
