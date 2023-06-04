package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * An infinite ray beginning from a point, in a given vector's direction
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    /**
     * Create a ray from point in the direction of vector
     * @param point ray head
     * @param vector ray direction
     */
    public Ray(Point point, Vector vector)
    {
        this.p0=point;
        this.dir=vector.normalize();
    }

    /**
     * Get the origin point of the ray
     * @return origin point
     */
    public Point getP0() {return this.p0;}

    /**
     * Get the direction vector of the ray.
     * Direction vector will be normalized.
     * @return direction vector
     */
    public Vector getDir() {return this.dir;}

    @Override
    public String toString()
    {
        return "Ray: ["+ this.p0.toString()+" " + this.dir.toString() + "]";
    }

    /**
     * returns whether two rays are the same
     * @param obj the object compared
     * @return true if the object is a ray and has the same attributes
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return this.p0.equals(other.p0)&& this.dir.equals(other.dir);

    }

    /**
     * function that gets a point on the ray
     * @param t a double to scale the vector
     * @return point
     */
    public Point getPoint(double t) throws IllegalArgumentException
    {
        double _t=Util.alignZero(t);
        //if the double passed is negative, throw an exception, we cannot go in opposite direction of this.dir
        if (_t<0) {
            throw new IllegalArgumentException("ERROR, a ray is unidirectional");
        }
        //if the double passed is 0, just return p0
        else if (_t==0) {
            return this.p0;
        }
        else {
            try {
                return this.p0.add(this.dir.scale(_t));
            } catch (IllegalArgumentException n)
            {
                return this.p0;
            }
        }
    }

    /**
     * method to find the closest point on the ray to p0
     * @param points list of GeoPoints
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points)
    {
        //null list or empty list
       if (points==null||points.size()==0)
       {
           return null;
       }

       double distance = Double.POSITIVE_INFINITY;
       double cur;
       GeoPoint close=null;

       for (GeoPoint gpt : points)
       {
           if (gpt==null)
               continue;

           //check if we have the same point as p0
           if (this.p0.equals(gpt.point))
               return gpt;

           cur=this.p0.distance(gpt.point);

           if (cur<distance)
           {
               distance=cur;
               close=gpt;
           }
       }

       return close;

    }

    /**
     * method to find the closest point on the ray to p0
     * @param points list of points
     * @return the closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * method that returns the distance between p0 and the point passed
     * @param point
     * @return double distance
     */
    public double findDistance(Point point)
    {
        return this.p0.distance(point);
    }
}
