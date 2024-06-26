package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Base class for ray tracing
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * Init a ray tracer for a scene
     * @param scene the scene
     */
    public RayTracerBase(Scene scene)
    {
        this.scene = scene;
    }

    /**
     * Trace ray from the given ray through the scene and return the final color that should be found
     * @param ray ray from camera to scene
     * @return color of intercepted object(s)
     */
    public abstract Color traceRay(Ray ray);
}
