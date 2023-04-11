package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
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
}
