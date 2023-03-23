package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class of sphere, 3d geometry that extends radialgeometry
 */
public class Sphere extends RadialGeometry{

    private final Point center;

    /**
     * constructs a sphere with the parameters passed
     * @param radius double for radius
     * @param point Point for center
     */
    public Sphere(double radius, Point point)
    {
        super(radius);
        this.center = point;
    }

    /**
     * Get the sphere's center origin point
     * @return the center of the sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * method to get the normal of a sphere from a specific point on the surface
     * @param point Point to get the normal based on
     * @return the normalized normal vector
     */
    @Override
    public Vector getNormal(Point point)
    {
        //the normal is the normalized vector created with point-center
        return point.subtract(this.center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray)
    {
        return null;
    }
}
