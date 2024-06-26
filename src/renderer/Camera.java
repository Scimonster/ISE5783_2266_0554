package renderer;
import primitives.*;

import java.util.MissingResourceException;
import java.util.stream.IntStream;


public class Camera {
    private Point location;
    private Vector vTo, vUp, vRight;

    private double width, height, distance;

    private ImageWriter iw;
    private RayTracerBase rayTracer;

    private Boolean threading=false;

    /**
     * setter for threading
     * @param val
     * @return
     */
    public Camera setThreading(boolean val)
    {
        this.threading=val;
        return this;
    }

    /**
     * construct the camera, by passing its location point and 2 vectors, that must be orthogonal
     * @param location where the camera is
     * @param vTo direction camera is facing
     * @param vUp direction of top of camera
     * @throws IllegalArgumentException if the vectors passed are not orthogonal
     */
    public Camera (Point location, Vector vTo, Vector vUp) throws IllegalArgumentException
    {
        if (!Util.isZero(vTo.dotProduct(vUp)))
        {
            throw new IllegalArgumentException("ERROR, vTo and vUp are not orthogonal");
        }

        //set the private members;
        this.location=location;

        //normalize the vectors
        this.vTo=vTo.normalize();
        this.vUp=vUp.normalize();

        //vRight is orthogonal to vTo and vUp, use Vector.crossProduct()
        this.vRight=this.vTo.crossProduct(this.vUp).normalize();

    }


    /**
     * getter for location of camera
     * @return location
     */
    public Point getLocation()
    {
        return this.location;
    }

    /**
     * getter for vTo vector
     * @return vTo
     */
    public Vector getVTo()
    {
        return this.vTo;
    }

    /**
     * getter for vUp vector
     * @return vUp
     */
    public Vector getVUp()
    {
        return this.vUp;
    }

    /**
     * getter for vRight vector
     * @return
     */
    public Vector getVRight() {
        return this.vRight;
    }

    /**
     * determines size of view plane
     * @param width
     * @param height
     * @return the Camera object itself
     * @throws IllegalArgumentException  if the width or the height are less than 0
     */
    public Camera setVPSize(double width, double height)
    {
        //make sure we have a proper viewplane
        if (Util.alignZero(width)<=0 || Util.alignZero(height)<=0)
        {
            throw new IllegalArgumentException("ERROR, view plane must be physically present");
        }

        this.width=width;
        this.height=height;
        return this;
    }


    /**
     * set viewPlane distance from camera
     * @param distance
     * @return the Camera object itself
     * @throws IllegalArgumentException if the distance is less than or equal to 0
     */
    public Camera setVPDistance(double distance)
    {
        //make sure we have a proper distance
        if (Util.alignZero(distance)<=0)
        {
            throw new IllegalArgumentException("ERROR, camera will never generate an image");
        }
        this.distance=distance;
        return this;
    }

    /**
     * Set the ray tracer that the camera uses
     * @param rayTracer
     * @return Camera for method chaining
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Set up the image writer to save the file
     * @param iw ImageWriter instance
     * @return Camera for method chaining
     */
    public Camera setImageWriter(ImageWriter iw) {
        this.iw = iw;
        return this;
    }

    /**
     * method to construct ray through pixel in viewPlane
     * @param nX number of columns (in px resolution)
     * @param nY number of rows (in px resolution)
     * @param j column index (in px resolution)
     * @param i row index (in px resolution)
     * @return the ray constructed
     */
    public Ray constructRay(int nX, int nY, int j, int i)
    {
        //image center
        Point pC=this.location.add(this.vTo.scale(this.distance));

        //get ratio of pixel size
        double rY=this.height/nY;
        double rX=this.width/nX;

        //get the pixel coordinate point
        double yI=-(i-((nY-1.0)/2.0))*rY;
        double xJ=(j-((nX-1.0)/2.0))*rX;
        Point pIJ=pC;

        //shifting Pij properly
        pIJ = pIJ.addScaled(this.vRight, xJ).addScaled(this.vUp, yI);

        //the direction vector
        Vector vIJ=pIJ.subtract(this.location);

        //now that we have the start of the ray and the vector, let us return that built ray
        return new Ray(this.location, vIJ);
    }

    /**
     * Render the camera image (to memory buffer)
     *
     * Calls ray tracing for all pixels
     * @throws {MissingResourceException} if any properties are unset
     * @return the object itself
     */
    public Camera renderImage()
    {
        if (this.location == null)
            throw new MissingResourceException("missing location", "Point", "location");
        if (this.rayTracer == null)
            throw new MissingResourceException("missing rayTracer", "RayTracerBase", "rayTracer");
        if (this.iw == null)
            throw new MissingResourceException("missing imageWriter", "ImageWriter", "iw");

        Ray cameraRay;
        Color pixelColor;

        if(this.threading)
        {
            Pixel.initialize(iw.getNx(), iw.getNy(), 100l);
            IntStream.range(0, iw.getNx()).parallel().forEach(j -> {
                IntStream.range(0, iw.getNy()).parallel().forEach(i -> {
                    this.iw.writePixel(j,i, this.rayTracer.traceRay(this.constructRay(iw.getNx(), iw.getNy(), j,i)));
                    Pixel.pixelDone();
                    Pixel.printPixel();
                });
            });
        }
        else {
            Pixel.initialize(this.iw.getNy(), this.iw.getNx(), 100l);
            for (int i = 0; i < this.iw.getNy(); ++i)
                for (int j = 0; j < this.iw.getNx(); ++j) {
                    this.iw.writePixel(j,i, this.rayTracer.traceRay(this.constructRay(iw.getNx(), iw.getNy(), j,i)));
                    Pixel.pixelDone();
                    Pixel.printPixel();
                //}
//            // loop over all x, y values, print the grid lines
//            for (int j = 0; j < iw.getNx(); j++) {
//                for (int i = 0; i < iw.getNy(); i++) {
//                    cameraRay = this.constructRay(iw.getNx(), iw.getNy(), j, i);
//                    pixelColor = this.rayTracer.traceRay(cameraRay);
//                    this.iw.writePixel(j, i, pixelColor);
                //}
            }
        }
        return this;
    }

    /**
     * Print a grid on the image
     * @param interval grid pixel size
     * @param color grid line color
     */
    public void printGrid(int interval, Color color)
    {
        if (this.iw == null)
            throw new MissingResourceException("missing imageWriter", "ImageWriter", "iw");

        // loop over all x, y values, print the grid lines
        for (int j = 0; j < iw.getNx(); j++) {
            for (int i = 0; i < iw.getNy(); i++) {
                if (j % interval == 0 || i % interval == 0 || j == iw.getNx() - 1 || i == iw.getNy() - 1) { // grid line
                    iw.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Write the generated image to file
     * @return the object itself
     */
    public Camera writeToImage() {
        if (this.iw == null)
            throw new MissingResourceException("missing imageWriter", "ImageWriter", "iw");

        iw.writeToImage();
        return this;
    }

    /**
     * Pan the camera along one of its axes
     * @param t amount to move
     * @param axis axis vector
     * @return this
     */
    private Camera pan(double t, Vector axis)
    {
        if (Util.isZero(t))
        {
            throw new IllegalArgumentException("Cannot move 0");
        }
        this.location = this.location.add(axis.scale(t));
        return this;
    }

    /**
     * Move the camera forwards or backwards (along the z-axis)
     * @param t amount to move
     * @return this
     */
    public Camera moveZ(double t)
    {
        return this.pan(t, this.vTo);
    }

    /**
     * Move the camera left or right (along the x-axis)
     * @param t amount to move
     * @return this
     */
    public Camera moveX(double t)
    {
        return this.pan(t, this.vRight);
    }

    /**
     * Move the camera up or down (along the y-axis)
     * @param t amount to move
     * @return this
     */
    public Camera moveY(double t)
    {
        return this.pan(t, this.vUp);
    }

    /**
     * Rotate a vector using the standard rotation matrix -- representation is based on interpretation
     * @param v1 vector to rotate by
     * @param v2 vector to rotate by
     * @param deg angle to rotate
     * @return rotated vector
     */
    private Vector rotateVector(Vector v1, Vector v2, double deg)
    {
        double theta = Math.toRadians(deg);
        if (Util.isZero(Math.cos(theta)))
            return v2.scale(Math.sin(theta));
        else if (Util.isZero(Math.sin(theta)))
            return v1.scale(Math.cos(theta));
        return v1.scale(Math.cos(theta)).add(v2.scale(Math.sin(theta)));
    }

    public Camera rotateByTo(double theta)
    {
        Vector v1=rotateVector(this.vUp, this.vRight, theta);
        Vector v2=rotateVector(this.vRight, this.vUp, -theta);

        this.vUp=v1;
        this.vRight=v2;
        return this;
    }

    public Camera rotateByUp(double theta)
    {
        Vector v1=rotateVector(this.vTo, this.vRight, theta);
        Vector v2=rotateVector(this.vRight, this.vTo, -theta);

        this.vTo=v1;
        this.vRight=v2;
        return this;
    }

    public Camera rotateByRight(double theta)
    {
        Vector v1=rotateVector(this.vUp, this.vTo, theta);
        Vector v2=rotateVector(this.vTo, this.vUp, -theta);

        this.vUp=v1;
        this.vTo=v2;
        return this;
    }

}
