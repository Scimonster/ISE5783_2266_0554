package unittests.geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Cylinder;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test getNormal method for {@link geometries.Cylinder#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        Ray axis= new Ray(new Point (0,0 ,0), new Vector(1,0,0));
        Cylinder cur= new Cylinder(2d, axis, 2);

        // ============ Equivalence Partitions Tests ==============
        //TC01: point on the round surface
        Point p1=new Point(1,2,0);
        assertEquals(cur.getNormal(p1), new Vector(0,1,0), "Normal incorrectly calculated");

        //TC02: point on surface of origin
        p1=new Point(0,0,1);
        assertEquals(cur.getNormal(p1),new Vector (-1,0,0), "Normal incorrectly calculated");

        //TC03: point on surface of opposite origin
        p1=new Point(2,0,1);
        assertEquals(cur.getNormal(p1),new Vector (1,0,0), "Normal incorrectly calculated");


        // =============== Boundary Values Tests ==================
        //TC04: the point is the origin
        p1=new Point(0,0,0);
        assertEquals(cur.getNormal(p1),new Vector (-1,0,0), "Normal incorrectly calculated");

        //TC05: the point is opposite the origin
        p1=new Point(2,0,0);
        assertEquals(cur.getNormal(p1),new Vector (1,0,0), "Normal incorrectly calculated");

        //TC06: point is on the base edge of the origin, we expect the base normal
        p1= new Point(0,2,0);
        assertEquals(cur.getNormal(p1),new Vector (-1,0,0), "Normal incorrectly calculated");

        //TC07: point is on the base edge opposite the origin
        p1=new Point(2,2,0);
        assertEquals(cur.getNormal(p1),new Vector (1,0,0), "Normal incorrectly calculated");

    }

    /**
     * Test findIntersections method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersections()
    {
        fail("not yet implemented");
    }
}
