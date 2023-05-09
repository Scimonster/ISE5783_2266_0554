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
     * default construct
     */
    public AmbientLight()
    {
        super(Color.BLACK);
    }


}
