package unittests.geometries;

import geometries.Plane;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
    void findIntsersections()
    {
        fail("not yet implemented");
    }
}
