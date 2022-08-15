package main;

import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        SecureRandom rng = new SecureRandom();
        final int height = 256, width = 256;
        System.out.printf("P3\n%d %d\n", width, height);

        System.out.println(255);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.printf("%d %d %d\n",
                        rng.nextInt(256),
                        rng.nextInt(256),
                        rng.nextInt(256));
            }
        }
    }
}
