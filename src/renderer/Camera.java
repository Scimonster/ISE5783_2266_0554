package renderer;
import primitives.*;

public class Camera {
    private Point location;
    private Vector vTo, vUp, vRight;

    private double width, height, distance;

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
     * @throws IllegalArgumentException if the distance is <=0
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
     * method to construct ray through pixel in viewPlane
     * @param nX number of columns
     * @param nY number of rows
     * @param j column index
     * @param i row index
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
        if (!Util.isZero(xJ))
        {
            pIJ=pIJ.add(this.vRight.scale(xJ));
        }
        if (!Util.isZero(yI))
        {
            pIJ=pIJ.add(this.vUp.scale(yI));
        }

        //the direction vector
        Vector vIJ=pIJ.subtract(this.location);

        //now that we have the start of the ray and the vector, let us return that built ray
        return new Ray(this.location, vIJ);


    }

}
