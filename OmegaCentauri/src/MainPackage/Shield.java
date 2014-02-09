package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Michael Kieburtz
 */
abstract class Shield {

    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private MediaLoader loader = new MediaLoader();
    
    private int opacity = 100;
    
    public Shield()
    {
        
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        
    }
}
