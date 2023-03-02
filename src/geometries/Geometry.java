package geometries;
import primitives.Point;
import primitives.Vector;
public abstract class Geometry {
    abstract Vector getNormal(Point point);
}
