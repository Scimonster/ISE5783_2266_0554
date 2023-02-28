package test.primitives;

import test.primitives.Double3;

public class Point {
    protected Double3 xyz;

    Point(Double3 point)
    {
        this.xyz=point;
    }
    public Point(double x, double y, double z)
    {
        this(new Double3(x,y,z));
    }

    public Vector subtract(Point other)
    {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    public Point add(Vector other)
    {
        return new Point(this.xyz.add(other.xyz));
    }

    public double distanceSqaured(Point other)
    {
         Double3 delta=this.xyz.subtract(other.xyz);
         return delta.d1*delta.d1+delta.d2*delta.d2+delta.d3*delta.d3;
    }

    public double distance(Point other)
    {
        return Math.sqrt(this.distanceSqaured(other));
    }

}
