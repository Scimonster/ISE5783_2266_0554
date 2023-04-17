package geometries;

import primitives.*;

import java.util.Collections;
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

        for(Intersectable shape: shapes)
        {
            this.shapes.add(shape);
        }

    }

    public void add(Intersectable shape)
    {
        this.shapes.add(shape);
    }

    @Override
    public List<Point> findIntersections(Ray ray)
    {
         List<Point> preres=null;
         List<Point> cur=null;

         for (Intersectable shape: this.shapes)
         {
             cur=shape.findIntersections(ray);
             if(cur!=null)
             {
                 if (preres==null)
                 {
                     preres=new LinkedList<>(cur);
                 }
                 else
                 {
                     preres.addAll(cur);
                 }
             }
         }

         if (preres==null)
         {
             return null;
         }


         return Collections.unmodifiableList(preres);

    }
}

