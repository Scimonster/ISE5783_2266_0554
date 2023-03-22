package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * A geometric plane.
 * We represent the plane using point-normal form.
 */
public class Plane extends Geometry{
    private final Point point;
    private final Vector normal;

    /**
     * Create a plane from three (non-colinear) points
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Plane(Point p1, Point p2, Point p3)
    {
        this.point=p1;
        this.normal=null;
    }

    /**
     * Create a plane from a point and orthogonal vector
     * @param point point on plane
     * @param vector vector orthogonal to plane
     */
    public Plane(Point point, Vector vector)
    {
        this.point = point;
        this.normal = vector.normalize();
    }


    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    /**
     * Get the normal vector
     * @return the normal vector
     */
    public Vector getNormal()
    {
        return null;
    }

    /**
     * Get the plane's defining point
     * @return the point defined by the plane
     */
    public Point getPoint()
    {
        return point;
    }
}
