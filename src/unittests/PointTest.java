package unittests;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 */
class PointTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    /**
     * Test add method for {@link primitives.Point#add(primitives.Vector)}
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // plus positive vector
        Point p1 = new Point(1, 2, 3);
        Vector v2 = new Vector(0, 1, 2);
        Point p3 = new Point(1, 3, 5);
        assertEquals(p1.add(v2), p3, "points not equal: plus positive vector");

        // plus negative vector
        v2 = new Vector(0,-1,-2);
        p3 = new Point(1,1,1);
        assertEquals(p1.add(v2), p3, "points not equal: plus positive vector");


        // =============== Boundary Values Tests ==================

        // point plus its negative equivalent vector
        Point p4 = new Point(1, 2, 3);
        Vector v5 = new Vector(-1, -2, -3);
        assertEquals(p4.add(v5), new Point(0,0,0), "Point is not the origin");
    }

    /**
     * Test subtract method for {@link primitives.Point#subtract(primitives.Point)}
     */
    @Test
    void subtract() {
        //test subtract with point
        Point p1 =new Point(1,1,1);
        Point p2= new Point(2,2,2);
        Vector v3= new Vector(-1,-1,-1);

        assertEquals(p1.subtract(p2), v3, "Vectors not equivalent, point subtract");

        //prevent creation of 0 vector
        assertThrows(IllegalArgumentException.class, ()->p1.subtract(p1), "Zero vector created");
    }

    /**
     * Test distanceSquared method for {@link primitives.Point#distanceSquared(primitives.Point)}
     */
    @Test
    void distanceSquared() {
        //Equivalence Partition
        Point p1=new Point(4,4,4);
        Point p2=new Point (1,1,1);

        assertEquals(p1.distanceSquared(p2), 27, "Yields unexpected valued");

        //Boundary Value Test

        //Distance squared from origin
       p1= new Point (0,0,0);
       assertEquals(p1.distanceSquared(p2), new Vector(1,1,1).lengthSquared(), 0.000001, "Distancesquared should equal equivalent Vector lengthsquared");

    }

    /**
     * Test distance method for {@link primitives.Point#distance(primitives.Point)}
     */
    @Test
    void distance() {
        //test distance
        Point p1=new Point(1,4,0);
        Point p2=new Point (1,0,0);

        assertEquals(p1.distance(p2),4 , 0.000001, "Yields unexpected valued");


        //Boundary Test

        //Distance from origin
        p1= new Point (0,0,0);
        assertEquals(p1.distance(p2), new Vector(1,0,0).length(), 0.000001, "Distance should equal equivalent Vector length");
    }
}
