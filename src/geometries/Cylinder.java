package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * extends tube, puts a limit on its height
 */
public class Cylinder extends Tube {

    private final double height;

    /**
     * constructs a tube
     * @param radius double for radius
     * @param ray Ray for axisRay
     * @param height double for height
     */
    public Cylinder(double radius, Ray ray, double height)
    {
        super(radius, ray);
        if (height<=0)
            throw new IllegalArgumentException(("Height cannot be less than 0"));
        this.height=height;

    }

    /**
     * to return the height
     * @return height
     */
    public double getHeight()
    {
        return this.height;
    }

    /**
     * Return the normal to the cylinder in a given point
     * If it's on one of the bases, return the normal to the base
     * If it's on the round tube, return the normal to the tube
     * If it's on the edge, we force it to be on the base
     * @param point Point on the tube
     * @return normal vector
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = this.axisRay.getDir();
        Point p0 = this.axisRay.getP0();
        Point p1 = this.axisRay.getPoint(this.height);

        //if the point passed is on the base of the origin, return the negative scaled axisRay vector
        if(point.equals(p0) || isZero(point.subtract(p0).dotProduct(v)))
        {
            return v.scale(-1);
        }

        //if point is on the opposite base, return the axisRay vector
        if (p1.equals(point) || isZero(point.subtract(p1).dotProduct(v)))
        {
            return v;
        }

        // on the tube, just use the super class
        return super.getNormal(point);
    }

    /**
     * find intersections of a ray with the cylinder
     * Avi Parshan
     * @param ray         - the ray to intersect with
     * @param maxDistance - upper bound of distance from ray head to intersection point
     * @return list of the intersection points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //https://mrl.cs.nyu.edu/~dzorin/rendering/lectures/lecture3/lecture3.pdf
        Ray axis = getAxisRay();
        Vector va = axis.getDir();
        Point p1 = axis.getP0();
        Point p2 = p1.add(va.scale(height));

        double radSquared = radius * radius;
        List<GeoPoint> result = new LinkedList<>();
        rayIntersectsPlaneChecker(ray, maxDistance, radSquared, initListIfNull(result), va, p1); //get intersections with bottom plane

        List<GeoPoint> res = super.findGeoIntersectionsHelper(ray, maxDistance); //intersect with tube (of cylinder)

        if (!isNullOrEmpty(res)) { //if list is not empty
            for (GeoPoint gp : res) {   //add all intersections of tube that are in the cylinder's bounders
                //bounds check below
                if (alignZero(va.dotProduct(gp.point.subtract(p1))) > 0 && alignZero(va.dotProduct(gp.point.subtract(p2))) < 0) {
                    result.add(new GeoPoint(this, gp.point)); //ensure to add as cylinder geo and not tube
                }
            }
        }

        rayIntersectsPlaneChecker(ray, maxDistance, radSquared, initListIfNull(result), va, p2); //get intersections with top plane

        return (!isNullOrEmpty(result)) ? result : null; //if at least 1 intersection, return the list
    }

    /**
     * Checks if ray intersects with a base (top or bottom) and if so, ensures it is within the cylinder's bounds, then add it to the results
     *
     * @param ray        - the ray to intersect with
     * @param maxDistance - upper bound of distance from ray head to intersection point
     * @param radSquared - radius squared
     * @param result    - list of the intersection points
     * @param va       - vector of the axis
     * @param p1      - point on the axis
     */
    private void rayIntersectsPlaneChecker(Ray ray, double maxDistance, double radSquared, List<GeoPoint> result, Vector va, Point p1) {
        Plane plane = new Plane(p1, va); //get plane of either base
        List<GeoPoint> res = plane.findGeoIntersections(ray, maxDistance); //intersections with cap - plane
        if (!isNullOrEmpty(res)) {
            //Add all intersections of plane that are in the base's bounders
            for (GeoPoint point : res) {
                if (point.point.equals(p1)) { //avoid creating zero vector
                    result.add(new GeoPoint(this, point.point)); //ensure to add as cylinder geo and not plane
                } else if ((point.point.subtract(p1).dotProduct(point.point.subtract(p1)) < radSquared)) { //checks that point is inside the base
                    result.add(new GeoPoint(this, point.point));//ensure to add as cylinder geo and not plane
                }
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Cylinder)) return false;
        Cylinder other = (Cylinder) obj;
        return super.equals(other) && isZero(this.height - other.height);
    }
}
