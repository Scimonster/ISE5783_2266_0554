package lighting;

import primitives.*;

/**
 * A light source
 */
public interface LightSource {

    /**
     * gets intensity on of the lightSource on a specific point
     * @param point
     * @return
     */
   public Color getIntensity(Point point);

    /**
     * direction vector of the LightSource to the point
     * @param point
     * @return
     */
   public Vector getL(Point point);
}
