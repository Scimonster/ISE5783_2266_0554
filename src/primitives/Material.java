package primitives;

/**
 * A material which a geometry can have
 * Holds diffusion, specular, and shininess factors
 */
public class Material {

    /**
     * Attributes of material
     */
    public Double3 kD=Double3.ZERO, kS=Double3.ZERO, kT=Double3.ZERO, kR=Double3.ZERO;

    public double nShininess=0, nGlossiness=0, nDiffusive=0;


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

    /**
     * set nGlossiness
     * @param nGlossiness
     * @return
     */
    public Material setGlossiness(double nGlossiness)
    {
        this.nGlossiness=nGlossiness;
        return this;
    }

    /**
     * set nDiffusive
     * @param n
     * @return
     */
    public Material setDiffusive(double nDiffusive)
    {
        this.nDiffusive=nDiffusive;
        return this;
    }



    /**
     * set transparency coefficient
     * @param kT
     * @return
     */
    public Material setKt(Double3 kT)
    {
        if (kT==null)
            throw new IllegalArgumentException("can't be null");
        this.kT = kT;
        return this;
    }

    /**
     * set reflection coefficient
     * @param kR
     * @return the object itself
     */
    public Material setKr(Double3 kR)
    {
        if (kR==null)
            throw new IllegalArgumentException("can't be null");
        this.kR = kR;
        return this;
    }

    /**
     * set transparency coefficient
     * @param kT
     * @return the object itself
     */
    public Material setKt(double kT)
    {
        this.kT=new Double3(kT);
        return this;
    }

    /**
     * set reflection coefficient
     * @param kR
     * @return the object itself
     */
    public Material setKr(double kR)
    {
        this.kR=new Double3(kR);
        return this;
    }
}
