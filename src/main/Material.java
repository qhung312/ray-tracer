package main;

public interface Material {
    Ray scatter(Ray in, Intersection c);
    Vector3 getAlbedo();
}
