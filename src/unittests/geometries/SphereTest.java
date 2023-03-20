package unittests.geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Sphere;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test getNormal method for {@link geometries.Sphere#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests =======
        Point p1= new Point (0,0,0);
        Sphere s1= new Sphere(1,p1);

        Point p2=new Point (1,0,0);

        Vector v1=p2.subtract(p1).normalize();
        assertEquals(s1.getNormal(p2), v1, "Normal not calculated correctly");
    }
}