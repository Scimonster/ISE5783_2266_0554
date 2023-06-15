package geometries;
import primitives.*;
import static primitives.Util.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube to extend radial geometry (cylinder with unlimited height)
 */
public class Tube extends RadialGeometry {
    final Ray axisRay;

    /**
     * constructs the tube
     * @param radius double for radius
     * @param ray Ray for center ray
     */
    public Tube (double radius, Ray ray)
    {
        super(radius);
        this.axisRay=ray;
    }

    /**
     * returns the axis ray
     * @return the axisRay
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * method to get the normal of a tube from a specific point on the surface
     * @param point Point to get the normal based on
     * @return the normalized normal from the specific point
     */
    @Override
    public Vector getNormal(Point point)
    {
        Vector v1=point.subtract(this.axisRay.getP0());

        double t=this.axisRay.getDir().dotProduct(v1);

        //if t is zero, v1 was already the normal since, it was already orthogonal
        if (isZero(t))
        {
            return v1.normalize();
        }

        //get the point on the axisRay that would produce the normal vector when subtracted from point
        Point O= this.axisRay.getP0().add(this.axisRay.getDir().scale(t));
        return point.subtract(O).normalize();
    }

    /**
     * Find intersections of a ray with the tube
     * Avi Parshan
     * @param ray         - the ray to intersect with
     * @param maxDistance - upper bound of distance from ray head to intersection point
     * @return a list of intersection points
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //https://mrl.cs.nyu.edu/~dzorin/rendering/lectures/lecture3/lecture3.pdf (source of the math)
        //find any intersections with this ray and a finite cylinder, solve quadratic equation for discriminant
        // can have 0,1, or 2 roots (intersections) and then find the points based on that

        Vector v = ray.getDir(); //direction vector of the ray
        Vector va = axisRay.getDir(); //direction vector of the axis ray
        Ray axisRay = getAxisRay();

        if (v.normalize().equals(va.normalize())) return null; //vectors are parallel = no intersections possible

        //use of calculated variables to avoid zero vector
        double vVa; //v dot va
        double pVa; //p0 dot va
        double a,b,c; //coefficients for quadratic equation
        double radSquared = radius * radius;
        double lenSqDeltaP; //length squared of deltaP
        //check every variable to avoid making a zero vector
        if (ray.getP0().equals(axisRay.getP0())) { //if the origin points are the same
            vVa = alignZero(v.dotProduct(va));
            if (isZero(vVa)) {
                a = v.lengthSquared(); //a = v^2
            } else {
                a = (v.subtract(va.scale(vVa))).lengthSquared(); //a = (v - (v dot va)va)^2
            }
            b = 0;
            c = -radSquared; //c = -r^2
        } else { //if the origin points are different
            Vector deltaP = ray.getP0().subtract(axisRay.getP0()); //difference of origin points
            vVa = alignZero(v.dotProduct(va)); //v dot va
            pVa = alignZero(deltaP.dotProduct(va)); //p0 dot va
            lenSqDeltaP = deltaP.lengthSquared(); //length squared of deltaP
            if (isZero(vVa) && isZero(pVa)) { //if both are zero
                a = v.lengthSquared(); //a = v^2
                b = 2 * v.dotProduct(deltaP); //b = 2v dot deltaP
                c = lenSqDeltaP - radSquared;
            } else if (isZero(vVa)) { //if vVa is zero
                a = v.lengthSquared(); //a = v^2
                Vector scale;
                try {
                    scale = va.scale(deltaP.dotProduct(va)); //va * (deltaP dot va)
                    if (deltaP.equals(scale)) { //if deltaP = va * (deltaP dot va)
                        b = 0;
                        c = -radSquared;
                    } else {
                        b = 2 * v.dotProduct(deltaP.subtract(scale));
                        c = (deltaP.subtract(scale).lengthSquared()) - radSquared;
                    }
                } catch (IllegalArgumentException e) { //issue with subtracting or scaling
                    b = 2 * v.dotProduct(deltaP); //b = 2v dot deltaP
                    c = lenSqDeltaP - radSquared; //c = deltaP^2 - r^2
                }
            } else if (isZero(pVa)) { //if pVa is zero
                a = (v.subtract(va.scale(vVa))).lengthSquared();
                b = 2 * v.subtract(va.scale(vVa)).dotProduct(deltaP);
                c = lenSqDeltaP - radSquared;
            } else { //if neither are zero, proceed as normal
                Vector vMinusVAScaled;
                Vector scale;
                try {
                    vMinusVAScaled = v.subtract(va.scale(vVa)); //v - (v dot va)va
                } catch (IllegalArgumentException e) {
                    vMinusVAScaled = v;
                }
                a = vMinusVAScaled.lengthSquared();
                try {
                    scale = va.scale(deltaP.dotProduct(va));
                    if (deltaP.equals(scale)) {
                        b = 0;
                        c = -radSquared;
                    } else {
                        b = 2 * vMinusVAScaled.dotProduct(deltaP.subtract(scale));
                        c = (deltaP.subtract(scale).lengthSquared()) - radSquared;
                    }
                } catch (IllegalArgumentException e) {
                    b = 2 * vMinusVAScaled.dotProduct(deltaP);
                    c = lenSqDeltaP - radSquared;
                }
            }
        }

        //calculate discriminant for result of equation (quadratic formula)
        double discriminant = alignZero(b * b - 4 * a * c);
        if (discriminant <= 0) return null; // no intersections
        //calculate points taking only those with t > 0
        //roots of the quadratic equation
        double t0 = alignZero((-b - Math.sqrt(discriminant)) / (2 * a));
        double t1 = alignZero((-b + Math.sqrt(discriminant)) / (2 * a));

        if (!isInDistance(t0, maxDistance) && !isInDistance(t1, maxDistance)) return null; //ensure both are in the bounds

        //only make a list if we have > 0 intersections
        List<GeoPoint> result = null; //default to null
        if (t0 > 0 && isInDistance(t0, maxDistance)) { //t0 is within bounds
            result = initListIfNull(result); //init list
            result.add(new GeoPoint(this, ray.getPoint(t0)));
        }

        if (t1 > 0 && isInDistance(t1, maxDistance)) { //t1 is within bounds
            result = initListIfNull(result);
            result.add(new GeoPoint(this, ray.getPoint(t1)));
        }
        return result; //return the list of points (or null if empty
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Tube)) return false;
        Tube other = (Tube) obj;
        return super.equals(other) && this.axisRay.equals(other.axisRay);
    }
}
