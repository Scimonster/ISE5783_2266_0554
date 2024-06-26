package unittests.renderer;


import static java.awt.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Test rendering a basic image
 * @author Dan */
public class RenderTests {

   /** Produce a scene with basic 3D model and render it into a png image with a
    * grid */
   @Test
   public void basicRenderTwoColorTest() {
      Scene scene = new Scene("Test scene")//
         .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                                           new Double3(1, 1, 1))) //
         .setBackground(new Color(75, 127, 90));

      scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
                           new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                           // left
                           new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                                        new Point(-100, -100, -100)), // down
                           // left
                           new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
      // right
      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPDistance(100) //
         .setVPSize(500, 500) //
         .setImageWriter(new ImageWriter("base render test", 1000, 1000))
         .setRayTracer(new RayTracerBasic(scene));

      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }

   // For stage 6 - please disregard in stage 5
   /** Produce a scene with basic 3D model - including individual lights of the
    * bodies and render it into a png image with a grid */
    @Test
    public void basicRenderMultiColorTest() {
    Scene scene = new Scene("Test scene")//
    .setAmbientLight(new AmbientLight(Color.WHITE, new Double3(0.2))); //

    scene.geometries.add( //
    new Sphere(new Point(0, 0, -100), 50),
    // up left
    new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new
    Point(-100, 100, -100))
    .setEmission(Color.GREEN),
    // down left
    new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new
    Point(-100, -100, -100))
    .setEmission(Color.RED),
    // down right
    new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
    Point(100, -100, -100))
    .setEmission(Color.BLUE));

    Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1,
    0)) //
    .setVPDistance(100) //
    .setVPSize(500, 500) //
    .setImageWriter(new ImageWriter("color render test", 1000, 1000))
    .setRayTracer(new RayTracerBasic(scene));

    camera.renderImage();
    camera.printGrid(100, Color.WHITE);
    camera.writeToImage();
    }

   /** Test for XML based scene - for bonus */
   @Test
   public void basicRenderXml() {
      Scene  scene=null;
      XMLParser doc = new XMLParser( "basicRenderTestTwoColors.xml");
      try {
         scene  = doc.parse();
      }
      catch (Exception e)
      {
         System.out.print(e.getMessage());
         fail("exception occurred during parse");
      }
      // enter XML file name and parse from XML file into scene object
      // using the code you added in appropriate packages
      // ...
      // NB: unit tests is not the correct place to put XML parsing code

      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))     //
         .setVPDistance(100)                                                                //
         .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
         .setRayTracer(new RayTracerBasic(scene));
      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }

   /**
    * Render, and pan camera
    */
   @Test
   public void panRenderTwoColorTest() {
      Scene scene = new Scene("Test scene")//
              .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                      new Double3(1, 1, 1))) //
              .setBackground(new Color(75, 127, 90));

      scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
              // left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                      new Point(-100, -100, -100)), // down
              // left
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
      // right
      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPDistance(100) //
              .setVPSize(500, 500) //
              .setImageWriter(new ImageWriter("base render test", 1000, 1000))
              .setRayTracer(new RayTracerBasic(scene));

      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();

      camera.setImageWriter(new ImageWriter("pan render test", 1000, 1000))
              .moveX(50).moveY(100).moveZ(-50);
      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }
}
