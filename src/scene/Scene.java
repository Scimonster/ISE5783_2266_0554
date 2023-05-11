package scene;


import lighting.AmbientLight;
import geometries.Geometries;
import geometries.Intersectable;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene in a 3D model
 *
 * Uses builder pattern and is a PDS
 */
public class Scene {
    public String name;
    public Color background=Color.BLACK;
    public AmbientLight ambient=new AmbientLight();

    public Geometries geometries=new Geometries();

    public List<LightSource> lights=new LinkedList<>();

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

    /**
     * add lights to a scene
     * @param lights
     * @return
     */
    public Scene addLightSource(LightSource ... lights)
    {
        for (LightSource light : lights)
        {
            this.lights.add(light);
        }

        return this;
    }

}
