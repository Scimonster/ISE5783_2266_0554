package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    private List<Intersectable> shapes;

    public Geometries()
    {
        shapes=new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable ... shapes)
    {
        this();

        this.add(shapes);

    }

    public void add(Intersectable ... shapes)
    {
        for(Intersectable shape: shapes)
        {
            this.shapes.add(shape);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray)
    {
         // 2 lists, one to hold the current shape's intersection points, and one to hold all the intersections
         List<Point> res=null, cur=null;

         //iterate through whole this.shapes, invoke findIntersections for each shape
         for (Intersectable shape: this.shapes)
         {
             cur=shape.findIntersections(ray);
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

