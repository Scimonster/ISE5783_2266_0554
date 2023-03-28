package unittests.geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Sphere;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test getNormal method for {@link geometries.Sphere#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests =======
        // TC01: point on the sphere
        Point p1= new Point (0,0,0);
        Sphere s1= new Sphere(1d,p1);

        Point p2=new Point (1,0,0);

        Vector v1=p2.subtract(p1).normalize();
        assertEquals(s1.getNormal(p2), v1, "Normal not calculated correctly");
    }

    /**
     * Test method findIntersections for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections()
    {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere, should have no points");
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere at wrong points");
        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(0.5,0,0), new Vector(0.5, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(result.get(0), new Point(1,0,1), "Ray crosses sphere at wrong point");

        // TC04: Ray starts after the sphere (0 points)
        result=sphere.findIntersections(new Ray(new Point(10,10,10), new Vector(9.5,10,10)));
        assertNull(result,"Ray's line after sphere, should have no points");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result= sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(-1, -1 ,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(1,-1,0), result.get(0), "Unexpected point");

        // TC12: Ray starts at sphere and goes outside (0 points)
        result= sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(1,1 ,0)));
        assertNull(result, "Should return 0 points");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result=sphere.findIntersections(new Ray(new Point(1,2,0), new Vector(0,-1,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        p1 = new Point(1,1,0);
        p2 = new Point(1,-1,0);
        if (result.get(0).getY() < result.get(1).getY())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1,p2), result, "Unexpected points");

        // TC14: Ray starts at sphere and goes inside
        result=sphere.findIntersections(new Ray(p1, new Vector(0,-1,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p2, result.get(0), "Unexpected point");

        // TC15: Ray starts inside (1 points)
        result=sphere.findIntersections(new Ray(new Point(1,0.5,0), new Vector(0,-1,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p2, result.get(0), "Unexpected point");

        // TC16: Ray starts at the center (1 points)
        result=sphere.findIntersections(new Ray(sphere.getCenter(), new Vector(0,-1,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(p2, result.get(0), "Unexpected point");

        // TC17: Ray starts at sphere and goes outside (0 points)
        result=sphere.findIntersections(new Ray(p2, new Vector(0,-1,0)));
        assertNull(result, "Unexpected result");
        // TC18: Ray starts after sphere (0 points)
        result=sphere.findIntersections(new Ray(new Point(1,-2,0), new Vector(0,-1,0)));
        assertNull(result, "Unexpected result");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        result=sphere.findIntersections(new Ray(new Point(1,-1,1), new Vector(0,1,1)));
        assertNull(result, "Unexpected result");
        // TC20: Ray starts at the tangent point
        result=sphere.findIntersections(new Ray(new Point(1,0,1), new Vector(0,1,1)));
        assertNull(result, "Unexpected result");

        // TC21: Ray starts after the tangent point
        result=sphere.findIntersections(new Ray(new Point(1,1,1), new Vector(0,1,1)));
        assertNull(result, "Unexpected result");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result=sphere.findIntersections(new Ray(new Point(1,2,0), new Vector(0,0,1)));
        assertNull(result, "Unexpected result");

    }
}
