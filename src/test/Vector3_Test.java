package test;

import main.Vector3;

public class Vector3_Test {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
    }

    public static void test1() {
        Vector3 a = new Vector3(1.5, 2.5, 3.5);
        Vector3 b = new Vector3(0.1, 0.4, -0.5);
        System.out.printf("Test 1: Adding %s and %s -> %s\n\n", a, b, a.add(b));
    }

    public static void test2() {
        Vector3 a = new Vector3(5, 6, 3.45);
        System.out.printf("Test 2: Additive inverse of %s -> %s\n\n", a, a.addInv());
    }

    public static void test3() {
        Vector3 a = new Vector3(13, 14.1, 72.5);
        final double s = 3.5;
        System.out.printf("Test 3: Scaling %s by %.3f -> %s\n\n", a, s, a.scale(s));
    }

    public static void test4() {
        Vector3 a = new Vector3(4, 5, 6);
        System.out.printf("Test 4: Length of %s -> %.3f\n\n", a, a.magnitude());
    }

    public static void test5() {
        Vector3 a = new Vector3(1, 2, 3);
        Vector3 b = new Vector3(4, 5, 6);
        System.out.printf("Test 5:\ndot of %s and %s -> %.3f\ncross of %s and %s -> %s\n\n", a, b, a.dot(b), a, b, a.cross(b));
    }
}
