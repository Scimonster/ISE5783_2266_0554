package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
import static primitives.Util.checkSign;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTest {

    @Test
    void zeroVector() {
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "zero vector was created");
    }

    /**
     * Test add method for {@link primitives.Vector#add(primitives.Vector)}
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: positive plus positive
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 1, 2);
        Vector v3 = new Vector(1, 3, 5);
        assertEquals(v1.add(v2), v3, "vectors not equal: positive plus positive");

        // TC02: positive plus negative
        v2 = new Vector(0,-1,-2);
        v3 = new Vector(1,1,1);
        assertEquals(v1.add(v2), v3, "vectors not equal: positive plus negative");

        // TC03: negative plus negative
        v1 = new Vector(-1, -1, -1);
        v2 = new Vector(0,-1,-2);
        v3 = new Vector(-1,-2,-3);
        assertEquals(v1.add(v2), v3, "vectors not equal: negative plus negative");

        // =============== Boundary Values Tests ==================
        // TC04: vector plus itself
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(2, 4, 6);
        assertEquals(v1.add(v1), v2, "vector plus itself not working");

        // TC05: vector plus its negative
        final Vector v4 = new Vector(1, 2, 3);
        final Vector v5 = new Vector(-1, -2, -3);
        assertThrows(IllegalArgumentException.class, () -> v4.add(v5), "vector plus its negative should throw");
    }

    /**
     * Test subtract method for primitives.Vector#subtract(primitives.Vector)
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: positive minus positive
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 1, 2);
        Vector v3 = new Vector(1, 3, 5);
        assertEquals(v3.subtract(v2), v1, "vectors not equal: positive minus positive");

        // TC02: positive minus negative
        v2 = new Vector(0,-1,-2);
        v3 = new Vector(1,1,1);
        assertEquals(v3.subtract(v2), v1, "vectors not equal: positive minus negative");

        // TC03: negative minus positive
        v1 = new Vector(-1, -1, -1);
        v2 = new Vector(0,1,2);
        v3 = new Vector(-1,-2,-3);
        assertEquals(v1.subtract(v2), v3, "vectors not equal: negative minus positive");

        // TC04: negative minus negative
        v1 = new Vector(-1, -1, -1);
        v2 = new Vector(0,-1,-2);
        v3 = new Vector(-1,-2,-3);
        assertEquals(v3.subtract(v2), v1, "vectors not equal: negative minus negative");

        // =============== Boundary Values Tests ==================
        // TC05: vector minus itself
        final Vector v4 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> v4.subtract(v4), "vector minus itself should throw");

        // TC06: vector minus its negative
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(-1, -2, -3);
        v3 = new Vector(2, 4, 6);
        assertEquals(v1.subtract(v2), v3, "vector minus its negative not working");
    }

    /**
     * Test scale method for {@link primitives.Vector#scale(double)}
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: scale positive integer
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 4, 6);
        assertEquals(v1.scale(2), v2, "scale by positive int not equal");

        // TC02: scale positive float
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(1.5, 3, 4.5);
        assertEquals(v1.scale(1.5), v2, "scale by positive float not equal");

        // TC03: scale negative integer
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(-2, -4, -6);
        assertEquals(v1.scale(-2), v2, "scale by negative int not equal");

        // TC04: scale negative float
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(-1.5, -3, -4.5);
        assertEquals(v1.scale(-1.5), v2, "scale by negative float not equal");

        // =============== Boundary Values Tests ==================
        // TC05: scale by 1
        v1 = new Vector(1, 2, 3);
        assertEquals(v1.scale(1), v1, "scale by 1 should give original vector");

        // TC06: scale by -1
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(-1, -2, -3);
        assertEquals(v1.scale(-1), v2, "scale by -1 should give inverse vector");

        // TC07: scale by 0
        assertThrows(IllegalArgumentException.class, () -> (new Vector(1,1,1)).scale(0), "scale by 0 should throw");
    }

    /**
     * Test dotProduct method for {@link primitives.Vector#dotProduct(primitives.Vector)}
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check that dot product works
        Vector v1 = new Vector (1,1,1);
        Vector v2 = new Vector (2,2,2);
        assertEquals(v1.dotProduct(v2), 6, 0.000001, "Dotproduct does not calculate correct dot product");

        // TC02: positive dot positive
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(0, 1, 2);
        assertTrue(checkSign(v1.dotProduct(v2),1), "positive dot positive should be positive");

        // TC03: positive dot negative
        v2 = new Vector(0,-1,-2);
        assertTrue(checkSign(v1.dotProduct(v2), -1), "positive dot negative should be negative");
        assertTrue(checkSign(v2.dotProduct(v1),-1), "negative dot positive should be negative");

        // TC04: negative dot negative
        v1 = new Vector(-1, -1, -1);
        assertTrue(checkSign(v1.dotProduct(v2),1), "negative dot negative should be positive");

        // =============== Boundary Values Tests ==================
        // TC05: vector dot orthogonal
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(0, 3, -2);
        assertTrue(isZero(v1.dotProduct(v2)), "orthogonal vectors should have dot product 0");

        // TC06: vector dot itself
        assertEquals(v1.dotProduct(v1), v1.lengthSquared(), 0.000001, "vector dot product itself should equal the length squared");
    }

    /**
     * Test crossProduct method for {@link primitives.Vector#crossProduct(primitives.Vector)}
     */
    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)),"crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC03: test zero vector from cross-product of co-linear vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3), "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test lengthSquared method for {@link primitives.Vector#lengthSquared()}
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: check that lengthSquared works
        Vector v1 = new Vector(1, 2, 3);
        assertTrue(isZero(v1.lengthSquared() - 14), "lengthSquared is wrong value");
    }

    /**
     * Test length method for {@link primitives.Vector#length()}
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: check that length works
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "length is wrong value");
    }

    /**
     * Test normalize method for {@link primitives.Vector#normalize()}
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        // TC01: check that normalized vector has length 1
        assertTrue(isZero(u.length() - 1), "normalized length should be 1");
        // TC02: check that normalized vector is parallel to original vector
        assertThrows(IllegalArgumentException.class, ()-> v.crossProduct(u), "Vectors are not parallel");
        // TC03: check that normalized vector is in the same direction as original vector
        assertTrue(v.dotProduct(u)>0, "Vectors are in opposite directions");
    }
}
