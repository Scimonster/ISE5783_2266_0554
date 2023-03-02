package geometries;

/**
 * A geometry with a radius
 */
public abstract class RadialGeometry extends Geometry {
    protected double radius;

    /**
     * constructs a radial geometry with the radius passed, fails if the double is less than 0
     * @param radius double
     */

    public RadialGeometry(double radius)
    {
        if (radius<0)
            throw new IllegalArgumentException("Radius must be greater than 0");

        this.radius=radius;
    }

    /**
     *
     * @return the radius
     */
    public double getRadius()
    {
        return this.radius;
    }

}
