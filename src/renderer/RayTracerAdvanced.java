package renderer;

import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;

public class RayTracerAdvanced extends RayTracerBasic {

    /**
     * Init a ray tracer
     * @param scene the scene to draw
     */
    public RayTracerAdvanced(Scene scene)
    {
        super(scene);
    }


    @Override
    protected Color calcGlobalEffects(Intersectable.GeoPoint gp, Ray inRay, int level, Double3 k)
    {
        Color color = Color.BLACK; // no color
        Material mat = gp.geometry.getMaterial();

        // reflection
        Double3 kr = mat.kR, kkr = k.product(kr);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Vector n = gp.geometry.getNormal(gp.point);
            List<Ray> reflectedRays = constructReflectedRays(n, gp.point, inRay);

            for (Ray reflectedRay:reflectedRays) {
                Intersectable.GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
                if (reflectedPoint != null)
                    color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }

        // refraction
        Double3 kt = mat.kT, kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            List<Ray>  refractedRays = constructRefractedRays(gp.point, inRay);
            for(Ray refractedRay:refractedRays) {
                Intersectable.GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                if (refractedPoint != null)
                    color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }

        return color;
    }

    protected List<Ray> constructReflectedRays(Vector n, Point point, Ray inRay)
    {
        return List.of(constructReflectedRay(n,point,inRay));
    }

    protected List<Ray> constructRefractedRays(Point point, Ray inRay)
    {
        return List.of(constructRefractedRay(point, inRay));
    }




}
