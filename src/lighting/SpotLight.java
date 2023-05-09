package lighting;

import primitives.*;

/**
 * Point light
 */
public class SpotLight extends PointLight{

    private Vector direction;

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
        this.direction=direction;
    }

    @Override
    public Color getIntensity(Point p)
    {
        return super.getIntensity(p).scale(Math.max(0,this.direction.dotProduct(this.getL(p))));
    }
}
