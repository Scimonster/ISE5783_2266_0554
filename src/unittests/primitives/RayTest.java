package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test getPoint method for {@link primitives.Ray#getPoint(double)}
     */
    @Test
    public void testGetPoint()
    {
        Ray ray= new Ray (new Point (0,1,0), new Vector(1,0,0) );
        // ============ Equivalence Partitions Tests ==============
        //TC01: positive scale
        assertEquals(ray.getPoint(1), new Point(1,1,0), "Returned wrong point");

        //TC02: negative scale, should throw an exception
        assertThrows(IllegalArgumentException.class, () -> ray.getPoint(-1d), "getPoint() should throw a exception for a negative scale");


        // =============== Boundary Values Tests ==================
        //TC03: zero scale
        assertEquals(ray.getPoint(0), ray.getP0(), "Returned wrong point");


    }

    /**
     * Test getPoint method for {@link primitives.Ray#findClosestPoint(List)}
     */

    @Test
    void testFindClosetPoint() {

        Point p0=new Point (0,0,1), p1=new Point(0,0,2), p2 = new Point(0,0,3), p3= new Point (0,0,4);

        Ray ray = new Ray(new Point(0,0,1), new Vector(0,0,1));
        // ============ Equivalence Partitions Tests ==============
        //TCO1, closet point is somewhere in the middle of the list
        assertEquals(p1, ray.findClosestPoint(List.of(p2, p1, p3)), "ERROR, returned wrong point");

        // =============== Boundary Values Tests ==================
        //TC02, empty list
        assertNull(ray.findClosestPoint(new ArrayList<Point>()), "ERROR, should not return a point");

        //TC03, null list
        assertNull(ray.findClosestPoint(null), "ERROR, should not return a point");

        //TC04, closest point is at beginning of list
        assertEquals(p1, ray.findClosestPoint(List.of(p1, p2, p3)), "ERROR, returned wrong point");

        //TC05, closest point is at the end of the list
        assertEquals(p1, ray.findClosestPoint(List.of(p2, p3, p1)), "ERROR, returned wrong point");

        //TC06, closest point is equivalent to ray start
        assertEquals(p0, ray.findClosestPoint(List.of(p2, p0, p3)), "ERROR, returned wrong point");

    }
}