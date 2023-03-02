package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane extends Geometry{
    Point point;
    Vector normal;

    public Plane(Point p1, Point p2, Point p3)
    {
        this.point=p1;
        this.normal=null;
    }

    public Plane(Point point, Vector vector)
    {
        this.point=point;
        this.normal=vector;
    }


    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    public Vector getNormal()
    {
        return normal;
    }

    public Point getPoint()
    {
        return point;
    }
}
