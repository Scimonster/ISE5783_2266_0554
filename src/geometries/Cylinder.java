package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

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
        return super.getNormal(point);
    }
}
