package lighting;

import primitives.Color;

/**
 * Light source
 */
public abstract class Light {

    private Color intensity;

    /**
     * Construct Light
     * @param color
     */
    protected Light(Color color)
    {
        if (color==null)
        {
            throw new IllegalArgumentException("Intensity can't be null");
        }

        this.intensity=color;
    }

    /**
     * getter for the intensity
     * @return intensity
     */
    public Color getIntensity()
    {
        return this.intensity;
    }



}
