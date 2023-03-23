package unittests.geometries;

import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Plane;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
}
