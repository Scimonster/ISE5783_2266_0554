package geometries;

public abstract class RadialGeometry extends Geometry {
    protected double radius;

    RadialGeometry(double radius)
    {
        this.radius=radius;
    }

}
