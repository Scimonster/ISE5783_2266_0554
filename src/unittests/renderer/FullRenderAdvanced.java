package unittests.renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerAdvanced;
import renderer.RayTracerBasic;
import scene.Scene;

public class FullRenderAdvanced {

    @Test
    public void testInteresting2()
    {
        //pyramid
        Point A=new Point(0,0,0),
                B= new Point(-10,-10, -10),
                C=new Point(-10,-10,10),
                D= new Point(10, -10,10),
                E = new Point(10, -10, -10);

//        (0, 10, 0)
//        (10,-7,-25)
//        (-10, 17, 25)

        Color color=new Color(180,180,180);
        Material mat=new Material().setKd(0.2).setKs(0.05).setKr(0.5).setShininess(1);


        Geometry t1=new Triangle(A,B,C).setMaterial(mat).setEmission(Color.BLUE),
                t2=new Triangle(A,C,D).setMaterial(mat).setEmission(Color.WHITE),
                t3=new Triangle(A,D,E).setMaterial(mat).setEmission(Color.RED),
                t4=new Triangle(A,E,B).setMaterial(mat).setEmission(Color.BLACK),
                square=new Polygon(B,C,D,E).setMaterial(mat).setEmission(color);
        Geometries pyramid = new Geometries(t1, t2, t3, t4, square);


        Point A1=new Point(0,20,0),
                B2= new Point(-10,30, -10),
                C3=new Point(-10,30,10),
                D4= new Point(10, 30,10),
                E5 = new Point(10, 30, -10);

        Geometry t11=new Triangle(A1,B2,C3).setMaterial(mat).setEmission(Color.BLUE),
                t22=new Triangle(A1,C3,D4).setMaterial(mat).setEmission(Color.RED),
                t33=new Triangle(A1,D4,E5).setMaterial(mat).setEmission(Color.WHITE),
                t44=new Triangle(A1,E5,B2).setMaterial(mat).setEmission(Color.BLACK),
                square1=new Polygon(B2,C3,D4,E5).setMaterial(mat).setEmission(color);

        Geometries pyramid2 = new Geometries(t11, t22, t33, t44, square1);


        Geometry plane = new Plane(new Point(0,0,-50), new Vector(0,1,4)).setMaterial(new Material().setKt(1).setShininess(0).setDiffusive(0.02)).setEmission(Color.BLACK);

        Geometry poly=new Polygon(new Point(-10,-5,60), new Point(10,-5,60), new Point(5,10,60), new Point(-5,10,60)).setMaterial(new Material().setKt(0.3)).setEmission(new Color(255,16, 200));


        //sphere
        Point sph = new Point(0,10, 0);
        Geometry sphere= new Sphere(sph, 10d).setMaterial(mat.setShininess(20).setKr(0.1).setGlossiness(0.04)).setEmission(Color.BLUE);


        Camera camera=new Camera(new Point(-20,-10,75), new Vector(0,0,-1), new Vector(0,1,0)).setVPSize(50,50).setVPDistance(50).setThreading(true);

        Scene scene=new Scene("original");

        scene.addLightSource( //
                new SpotLight(new Color(1000, 600, 0), new Point(-10, -10, -20), new Vector(0.1, 0.1, 2)).setNarrowBeam(3) //
                        .setKl(0.0004).setKq(0.00006),
//                new DirectionalLight(new Color(255,0,0), new Vector(0,0,5))
                new DirectionalLight(new Color(255,0,0), new Vector(0,-1,-5)),
                new PointLight(new Color(255, 172, 75), new Point(-20, -5, 15)).setKl(0.002).setKq(0.00004)
        );

        scene.geometries.add(pyramid, sphere, plane, poly, pyramid2);

        scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

        Camera camera2= new Camera(new Point(0,0,-100), new Vector(0,0,1), new Vector(0,1,0)).setVPSize(50,50).setVPDistance(25).setThreading(true);
        camera2.setImageWriter(new ImageWriter("original2.7", 500, 500)) //
                .setRayTracer(new RayTracerAdvanced(scene).setSampleSize(81).setDistance(100))
                .renderImage() //
                .writeToImage();



        camera.setImageWriter(new ImageWriter("original2.1", 500, 500)) //
                .setRayTracer(new RayTracerAdvanced(scene).setDistance(100).setSampleSize(81)) //
                .renderImage() //
                .writeToImage();



        camera.setImageWriter(new ImageWriter("original2.2", 500, 500)) //
                .setRayTracer(new RayTracerAdvanced(scene).setDistance(100).setSampleSize(81))
                .moveX(10)
                .moveY(20)//
                .renderImage() //
                .writeToImage();


        camera.setImageWriter(new ImageWriter("original2.3", 500, 500)) //
                .setRayTracer(new RayTracerAdvanced(scene).setDistance(100).setSampleSize(81))
                .moveZ(20)
                .rotateByRight(30)//
                .renderImage() //
                .writeToImage();

        camera.setImageWriter(new ImageWriter("original2.4", 500, 500)) //
                .setRayTracer(new RayTracerAdvanced(scene).setDistance(100).setSampleSize(81))
                .moveZ(20)
                .rotateByRight(10)
                .moveY(20)//
                .renderImage() //
                .writeToImage();

        camera.setImageWriter(new ImageWriter("original2.5", 500, 500)) //
                .setRayTracer(new RayTracerAdvanced(scene).setDistance(100).setSampleSize(81))
                .moveZ(20)
                .rotateByRight(-50)
                .moveY(-20)//
                .renderImage() //
                .writeToImage();

        camera.setImageWriter(new ImageWriter("original2.6", 500, 500)) //
                .setRayTracer(new RayTracerAdvanced(scene).setDistance(100).setSampleSize(81))
                .moveZ(-10)
                .rotateByRight(-10)
                .rotateByUp(30)//
                .renderImage() //
                .writeToImage();




    }

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000).setThreading(true); //

        Scene scene = new Scene("Test scene");

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0)).setDiffusive(0.002)),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1).setGlossiness(0.002)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirroredImproved", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerAdvanced(scene).setDistance(100).setSampleSize(81)) //
                .renderImage() //
                .writeToImage();
    }


}




