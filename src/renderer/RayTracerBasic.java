package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.List;
import geometries.Intersectable.GeoPoint;


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
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        GeoPoint closest = ray.findClosestGeoPoint(points); // findClosestGeoPoint knows to handle null

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
     * @param gpt Point in scene (with its geometry)
     * @return color of the point
     */
    private Color calcColor(GeoPoint gpt) {
        return scene.ambient.getIntensity().add(gpt.geometry.getEmission());
    }
}
