package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.List;

/**
 * Basic ray tracer
 */
public class RayTracerBasic extends RayTracerBase {
    /**
     * Init a ray tracer
     * @param scene the scene to draw
     */
    public RayTracerBasic(Scene scene)
    {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray)
    {
        List<Point> points = scene.geometries.findIntersections(ray);
        Point closest = ray.findClosestPoint(points); // findClosestPoints knows to handle null

        // if no intersection points
        if (closest == null)
        {
            return this.scene.background;
        }

        // calculate color of the point
        return this.calcColor(closest);
    }

    /**
     * Calculate the color of a point in a scene.
     * Currently: just ambient lighting
     * @param point Point in scene
     * @return color of the point
     */
    private Color calcColor(Point point) {
        return scene.ambient.getIntensity();
    }
}
