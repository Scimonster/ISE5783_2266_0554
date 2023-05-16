package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * class of sphere, 3d geometry that extends radialgeometry
 */
public class Sphere extends RadialGeometry{

    private final Point center;

    /**
     * constructs a sphere with the parameters passed
     *
     * @param point  Point for center
     * @param radius double for radius
     */
    public Sphere(Point point, double radius)
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
        double tm, d;
        if(this.center.equals(ray.getP0()))
        {
            tm = 0;
            d = 0;
        }
        else
        {
            //get vector that goes through the origin
            Vector u=this.getCenter().subtract(ray.getP0());
            //get the length of a side of the right triangle starting at ray origin, and considering vector u as the hypotenuse
            tm = Util.alignZero(ray.getDir().dotProduct(u));
            //get length of 3rd side of the right triangle, which is the distance from the origin to the ray
            d = Util.alignZero(Math.sqrt(u.lengthSquared() - tm*tm));
        }

        //if d is >= the radius of the sphere we have no intersections
        if(Util.alignZero(d-this.radius)>=0)
        {
            return null;
        }

        //now we can get half the length of the cord of the ray that appears within the sphere
        double th = Math.sqrt(this.radius*this.radius - d*d);

        //now let us calculate the distances from p0 along the ray that get intersections
        double t1 = Util.alignZero(tm +th);
        double t2 = Util.alignZero(tm-th);

        // make sure distances are within given distance range
        // if not, forcefully exclude it by being super negative
        if (Util.alignZero(t1-maxDistance)>0)
            t1 = Double.NEGATIVE_INFINITY;
        if(Util.alignZero(t1-maxDistance)>0)
            t2 = Double.NEGATIVE_INFINITY;



        //return the correct points based on the positivity of t1, t2
        if (t1>0 && t2>0)
        {
            return List.of(
                new GeoPoint(this, ray.getPoint(t1)),
                new GeoPoint(this, ray.getPoint(t2))
            );
        }
        else if (t1>0 && t2<=0)
        {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }
        else if (t2>0 && t1<=0) // this wouldn't happen naturally, but can when faking distance
        {
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }
        else
        {
          return null;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Sphere))
            return false;
        Sphere other = (Sphere) obj;
        return super.equals(other) && this.center.equals(other.center);
    }
}
