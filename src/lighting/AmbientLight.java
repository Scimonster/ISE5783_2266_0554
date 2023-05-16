package lighting;
import primitives.*;

/**
 * standard ambient lighting class
 */
public class AmbientLight extends Light{

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * construct ambient light
     * @param iA intensity
     * @param kA attenuation
     */
    public AmbientLight(Color iA, Double3 kA)
    {
       super(iA.scale(kA));
    }

    /**
     * construct ambient light
     * @param iA intensity
     * @param kA attenuation
     */
    public AmbientLight(Color iA, double kA)
    {
        this(iA, new Double3(kA));
    }

    /**
     * default constructor -- black ambient light
     */
    public AmbientLight()
    {
        super(Color.BLACK);
    }


}
