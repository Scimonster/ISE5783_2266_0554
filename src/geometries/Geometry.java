package geometries;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * A generic Geometry object in our model
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    /**
     * Get the normal vector to the geometry at a given point
     * @param point Point to get the normal based on
     * @return Normal vector to the given point
     */
    abstract public Vector getNormal(Point point);

    @Override
    abstract public List<Point> findIntersections(Ray ray);

    /**
     * method that returns the emission color
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * method to set emission color
     * @param emit
     * @return the object itself
     */

    public Geometry setEmission(Color emit)
    {
        if (emit == null)
            throw new IllegalArgumentException("can't give a null color");
        this.emission=emit;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Geometry)) return false;
        Geometry other = (Geometry) obj;
        return this.emission.equals(other.emission);
        // overriding classes will check further
    }
}
