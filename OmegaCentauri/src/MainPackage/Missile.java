package MainPackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Kieburtz
 */
public class Missile {

    private ArrayList<BufferedImage> images = new ArrayList<>();
    
    public Missile()
    {
        images = Resources.getImagesForMissle();
    }
    
    public void update()
    {
        
    }
    
    public void draw(Graphics2D g2d)
    {
        
    }
}
