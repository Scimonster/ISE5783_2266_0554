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

    @Override
    public Vector getNormal(Point point) {

        //if the point passed is the origin, just return the negative scaled axisRay vector
        if(point.equals(this.axisRay.getP0()))
        {
            return this.axisRay.getDir().scale(-1);
        }

        Vector v1=point.subtract(this.axisRay.getP0());

        //if v1 is orthogonal, we know that point is on the base of the origin
        if(isZero(v1.dotProduct(this.axisRay.getDir())))
        {
            return this.axisRay.getDir().scale(-1);
        }

        Point p1= this.axisRay.getP0().add(this.axisRay.getDir().scale(this.height));

        //if point is equivalent to p1, we just return the vector
        if (p1.equals(point))
        {
            return this.axisRay.getDir();
        }

        v1=point.subtract(p1);

        //if v1 is orthogonal now, we know it is on the other base
        if(isZero(v1.dotProduct(this.axisRay.getDir())))
        {
            return this.axisRay.getDir();
        }

        //now we just use the super class
        return super.getNormal(point);
    }
}
