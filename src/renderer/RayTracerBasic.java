package renderer;

import lighting.LightSource;
import primitives.*;
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
        return this.calcColor(closest, ray);
    }

    /**
     * Calculate the color of a point in a scene.
     * Currently: just ambient lighting
     * @param gpt Point in scene (with its geometry)
     * @return color of the point
     */
    private Color calcColor(GeoPoint gpt, Ray ray) {
        return scene.ambient.getIntensity().add(calcLocalEffects(gpt, ray));

    }

    /**
     * calculating local effects of lights
     * @param gp
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();

        Vector v = ray.getDir ();

        Vector n = gp.geometry.getNormal(gp.point);

        double nv = Util.alignZero(n.dotProduct(v));

        if (nv == 0)
            return color;

        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights)
        {
            Vector l = lightSource.getL(gp.point);

            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0)
            { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(mat, nl)),
                        iL.scale(calcSpecular(mat, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * helper function that calculates Diffusive attribute
     * @param mat
     * @param nl
     * @return
     */
    private Double3 calcDiffusive(Material mat, double nl)
    {
        return mat.kD.scale(Math.abs(nl));
    }

    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v)
    {
        Vector reflect=l.subtract(n.scale(2*nl));

        double spec = Math.pow(Math.max(0, v.scale(-1).dotProduct(reflect)), mat.nShininess);

        return mat.kS.scale(spec);
    }


}
