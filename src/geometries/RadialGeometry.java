package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;

import java.util.List;

/**
 * A geometry with a radius
 */
public abstract class RadialGeometry extends Geometry {
    final double radius;

    /**
     * constructs a radial geometry with the radius passed, fails if the double is less than 0
     * @param radius double
     */

    public RadialGeometry(double radius)
    {
        if (radius<=0)
            throw new IllegalArgumentException("Radius must be greater than 0");

        this.radius=radius;
    }

    /**
     * returns the radius of the geometry
     * @return the radius
     */
    public double getRadius()
    {
        return this.radius;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this==obj)
            return true;
        if (obj==null)
            return false;
        if (!(obj instanceof RadialGeometry))
            return false;

        RadialGeometry other = (RadialGeometry) obj;

        return Util.isZero(this.radius-other.radius);
    }



}
