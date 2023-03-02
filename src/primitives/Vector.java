package primitives;

/**
 * class of a vector, inherits from point. Start at origin and end at the point
 */
public class Vector extends Point {

    /**
     *
     * @param point of Double3 a tuple of Doubles
     * @throws IllegalArgumentException
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
     * Create a vector from triplet of points
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x,y,z));
    }

    /**
     * Add two vectors
     * @param other the vector to add
     * @return new vector representing sum of this vector and the other
     */
    public Vector add(Vector other) {
        return new Vector(this.xyz.add(other.xyz));
    }

    /**
     *
     * @param c scalar
     * @return new vector multiplied by scalar
     */
    public Vector scale(double c) {
        return new Vector(this.xyz.scale(c));
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
}
