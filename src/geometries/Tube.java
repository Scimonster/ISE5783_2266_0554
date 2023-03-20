package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static primitives.Util.isZero;

/**
 * Tube to extend radial geometry (cylinder with unlimited height)
 */
public class Tube extends RadialGeometry {
    final Ray axisRay;

    /**
     * constructs the tube
     * @param radius double for radius
     * @param ray Ray for center ray
     */
    public Tube (double radius, Ray ray)
    {
        super(radius);
        this.axisRay=ray;
    }

    /**
     * returns the axis ray
     * @return the axisRay
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * method to get the normal of a tube from a specific point on the surface
     * @param point Point to get the normal based on
     * @return the normalized normal from the specific point
     */
    @Override
    public Vector getNormal(Point point)
    {
        Vector v1=point.subtract(this.axisRay.getP0());
        double t=this.axisRay.getDir().dotProduct(v1);
        if (isZero(t))
        {
            return v1.normalize();
        }
        Point O= this.axisRay.getP0().add(this.axisRay.getDir().scale(t));
        return point.subtract(O).normalize();
    }
}
