package lighting;

import primitives.*;

/**
 * Spot light -- like point light but focused in a direction
 */
public class SpotLight extends PointLight{

    private Vector direction;
    private int narrow=1;

    /**
     * construct
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction)
    {
        super(intensity, position);
        if(direction==null)
            throw new IllegalArgumentException("must have a direction");
        this.direction=direction.normalize();
    }

    @Override
    public Color getIntensity(Point p)
    {
        // point light attenuation by distance, multiplied with spotlight focus and narrowing
        return super.getIntensity(p).scale(
                Math.pow(Math.max(0,direction.dotProduct(this.getL(p))), narrow)
        );
    }

    /**
     * Set spotlight narrowing factor
     * @param x narrowing
     * @return object for chaining
     */
    public SpotLight setNarrowBeam(int x)
    {
        if (Util.alignZero(x)<=0)
        {
            throw new IllegalArgumentException("Must be positive");
        }
        this.narrow=x;
        return this;
    }
}
