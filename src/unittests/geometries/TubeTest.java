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
        Tube t1= new Tube(1,r1);

        // ============ Equivalence Partitions Tests =======
        Point p2= new Point (1,1,0);
        assertEquals(t1.getNormal(p2), new Vector(0,1,0), "Normal incorrectly calculated" );

        // =============== Boundary Values Tests ==================
        p2= new Point (0,1, 0);

        assertEquals(t1.getNormal(p2), new Vector(0,1,0), "Normal incorrectly calculated");

    }
}