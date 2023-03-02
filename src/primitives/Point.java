package primitives;

/**
 * A point in the 3d plane
 */
public class Point {
    protected Double3 xyz;

    Point(Double3 point)
    {
        this.xyz=point;
    }

    /**
     * Create a point from 3 coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     */
    public Point(double x, double y, double z)
    {
        this.xyz=new Double3(x,y,z);
    }

    /**
     * Subtract two points (vector subtraction)
     * @param other The point to subtract from this point
     * @return A vector representing the difference between points
     */
    public Vector subtract(Point other)
    {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * Add two points (vector addition, head-to-tail)
     * @param other The point to add
     * @return A new point at the tail of the sum vector
     */
    public Point add(Vector other)
    {
        return new Point(this.xyz.add(other.xyz));
    }

    /**
     * Distance between two points (squared)
     * @param other Point to get distance from
     * @return distance squared
     */
    public double distanceSquared(Point other)
    {
         Double3 delta=this.xyz.subtract(other.xyz);
         return delta.d1*delta.d1 + delta.d2*delta.d2 + delta.d3*delta.d3;
    }

    /**
     * Distance between two points (Pythagorean formula)
     * @param other Point to get distance from
     * @return distance
     */
    public double distance(Point other)
    {
        return Math.sqrt(this.distanceSquared(other));
    }

    @Override
    public String toString() {
        return "Point: " + this.xyz.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point)obj;
        return this.xyz.equals(other.xyz);
    }

}
