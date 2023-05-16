package lighting;

import primitives.*;

/**
 * point light
 */
public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC=1, kL=0, kQ=0;

    /**
     * construct
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point position)
    {
        super(intensity);
        if (position==null)
            throw new IllegalArgumentException("must have a position");
        this.position=position;
    }

    /**
     * setter for kC (constant attenuation factor)
     * @param k
     * @return
     */
    public PointLight setKc(double k)
    {
        this.kC=k;
        return this;
    }

    /**
     * setter for kL (linear attenuation factor)
     * @param k
     * @return
     */

    public PointLight setKl(double k)
    {
        this.kL=k;
        return this;
    }

    /**
     * setter for kQ (quadratic attenuation factor)
     * @param k
     * @return
     */
    public PointLight setKq(double k)
    {
        this.kQ=k;
        return this;
    }

    @Override
    public Color getIntensity(Point point) {
        return this.getIntensity().reduce(this.kC
                +this.kL*this.position.distance(point)
                +this.kQ*this.position.distanceSquared(point)); //attenuation factor math
    }

    @Override
    public Vector getL(Point point)
    {
        return point.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point point)
    {
       return  Util.alignZero(this.position.distance(point));
    }
}
