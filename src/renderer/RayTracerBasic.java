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

    private static final double DELTA = 0.1;

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
     * @param ray the camera ray
     * @return color of the point
     */
    private Color calcColor(GeoPoint gpt, Ray ray) {
        return scene.ambient.getIntensity().add(calcLocalEffects(gpt, ray));
    }

    /**
     * calculating local effects of lights on a point
     * @param gp the point (with its geometry)
     * @param ray the camera ray
     * @return the color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        // start with emission light of the geometry
        Color color = gp.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);

        double nv = Util.alignZero(n.dotProduct(v));
        // looking perpendicular to the geometry, won't see any lighting effects
        if (nv == 0)
            return color;

        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights)
        {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));
            // make sure light and camera are hitting the geometry from the same side
            if (Util.checkSign(nl, nv))
            {    if (unshaded(gp , l, n, lightSource, nl))
                {
                    // apply diffuse and specular effects
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(mat, nl)),
                            iL.scale(calcSpecular(mat, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * helper function that calculates diffusive attribute
     * @param mat material
     * @param nl dot product of geometry normal and light vector
     * @return diffusive coefficient
     */
    private Double3 calcDiffusive(Material mat, double nl)
    {
        return mat.kD.scale(Math.abs(nl));
    }

    /**
     * helper function that calculates specular attribute
     * @param mat material
     * @param n geometry normal
     * @param l light vector
     * @param nl n dot l
     * @param v camera ray
     * @return specular coefficient
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl, Vector v)
    {
        Vector reflect=l.subtract(n.scale(2*nl));

        double spec = Math.pow(Math.max(0, v.scale(-1).dotProduct(reflect)), mat.nShininess);

        return mat.kS.scale(spec);
    }

    /**
     * function that returns true whether a point is unshaded
     * @param gp the point
     * @param l the vector from the lightsource
     * @param n the normal vector from that point
     * @param light
     * @param nl
     * @return if the point should be unshaded (effected by the light source) (boolean)
     */
    private boolean unshaded(GeoPoint gp , Vector l, Vector n, LightSource light, double nl)
    {
        Vector lightDirection = l.scale(-1); // from point to light source

        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);


        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));

        return intersections==null;
    }

}
