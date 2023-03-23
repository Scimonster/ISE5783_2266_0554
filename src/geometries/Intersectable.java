package geometries;
import primitives.*;

import java.util.List;

/**
 * interface for objects that can be intersected by a ray
 */

public interface Intersectable {


    /**
     * function that finds the points intersected by the ray on a geometry
     * @param ray the ray being shot
     * @return a list of points
     */
    List<Point> findIntersections(Ray ray);

}
