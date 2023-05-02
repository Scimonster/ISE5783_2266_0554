package scene;


import lighting.AmbientLight;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;
import primitives.Double3;

/**
 * Scene in a 3D model
 *
 * Uses builder pattern and is a PDS
 */
public class Scene {
    public String name;
    public Color background=Color.BLACK;
    public AmbientLight ambient=new AmbientLight();;
    public Geometries geometries=new Geometries();;

    /**
     * constructor to initialize name
     * @param name
     */
    public Scene(String name)
    {
        this.name=name;
    }

    public Scene setBackground(Color back)
    {
        this.background=back;
        return this;
    }
    public Scene setAmbientLight(Color iA, Double3 kA)
    {
        return this.setAmbientLight(new AmbientLight(iA, kA));
    }

    public Scene setAmbientLight(AmbientLight ambient)
    {
        this.ambient=ambient;
        return this;
    }

    public Scene addGeometries(Intersectable...geometries)
    {
        this.geometries.add(geometries);
        return this;
    }

}
