package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

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
        this.point = p1;
        //calculation of get normal using the vectors created by the points and crossproduct between them
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        this.normal = v1.crossProduct(v2).normalize();

        this.lowBound=null;
        this.highBound=null;

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
        return this.normal;
    }

    /**
     * Get the plane's defining point
     * @return the point defined by the plane
     */
    public Point getPoint()
    {
        return point;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
        //if the start point of the ray is in the plane
        if(this.point.equals(ray.getP0()))
        {
            // don't include ray start as an intersection
            return null;
        }

        //if the ray is parallel to the plane, no intersection
        double nDotV = this.normal.dotProduct(ray.getDir());
        if (Util.isZero(nDotV))
        {
            return null;
        }

        //take dot product of the normal and the vector made by the point minus the ray origin, if it is greater than 0, use that as a scalar in get point to get the intersection
        //else there is no intersections
        double t = Util.alignZero(normal.dotProduct(point.subtract(ray.getP0())) / nDotV);
        if (t <= 0 || Util.alignZero(t-maxDistance)>0) {
            return null;
        }



        //floating point exception can still occur
        try {
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        catch (IllegalArgumentException n)
        {
            return List.of(new GeoPoint(this, ray.getP0()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Plane)) return false;
        Plane other = (Plane) obj;
        return super.equals(other) && this.point.equals(other.point) && this.normal.equals(other.normal);
    }
}
