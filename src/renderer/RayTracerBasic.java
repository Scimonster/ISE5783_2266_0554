package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

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
        return null;
    }
}
