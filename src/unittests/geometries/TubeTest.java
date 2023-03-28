package unittests.geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import geometries.Tube;

class TubeTest {

    /**
     * Test getNormal method for {@link geometries.Tube#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        Point p1= new Point (0,0,0);
        Vector v1= new Vector (1,0,0);
        Ray r1 =new Ray(p1,v1);
        Tube t1= new Tube(1d,r1);

        // ============ Equivalence Partitions Tests =======
        // TC01: point on the tube
        Point p2= new Point (1,1,0);
        assertEquals(t1.getNormal(p2), new Vector(0,1,0), "Normal incorrectly calculated" );

        // =============== Boundary Values Tests ==================
        // TC01: point is tube center
        p2= new Point (0,1, 0);

        assertEquals(t1.getNormal(p2), new Vector(0,1,0), "Normal incorrectly calculated");

    }


    /**
     * Test findIntersections method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    void findIntersections()
    {
        fail("not yet implemented");
    }
}
