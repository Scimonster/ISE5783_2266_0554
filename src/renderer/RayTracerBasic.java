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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        GeoPoint closest = findClosestIntersection(ray);

        // if no intersection points
        if (closest == null)
        {
            return this.scene.background;
        }

        // calculate color of the point
        return this.calcColor(closest, ray);
    }

    /**
     * Find the closest intersection point on a ray
     * @param ray ray being traced
     * @return geopoint
     */
    private GeoPoint findClosestIntersection(Ray ray)
    {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        GeoPoint closest = ray.findClosestGeoPoint(points); // findClosestGeoPoint knows to handle null
        return closest;
    }

    /**
     * Calculate the color of a point in a scene.
     * @param gpt Point in scene (with its geometry)
     * @param ray the camera ray
     * @param level recursion level
     * @param k attenuation coefficient -- scaling down
     * @return color of the point
     */
    private Color calcColor(GeoPoint gpt, Ray ray, int level, Double3 k) {
        if (level <= 0)
            return Color.BLACK; // no additive property

        return calcLocalEffects(gpt, ray, k) // local lighting effects
                .add(calcGlobalEffects(gpt, ray, level, k)); // global reflection/refraction effects
    }

    /**
     * Calculate the color of a point in a scene
     * @param gp Point in scene (with its geometry)
     * @param ray the camera ray
     * @return color of the point after applying all effects
     */
    private Color calcColor(GeoPoint gp, Ray ray)
    {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambient.getIntensity());
    }

    /**
     * calculating local effects of lights on a point
     * @param gp the point (with its geometry)
     * @param ray the camera ray
     * @return the color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
            {
                Double3 ktr = transparency(gp, l, n, lightSource, nl);

                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {

                    // if (unshaded(gp , l, n, lightSource, nl))

                    // apply diffuse and specular effects
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
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

        if (intersections == null) return true;

        // only keep completely opaque geometries
        for (GeoPoint intersection: intersections) {
            // if kT==0, it is opaque, and hence casting shade, ie not unshaded
            if (intersection.geometry.getMaterial().kT.equals(Double3.ZERO))
                return false;
        }
        return true;
    }

    /**
     * Calculate global effects such as reflections and refractions
     * @param gp geopoint that was hit by ray
     * @param inRay ray coming in from camera or reflection/refraction
     * @param level recursion level
     * @param k attenuation coefficient
     * @return the composite color of all the effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray inRay, int level, Double3 k) {
        Color color = Color.BLACK; // no color
        Material mat = gp.geometry.getMaterial();

        // reflection
        Double3 kr = mat.kR, kkr = k.product(kr);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Vector n = gp.geometry.getNormal(gp.point);
            Ray reflectedRay = constructReflectedRay(n, gp.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }

        // refraction
        Double3 kt = mat.kT, kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(gp.point, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }

        return color;
    }


    /**
     * Construct reflected ray from inRay bouncing off point (with normal n)
     * @param n normal to the geometry at point
     * @param point point to bounce off
     * @param inRay ray coming in, to be reflected
     * @return reflected ray
     */
    private Ray constructReflectedRay(Vector n, Point point, Ray inRay)
    {
        double nl = n.dotProduct(inRay.getDir());
        Vector reflect = inRay.getDir().subtract(n.scale(2*nl));
        return new Ray(point, reflect);
    }

    /**
     * Refract from the point
     * We do not actually have any refraction, ray just continues in exact same direction
     * @param point intersection point
     * @param inRay incoming ray
     * @return "refracted" ray
     */
    private Ray constructRefractedRay(Point point, Ray inRay)
    {
        return new Ray(point, inRay.getDir());
    }

    /**
     * method that returns the transparency index for partial shadows
     * @param gp
     * @param l
     * @param n
     * @param light
     * @param nl
     * @return Double3 transparency
     */
    private Double3 transparency(GeoPoint gp , Vector l, Vector n, LightSource light, double nl)
    {
        Vector lightDirection = l.scale(-1); // from point to light source

        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);


        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));

        Double3 ktr=Double3.ONE;
        if (intersections == null) return ktr;


        // get the cumulative (multiplicative) transparency coefficient
        for (GeoPoint intersection: intersections) {
             ktr = ktr.product(intersection.geometry.getMaterial().kT);
        }

        return ktr;

    }
}
