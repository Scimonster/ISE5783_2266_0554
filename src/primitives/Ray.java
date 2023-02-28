package primitives;

public class Ray {
    Point p0;
    Vector dir;

    public Ray(Point point, Vector vector)
    {
        this.p0=point;
        this.dir=vector.normalize();
    }

    @Override
    public String toString()
    {
        return "Ray: ["+ this.p0.toString()+" " + this.dir.toString() + "]";
    }

    /**
     * returns whether two rays are the same
     * @param obj the object compared
     * @return true if the object is a ray and has the same attributes
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return this.p0.equals(other.p0)&& this.dir.equals(other.dir);

    }
}
