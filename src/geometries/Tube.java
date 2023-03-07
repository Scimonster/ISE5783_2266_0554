package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Tube to extend radial geometry (cylinder with unlimited height)
 */
public class Tube extends RadialGeometry {
    protected Ray axisRay;

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

    @Override
    public Vector getNormal(Point point)
    {
        return null;
    }
}
