package renderer;

import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;


public class RayTracerAdvanced extends RayTracerBasic {
    private int sampleSize=81;


    private int rDistance=100;

    /**
     * Init a ray tracer
     * @param scene the scene to draw
     */
    public RayTracerAdvanced(Scene scene)
    {
        super(scene);
    }

    /**
     * setter for sample size
     * @param samp
     * @return
     */
    public RayTracerAdvanced setSampleSize(int samp)
    {
        sampleSize=samp;
        return this;
    }

    /**
     * setter for distance
     * @param dist
     * @return
     */
    public RayTracerAdvanced setDistance(int dist)
    {
        rDistance=dist;
        return this;
    }



    /**
     * method to construct reflected rays
     * @param n
     * @param gp
     * @param inRay
     * @return list of rays
     */
    @Override
    protected List<Ray> constructReflectedRays(Vector n, GeoPoint gp, Ray inRay)
    {

        double side_size = inRay.findDistance(gp.point)*gp.geometry.getMaterial().nGlossiness;

        Ray reflected=constructReflectedRay(n, gp.point, inRay);

        if(Util.isZero(side_size)) {
            return List.of(reflected);
        }

        return constructRays(reflected, side_size, side_size, this.rDistance);
    }

    /**
     * method to construct refracted rays
     * @param gp
     * @param inRay
     * @return list of rays
     */
    @Override
    protected List<Ray> constructRefractedRays(GeoPoint gp, Ray inRay)
    {
        double side_size = inRay.findDistance(gp.point)*gp.geometry.getMaterial().nDiffusive;
        Ray refracted=constructRefractedRay(gp.point, inRay);

        if(Util.isZero(side_size))
        {
            return List.of(refracted);
        }

        return constructRays(refracted, side_size, side_size, this.rDistance);
    }


    /**
     * construct a stam amount of rays
     * @param height
     * @param width
     * @param toRay
     * @return list of rays
     */
    @Override
    protected List<Ray> constructRays(Ray toRay, double height, double width, double distance)
    {
        List<Ray> rays = new LinkedList<>();
        rays.add(toRay); // start with original ray

        if(this.sampleSize<5)
        {
            return rays;
        }

        // Jitter algorithm
        // Divide into segments (based on sample size), then randomly distribute rays within each segment

        int segments_per_side = (int) Math.round(Math.log10(this.sampleSize)/Math.log10(5));

        int rays_per_segment = (int) Math.round(this.sampleSize / (height*width));

        // Get coordinate system for our viewplane
        List<Vector> orthogonal = toRay.getDir().getOrthogonalVectors();
        Vector xAxis = orthogonal.get(0),
               yAxis = orthogonal.get(1);
        Point pC=toRay.getPoint(distance);

        // Divide the view plane into segments
        double segment_size_w = width / segments_per_side;
        double segment_size_h = height / segments_per_side;


        for (int i = 0; i < segments_per_side; i++) {
            for (int j = 0; j < segments_per_side; j++) {
                // Determine the starting point of the current segment
                double segment_start_x = i * segment_size_w;
                double segment_start_y = j * segment_size_h;

                for (int k = 0; k < rays_per_segment; k++) {
                    // Calculate the position of the ray within the segment
                    double ray_x = segment_start_x + (Math.random() * segment_size_w);
                    double ray_y = segment_start_y + (Math.random() * segment_size_h);


                    //get the pixel coordinate point
                   double yI = -(ray_y - ((height - 1.0) / 2.0));
                   double xJ = (ray_x - ((width - 1.0) / 2.0));
                    Point pIJ = pC;

                    //shifting Pij properly
                    if (!Util.isZero(ray_x)) {
                        try {
                            pIJ = pIJ.add(xAxis.scale(xJ));
                        }catch(IllegalArgumentException n){}
                    }
                    if (!Util.isZero(ray_y)) {
                        try {
                            pIJ = pIJ.add(yAxis.scale(yI));
                        }catch(IllegalArgumentException n){}
                    }

                    //the direction vector
                    Vector vIJ = pIJ.subtract(toRay.getP0());


                    Ray cur = new Ray(toRay.getP0(), vIJ);


                    //don't add our original ray twice
                    if (!cur.equals(toRay)) {
                        rays.add(cur);
                    }

                }
            }
        }

        return rays;

    }

}
