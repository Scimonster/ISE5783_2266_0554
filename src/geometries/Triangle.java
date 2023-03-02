package geometries;
import primitives.Point;

import java.util.List;

/**
 * class of triangle, contains 3 points
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

}
