package unittests.geometries;

import geometries.Plane;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Triangle;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test getNormal method for {@link geometries.Triangle#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests =======
        // TC01: point on the triangle -- normal could go in either direction
        Point p1 = new Point (1,0,0);
        Point p2 = new Point(0,1, 0);
        Point p3 = new Point (1,1,0);
        Vector v1 = new Vector (0,0,1);
        Vector v2 = v1.scale(-1);
        Plane cur = new Plane (p1,p2,p3);

        assertThat(cur.getNormal(p1), AnyOf.anyOf(IsEqual.equalTo(v1),IsEqual.equalTo(v2)));
    }

    /**
     * Test findIntersections method for {@link geometries.Triangle#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersections()
    {
        Vector v1= new Vector(0,0,1);
        Triangle T1 = new Triangle(new Point(1,0,1), new Point(1,1,1), new Point(0,1,1));
        // ============ Equivalence Partitions Tests =======

        //TC00: Ray does not intersect triangle's plane
        assertNull(T1.findIntersections(new Ray(new Point(0,0,0), new Vector(1, 0, 0))));

        //TC01: Ray does not intersect triangle (ray passes by a side of the triangle in plane that triangle is found) (0 points)
        assertNull(T1.findIntersections(new Ray(new Point(0.5,2,0), v1)), "Should return 0 points");


        //TC02: Ray does not intersect triangle (Intersection is found "by corner" of triangle, between 2 side "continuations) (0 points)
        assertNull(T1.findIntersections(new Ray(new Point(2,2,0), v1)), "Should return 0 points");

        //TC03: Ray intersects triangle (within the triangle) (1 point)
        Point p1= new Point(0.5,0.5,1);
        List<Point> result=T1.findIntersections(new Ray(new Point(0.9, 0.9, 0), v1));
        assertEquals(1,result.size(), "Returned wrong number of points");
        assertEquals(result, List.of(p1), "Returned wrong point");

        // =============== Boundary Values Tests ==================
        //TC04: Intersection is on a side of triangle (0 points)
        assertNull(T1.findIntersections(new Ray(new Point(0, 1, 0), v1)), "Should return 0 points");

        //TC05: Intersection is on a corner of triangle (0 points)
        assertNull(T1.findIntersections((new Ray(new Point(1,1,0), v1 ))), "Should return 0 points" );

        //TC06: Intersection is on "continuation of a side" of the triangle (0 points)
        assertNull(T1.findIntersections(new Ray(new Point(2,0,0), v1)), "Should return 0 points");





    }
}
