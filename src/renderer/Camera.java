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
    public Vector getvTo()
    {
        return this.vTo;
    }

    /**
     * getter for vUp vector
     * @return vUp
     */
    public Vector getvUp()
    {
        return this.vUp;
    }

    /**
     * getter for vRight vector
     * @return
     */
    public Vector getvRight() {
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
        return null;
    }

}
