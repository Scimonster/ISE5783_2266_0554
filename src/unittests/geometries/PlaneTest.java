package unittests.geometries;

import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Plane;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PlaneTest {
    /**
     * Test getNormal method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests =======
        // TC01: xy plane
        Point p1 = new Point (1,0,0);
        Point p2 = new Point(0,1, 0);
        Point p3 = new Point (1,1,0);
        Vector v1 = new Vector (0,0,1);
        Vector v2 = v1.scale(-1);
        Plane cur = new Plane (p1,p2,p3);

        assertThat(cur.getNormal(p1), AnyOf.anyOf(IsEqual.equalTo(v1),IsEqual.equalTo(v2)));

    }

    @Test
    void testConstructor() {
        Point p1 = new Point (1,0,0);
        Point p2 = new Point(0,1, 0);
        // =============== Boundary Values Tests ==================
        // TC01: first and second points equal
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1, p2), "should not create plane with points being equal");

        // TC02: all points in a line
        Point p3 = new Point (1,0,0);
        Point p4 = new Point(2,0, 0);
        Point p5 = new Point(3,0, 0);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p3, p4, p5), "should not create plane with 3 colinear points");
    }

    /**
     * Test findIntersections method for {@link geometries.Plane#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersections()
    {
        // plane z=1, with reference point (0,0,1)
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(0,0,1));
        List<Point> result;
        // ============ Equivalence Partitions Tests =======
        // Ray is neither orthogonal nor parallel to the plane
        // TC01: Ray intersects the plane (1 point)
        result = plane.findIntersections(new Ray(new Point(1,1,2), new Vector(-1, -1, -1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(0,0,1), result.get(0), "Unexpected point");
        // TC02: Ray does not intersect the plane (0 points)
        result = plane.findIntersections(new Ray(new Point(1,1,2), new Vector(1, 1, 1)));
        assertNull(result, "Should return 0 points");

        // =============== Boundary Values Tests ==================
        // Group: ray is parallel to the plane (no points)
        // TC03: Ray is included in the plane
        result = plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1, 1, 0)));
        assertNull(result, "Should return 0 points");

        // TC04: Ray is not included in the plane
        result = plane.findIntersections(new Ray(new Point(0,0,2), new Vector(1, 1, 0)));
        assertNull(result, "Should return 0 points");

        // Group: ray is orthogonal to the plane
        // TC05: P0 is before the plane (1 point)
        result = plane.findIntersections(new Ray(new Point(1,0,0), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(1,0,1), result.get(0), "Unexpected point");
        // TC06: P0 is on the plane (0 points)
        result = plane.findIntersections(new Ray(new Point(1,0,1), new Vector(0, 0, 1)));
        assertNull(result, "Should return 0 points");
        // TC07: P0 is after the plane
        result = plane.findIntersections(new Ray(new Point(1,0,2), new Vector(0, 0, 1)));
        assertNull(result, "Should return 0 points");

        // Group: special cases
        // TC08: Ray is neither orthogonal nor parallel to and begins at the plane
        result = plane.findIntersections(new Ray(new Point(1,0,1), new Vector(1, 1, 1)));
        assertNull(result, "Should return 0 points");

        // TC09: Ray begins in the plane's reference point, not parallel or orthogonal
        result = plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1, 1, 1)));
        assertNull(result, "Should return 0 points");
    }
}
