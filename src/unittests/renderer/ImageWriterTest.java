package unittests.renderer;

import org.junit.Test;
import primitives.Color;
import renderer.ImageWriter;

/**
 * Test {@link ImageWriter}
 */
public class ImageWriterTest {
    static final int NX = 800;
    static final int NY = 500;
    static final int WIDTH = 16;
    static final int HEIGHT = 10;

    /**
     * Build a basic 10x16 grid at resolution 500x800
     */
    @Test
    public void testImageWriter()
    {
        Color bg = new Color(255, 0, 0);
        Color fg = new Color(0, 0, 255);
        ImageWriter iw = new ImageWriter("grid", NX, NY);

        int xScale = NX / WIDTH;
        int yScale = NY / HEIGHT;

        for (int j = 0; j < NX; j++) {
            for (int i = 0; i < NY; i++) {
                if (j % xScale == 0 || i % yScale == 0) { // grid line
                    iw.writePixel(j, i, fg);
                } else {
                    iw.writePixel(j, i, bg);
                }
            }
        }
        iw.writeToImage();
    }
}
