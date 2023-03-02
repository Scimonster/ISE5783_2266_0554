package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * A generic Geometry object in our model
 */
public abstract class Geometry {
    /**
     * Get the normal vector to the geometry at a given point
     * @param point Point to get the normal based on
     * @return Normal vector to the given point
     */
    abstract Vector getNormal(Point point);
}
