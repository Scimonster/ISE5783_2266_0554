package geometries;
import primitives.*;


import java.util.List;

/**
 * A flat triangle shape
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the 3 points passed as parameters
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     */
    public Triangle(Point p1, Point p2, Point p3)
    {
        super(p1,p2,p3);
    }

    /**
     * getter for the points
     * @return returns vertices
     */
    public List<Point> getPoints()
    {
        return this.vertices;
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        List<GeoPoint> res= this.plane.findGeoIntersections(ray);

        if(res==null) {
            return null;
        }

        // test if intersection point is inside the triangle
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        // test if p is any of the triangle vertices
        if (p0.equals(this.vertices.get(0)) || p0.equals(this.vertices.get(1)) || p0.equals(this.vertices.get(2))) {
            return null;
        }
        Vector  v1 = this.vertices.get(0).subtract(p0),
                v2 = this.vertices.get(1).subtract(p0),
                v3 = this.vertices.get(2).subtract(p0);
        Vector  n1 = v1.crossProduct(v2).normalize(),
                n2 = v2.crossProduct(v3).normalize(),
                n3 = v3.crossProduct(v1).normalize();
        double  dot1 = Util.alignZero(v.dotProduct(n1)),
                dot2 = Util.alignZero(v.dotProduct(n2)),
                dot3 = Util.alignZero(v.dotProduct(n3));
        if ( Util.checkSign(dot1, dot2)&& Util.checkSign(dot2, dot3))
        {
            // all the dot products have same sign -- point is inside triangle
            return List.of(new GeoPoint(this, res.get(0).point));
        }
        return null;
   }
   
}
