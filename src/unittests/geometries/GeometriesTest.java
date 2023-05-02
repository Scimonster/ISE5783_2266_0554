package unittests.geometries;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        Vector v1 =new Vector(0,0,1);
        Geometries empty=new Geometries(),
                geo= new Geometries(
                        new Plane(new Point (0,0,1), v1),
                        new Triangle(new Point(0,0,2), new Point(0,5,2), new Point (5,0,2)),
                        new Sphere(new Point(0,0,2), 2d)
                        );

        // ============ Equivalence Partitions Tests ==============
        //TC01: Intersects multiple geometries (triangle and plane) (2 points)
        assertEquals(geo.findIntersections(new Ray(new Point(3,1,0), v1)).size(), 2, "ERROR, returned wrong number of points");

        // =============== Boundary Values Tests ==================
        //TC02: empty collection of shapes (0 points)
        assertNull(empty.findIntersections(new Ray(new Point( 1,1,1), v1)), "ERROR, no shapes to intersect" );

        //TC03: no intersection points (0 points)
        assertNull(geo.findIntersections(new Ray(new Point(0,0,5), v1)), "ERROR should not have intersected any shapes");

        //TC04: only one shape is intersected (intersecting the plane) (1 point)
        assertEquals(geo.findIntersections(new Ray(new Point(8,7,0), v1)).size(),1, "ERROR, returned too many points");

        //TC05: Intersects all the shapes (4 points)
        assertEquals(geo.findIntersections(new Ray(new Point(1,1,0), v1)).size(),4, "ERROR, returned wrong number of points");


    }
}