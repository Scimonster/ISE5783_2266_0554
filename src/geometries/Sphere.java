package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * class of sphere, 3d geometry that extends radialgeometry
 */
public class Sphere extends RadialGeometry{

    private Point Center;

    /**
     * constructs a sphere with the parameters passed
     * @param radius double for radius
     * @param point Point for center
     */
    public Sphere(Double radius, Point point)
    {
        super(radius);
        this.Center=point;
    }

    /**
     *
     * @return the center of the sphere
     */
    public Point getCenter() {
        return Center;
    }

    @Override
    public Vector getNormal(Point point)
    {
        return null;
    }
}
