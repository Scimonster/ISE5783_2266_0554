package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * A generic Geometry object in our model
 */
public abstract class Geometry implements Intersectable {
    /**
     * Get the normal vector to the geometry at a given point
     * @param point Point to get the normal based on
     * @return Normal vector to the given point
     */
    abstract Vector getNormal(Point point);

    abstract public List<Point> findIntersections(Ray ray);
}
