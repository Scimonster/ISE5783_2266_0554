package geometries;

import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;

import org.junit.platform.commons.function.Try;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   private final int           size;

   /** Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }

   @Override
   public Vector getNormal(Point point) { return plane.getNormal(); }

   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
   {
      //check if it falls in plane of polygon
      List<GeoPoint> res=this.plane.findGeoIntersections(ray); // not geo point

      if (res==null)
      {
         return null;
      }

      //check if all the crossProducts are in same directions
      Point p1, p2;
      Point intersect=res.get(0).point;
      List<Vector> crossList= new ArrayList<Vector>(this.vertices.size());
      Vector v1;
      Vector v2;
      Vector v3;
      double dotProduct;
      double dotProduct2;


      //if 0 vector created, return null
      try
      {
         //get all the cross products
         for (int x=0; x<this.vertices.size(); x++)
         {
            p1=this.vertices.get(x);
            p2=this.vertices.get((x+1)%this.vertices.size());
            v1=p2.subtract(p1);
            v2=p1.subtract(intersect);
            v3=v1.crossProduct(v2);
            crossList.add(v3);
         }

      }
      catch (IllegalArgumentException f)
      {
         return null;
      }

      //get the first dot product
      dotProduct=Util.alignZero(crossList.get(0).dotProduct(crossList.get(1)));
      if (dotProduct==0)
      {
         return null;
      }

      //check that all subsequent dot products are the same sign, if they aren't return null
      for (int x=1; x<crossList.size(); ++x)
      {
         dotProduct2=Util.alignZero(crossList.get(x).dotProduct(crossList.get((x+1)%this.vertices.size())));

         if(!Util.checkSign(dotProduct, dotProduct2))
         {
            return null;
         }
      }

      return List.of(new GeoPoint(this, intersect));
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (!(obj instanceof Polygon)) return false;
      Polygon other = (Polygon) obj;
      return super.equals(other) && this.vertices.equals(other.vertices);
   }


}
