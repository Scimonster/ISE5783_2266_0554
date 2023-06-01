package primitives;

import java.util.List;

/**
 * Class of a vector, inherits from point.
 * Vectors start at the origin and end at the point.
 */
public class Vector extends Point {

    /**
     * Internal constructor for a triplet of doubles
     * @param point triplet of doubles
     * @throws IllegalArgumentException if vector is zero vector
     */
    Vector(Double3 point) throws IllegalArgumentException
    {
        super(point);
        // don't allow creating 0-vector
        if (point.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Cannot create zero vector");
        }
    }

    /**
     * creates a vector
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     */
     public Vector(double x, double y, double z)
     {
         this(new Double3(x,y,z));
     }


    /**
     *
     * @param other The point to add
     * @return a vector that is the sum of the 2 vectors
     */
    public Vector add(Vector other) {

        return new Vector(this.xyz.add(other.xyz));
    }

    /**
     * Scale a vector by a scalar
     * @param c scalar
     * @return new vector multiplied by scalar
     */
    public Vector scale(double c) {
        double _c=Util.alignZero(c);
        if(_c==0)
            throw new IllegalArgumentException("Cannot scale by zero");
        return new Vector(this.xyz.scale(_c));
    }

    /**
     * Dot product between two vectors
     * @param other other vector
     * @return dot product
     */
    public double dotProduct(Vector other) {
        return this.xyz.d1*other.xyz.d1 + this.xyz.d2*other.xyz.d2 + this.xyz.d3*other.xyz.d3;
    }

    /**
     * Cross product between two vectors, return new vector orthogonal to both
     * @param that other vector
     * @return cross product
     */
    public Vector crossProduct(Vector that) {
        Double3 p1 = this.xyz, p2 = that.xyz;
        return new Vector(
                p1.d2 * p2.d3 - p1.d3 * p2.d2,
                p1.d3 * p2.d1 - p1.d1 * p2.d3,
                p1.d1 * p2.d2 - p1.d2 * p2.d1
        );
    }

    /**
     * Length of the vector squared (dot product with itself)
     * @return length squared
     */
    public double lengthSquared() {
        // length^2 is dot product with itself
        return this.dotProduct(this);
    }

    /**
     * Length of the vector
     * @return length
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalize the vector to length 1
     * @return new normalized vector
     */
    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }

    @Override
    public String toString() {
        return "Vector: " + this.xyz.toString();
    }
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Vector && super.equals(obj));
    }

    /**
     * Generate two orthogonal vectors to the vector, eg for using as an axis definition
     * @return list of two orthonormal vectors
     */
    public List<Vector> getOrthogonalVectors()
    {
        Vector v0 = this.normalize();
        Vector v1, v2;
        // arbitrarily pick a field to set to zero
        if (Util.isZero(v0.getX())) {
            // take an arbitrary orthogonal vector
            v1 = new Vector(1, 0, 0);
        } else {
            // take a non-parallel vector, and get an orthogonal vector to the original
            v1 = new Vector(0, 1, 0).crossProduct(v0).normalize();
        }
        v2 = v0.crossProduct(v1);
        return List.of(v1, v2);
    }
}
