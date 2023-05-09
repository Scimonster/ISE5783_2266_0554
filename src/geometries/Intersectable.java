package geometries;
import primitives.*;

import java.util.List;

/**
 * interface for objects that can be intersected by a ray
 */

public abstract class Intersectable {

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

    public abstract List<Point> findIntersections(Ray ray);

    /**
     * method to find GeoIntersections
     * @param ray
     * @return a list of geoIntersections
     */
    public List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return this.findGeoIntersectionsHelper(ray);
    }

    /**
     * helper method for intersections
     * @param ray
     * @return
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
