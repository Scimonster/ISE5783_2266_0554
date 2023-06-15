package unittests.renderer;
import static java.awt.Color.*;

import lighting.*;
import org.junit.jupiter.api.Test;


import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class FINALPICTURE {
    @Test
    public void FINAL()
    {
        Geometry top=new Polygon(new Point(0,0,0), new Point(0,50,0), new Point(100,50, 0), new Point(100,0,0)).setMaterial(new Material().setKt(.85).setGlossiness(0).setDiffusive(0.03)).setEmission(Color.BLACK);
        Geometry middle=new Polygon(new Point(0,0,0),new Point(100,0,0),new Point(100,-15,0), new Point( 0,-15,0)).setMaterial(new Material().setKt(0).setKd(0.03)).setEmission(Color.BLUE);
        Geometry bottom=new Polygon(new Point(0,-15,0), new Point(0,-75,0), new Point(100,-75, 0), new Point(100,-15,0)).setMaterial(new Material().setKt(.85).setGlossiness(0.05).setDiffusive(0.03)).setEmission(new Color(240,240,240));

        Geometries window=new Geometries(top,middle,bottom);


        Geometry grass=new Plane(new Point(0,-100,0), new Vector(0,1,0)).setEmission(Color.GREEN);

        //Geometry building1= new Cylinder(50, new Ray(new Point(-100,-100,-400), new Vector(0,1,0)), 1000).setEmission(Color.BLUE);

        Geometry building1 =new Sphere(new Point(0,100,-400), 100).setEmission(Color.BLUE);



        Geometries buildings=new Geometries(building1);


        Camera camera=new Camera(new Point(50,20,20), new Vector(0,0,-1), new Vector(0,1,0)).setThreading(true).setVPSize(500,500).setVPDistance(50).setRayTracer(
                new RayTracerBasic(new Scene("Picture").setBackground(new Color(51,255,255)).addGeometries(window,grass,buildings).setAmbientLight(new AmbientLight(Color.BLACK, Double3.ONE)))).setImageWriter(new ImageWriter("picture", 250,250)).renderImage().writeToImage();







    }

}
