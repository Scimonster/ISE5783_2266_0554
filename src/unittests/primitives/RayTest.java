package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

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
}