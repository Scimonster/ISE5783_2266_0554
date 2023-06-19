package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite design patter for geometries
 */
public class Geometries extends Intersectable{
    private List<Intersectable> shapes;

    public Geometries()
    {
        shapes=new LinkedList<Intersectable>();
        this.highBound=new Point(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
        this.lowBound=new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * construct Geometries with a list of intersectables
     * @param shapes
     */
    public Geometries(Intersectable ... shapes)
    {
        this();
        this.add(shapes);

    }


    /**
     * method to add intersectables to Geometries
     * @param shapes list of geometries
     */

    public void add(Intersectable ... shapes)
    {
        double xMin=lowBound.getX(),
               yMin=lowBound.getY(),
               zMin=lowBound.getZ(),
               xMax=highBound.getX(),
               yMax=highBound.getY(),
               zMax=highBound.getZ();

        for(Intersectable shape: shapes)
        {
            this.shapes.add(shape);

            if(shape.lowBound==null)
            {
                xMin=yMin=zMin=Double.NEGATIVE_INFINITY;
            }
            else {
                xMin = Math.min(xMin, shape.lowBound.getX());
                yMin = Math.min(yMin, shape.lowBound.getY());
                zMin = Math.min(zMin, shape.lowBound.getZ());
            }



            if(shape.highBound==null)
            {
                xMax=yMax=zMax=Double.POSITIVE_INFINITY;
            }
            else {
                xMax = Math.max(xMax, shape.highBound.getX());
                yMax = Math.max(yMax, shape.highBound.getY());
                zMax = Math.max(zMax, shape.highBound.getZ());
            }
        }
        if(xMax!=Double.POSITIVE_INFINITY) {
            this.highBound = new Point(xMax, yMax, zMax);
        }
        else {
            this.highBound=null;
        }

        if(xMin!=Double.NEGATIVE_INFINITY)
        {
            this.lowBound=new Point(xMin,yMin,zMin);
        }
        else {
            this.lowBound=null;
        }
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
    {
         // 2 lists, one to hold the current shape's intersection points, and one to hold all the intersections
         List<GeoPoint> res=null, cur=null;

         //iterate through whole this.shapes, invoke findIntersections for each shape
         for (Intersectable shape: this.shapes)
         {
             cur=shape.findGeoIntersections(ray, maxDistance);
             if(cur!=null)
             {
                 if (res==null)
                 {
                     res=new LinkedList<>(cur);
                 }
                 else
                 {
                     res.addAll(cur);
                 }
             }
         }

         return res;

    }

}

