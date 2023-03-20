package geometries;
import primitives.Point;
import primitives.Vector;

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

    @Override
    public Vector getNormal(Point point)
    {
        return point.subtract(this.center).normalize();
    }
}
