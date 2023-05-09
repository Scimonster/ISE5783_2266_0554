package lighting;

import primitives.*;


/**
 * Directional light
 */
public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     * construct
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction)
    {
        super(intensity);
        if(direction==null)
            throw new IllegalArgumentException("must have a direction");
        this.direction=direction.normalize();
    }

    @Override
    public Vector getL(Point point)
    {
        return this.direction;
    }

    @Override
    public Color getIntensity(Point point)
    {
        return this.getIntensity();
    }
}
