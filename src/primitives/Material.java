package primitives;

/**
 * A material which a geometry can have
 * Holds diffusion, specular, and shininess factors
 */
public class Material {

    public Double3 kD=Double3.ZERO, kS=Double3.ZERO;

    public int nShininess=0;

    /**
     * set kD
     *
     * @param kD diffusion coefficient (different for red, green, and blue)
     * @return the object itself for chaining
     */
    public Material setKd(Double3 kD)
    {
        if (kD==null)
            throw new IllegalArgumentException("can't be null");
        this.kD = kD;
        return this;
    }

    /**
     * set kS
     *
     * @param kS specular coefficient (different for red, green, and blue)
     * @return the object itself for chaining
     */

    public Material setKs(Double3 kS)
    {
        if (kS==null)
            throw new IllegalArgumentException("can't be null");
        this.kS = kS;

        return this;
    }


    /**
     * set kD
     * @param kD diffusion coefficient (single value)
     * @return the object itself for chaining
     */
    public Material setKd(double kD)
    {
        this.kD=new Double3(kD);
        return this;
    }

    /**
     * set kS
     * @param kS specular coefficient (single value)
     * @return the object itself for chaining
     */
    public Material setKs(double kS)
    {
        this.kS=new Double3(kS);
        return this;
    }

    /**
     * set nShininess
     * @param nShininess material shininess factor
     * @return the object itself for chaining
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
