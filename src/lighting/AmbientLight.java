package lighting;
import primitives.*;

/**
 * standard ambient lighting class
 */
public class AmbientLight {

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    private Color intensity;

    /**
     * construct ambient light
     * @param iA intensity
     * @param kA attenuation
     */
    public AmbientLight(Color iA, Double3 kA)
    {
        this.intensity=iA.scale(kA);
    }

    /**
     * default construct
     */
    public AmbientLight()
    {
        this.intensity=Color.BLACK;
    }

    public Color getIntensity()
    {
        return this.intensity;
    }
}
