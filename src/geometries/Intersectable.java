package geometries;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * interface for objects that can be intersected by a ray
 */

public abstract class Intersectable {

    //bottom left front corner of bounded box
    protected Point lowBound;

    //top right back corner of box
    protected Point highBound;

    /**
     * passive data structure to hold a Point with the Geometry at that point
     */
    public static class GeoPoint {

        /**
         * a Geometry
         */
        public Geometry geometry;

        /**
         * a Point
         */
        public Point point;

        /**
         * construct a geoPoint
         * @param geo
         * @param point
         */
        public GeoPoint(Geometry geo, Point point)
        {
            this.geometry=geo;
            this.point=point;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this==obj)
                return true;
            if (obj==null)
                return false;
            if (!(obj instanceof GeoPoint))
                return false;

            GeoPoint other = (GeoPoint)obj;
            return this.geometry== other.geometry && this.point==other.point;
        }

        @Override
        public String toString()
        {
            return "Geometry: " + System.identityHashCode(this.geometry) + ". Point: " + this.point.toString() + ".";
        }
    }


    /**
     * function that finds the points intersected by the ray on a geometry
     * @param ray the ray being shot
     * @return a list of points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * method to find GeoIntersections
     * @param ray
     * @return a list of geoIntersections
     */
    public List<GeoPoint> findGeoIntersections(Ray ray)
    {

            return this.findGeoIntersections(ray, Double.POSITIVE_INFINITY);

    }

    /**
     * helper method for intersections
     * @param ray
     * @return
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance)
    {
            if(this.intersectBoundedBox(ray)) {

                //check that there is a possibility of an intersection
                return this.findGeoIntersectionsHelper(ray, maxDistance);
            }
            else {
                return null;
            }

    }


    /**
     * function that will see if a ray intersects the bounded box
     * @param ray
     * @return
     */

    private boolean intersectBoundedBox(Ray ray)
    {


        //if there is no bounded box possibility, no early exit option
        if(this.lowBound==null||this.highBound==null)
        {
            return true;
        }
        //calculate the distance on the ray to each bounded axis plane
        double  t1x = (lowBound.getX() - ray.getP0().getX()) / ray.getDir().getX(),
                t1y = (lowBound.getY() - ray.getP0().getY()) / ray.getDir().getY(),
                t1z = (lowBound.getZ() - ray.getP0().getZ()) / ray.getDir().getZ(),
                t2x = (highBound.getX() - ray.getP0().getX()) / ray.getDir().getX(),
                t2y = (highBound.getY() - ray.getP0().getY()) / ray.getDir().getY(),
                t2z = (highBound.getZ() - ray.getP0().getZ()) / ray.getDir().getZ();

        double  tMaxX = Math.max(t1x, t2x),
                tMaxY = Math.max(t1y, t2y),
                tMaxZ = Math.max(t1z, t2z),
                tMinX = Math.min(t1x, t2x),
                tMinY = Math.min(t1y, t2y),
                tMinZ = Math.min(t1z, t2z);

        //get the maximum distance for the lowBound and highBound points
        double tNear=Math.max(tMinX,Math.max(tMinY,tMinZ));
        double tFar=Math.min(tMaxX,Math.min(tMaxY,tMaxZ));

        //if tNear is greater the tFar, missed box
        if(Util.alignZero(tNear-tFar)>0||Util.alignZero(tFar)<0) {
            return false;
        }

        return true;


    }


}
