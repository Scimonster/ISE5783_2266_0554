package primitives;

public class Material {

    public Double3 kD=Double3.ZERO, kS=Double3.ZERO;

    public int nShininess=0;

    /**
     * set kD
     * @param kD
     * @return
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
     * @param kS
     * @return the object itself
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
     * @param kD
     * @return the object itself
     */
    public Material setKd(double kD)
    {
        this.kD=new Double3(kD);
        return this;
    }

    /**
     * set kS
     * @param kS
     * @return the object itself
     */
    public Material setKs(double kS)
    {
        this.kS=new Double3(kS);
        return this;
    }

    /**
     * set nShininess
     * @param nShininess
     * @return the object itself
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
