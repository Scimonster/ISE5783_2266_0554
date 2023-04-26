package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;
import renderer.Camera;

import java.util.List;

public class IntegrationTests {
    final static int NX = 3;
    final static int NY = 3;
    final static int WIDTH = 3;
    final static int HEIGHT = 3;

    private int countIntersections(Camera camera, Intersectable object)
    {
        int count = 0;
        Ray cameraRay;
        List<Point> intersections;
        for (int i=0; i<NX; i++) {
            for (int j=0; j<NY; j++)
            {
                // camera ray through the point in the view plane
                cameraRay = camera.constructRay(NX, NY, j, i);
                // object intersections with camera ray
                intersections = object.findIntersections(cameraRay);
                // add to the count
                if (intersections != null) {
                    count += intersections.size();
                }
            }
        }
        return count;
    }

    /**
     * Integration test for methods
     * {@link renderer.Camera#constructRay(int, int, int, int)}
     * {@link geometries.Sphere#findIntersections(Ray)}
     */
    @Test
    void testCameraSphereIntersections()
    {
        Camera camera;
        Sphere sphere;

        // TC01: sphere r=1, 2 intersection points
        camera = new Camera(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        sphere = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2, countIntersections(camera, sphere), "wrong number of intersections");

        // TC02: sphere r=2.5, 18 intersections
        camera = new Camera(
                new Point(0, 0, 0.5),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(18, countIntersections(camera, sphere), "wrong number of intersections");

        // TC03: sphere r=2, 10 intersections
        camera = new Camera(
                new Point(0, 0, 0.5),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        sphere = new Sphere(2, new Point(0, 0, -2));
        assertEquals(10, countIntersections(camera, sphere), "wrong number of intersections");

        // TC04: sphere r=4, 9 intersections (sphere encompasses viewplane)
        camera = new Camera(
                new Point(0, 0, 0.5),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        sphere = new Sphere(4, new Point(0, 0, 0.5));
        assertEquals(9, countIntersections(camera, sphere), "wrong number of intersections");

        // TC05: sphere r=0.5, 0 intersections (behind camera)
        camera = new Camera(
                new Point(0, 0, 0.5),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, countIntersections(camera, sphere), "wrong number of intersections");
    }

    /**
     * Integration test for methods
     * {@link renderer.Camera#constructRay(int, int, int, int)}
     * {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    void testCameraPlaneIntersections()
    {
        Camera camera;
        Plane plane;

        // TC01: plane parallel to viewplane
        camera = new Camera(
                new Point(0, 0, 2),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        // xy plane
        plane = new Plane(new Point(0, 0, 0), new Vector(0,0, 1));
        assertEquals(9, countIntersections(camera, plane), "wrong number of intersections");

        // TC02: plane not parallel to viewplane but intersects fully
        camera = new Camera(
                new Point(0, 0, 2),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        plane = new Plane(new Point(0, 1, -1), new Vector(1,0, 2));
        assertEquals(9, countIntersections(camera, plane), "wrong number of intersections");

        // TC03: plane parallel to vectors through top of viewplane, 6 intersections
        camera = new Camera(
                new Point(0, 0, 2),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        plane = new Plane(new Point(0, 1, -1), new Vector(1,0, 1));
        assertEquals(6, countIntersections(camera, plane), "wrong number of intersections");
    }

    /**
     * Integration test for methods
     * {@link renderer.Camera#constructRay(int, int, int, int)}
     * {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void testCameraTriangleIntersections()
    {
        Camera camera;
        Triangle triangle;

        // TC01: small triangle
        camera = new Camera(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        triangle = new Triangle(
                new Point(0,1, -2),
                new Point(1,-1, -2),
                new Point(-1, -1, -2)
        );
        assertEquals(1, countIntersections(camera, triangle));

        // TC02: big triangle
        camera = new Camera(
                new Point(0, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(WIDTH, HEIGHT).setVPDistance(1);
        triangle = new Triangle(
                new Point(0,20, -2),
                new Point(1,-1, -2),
                new Point(-1, -1, -2)
        );
        assertEquals(2, countIntersections(camera, triangle));


    }
}
