package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */

abstract class Shot {
    
    protected int lifespan;
    protected int life;
    protected int damage;
    protected ImageLoader imageLoader = new ImageLoader();
    protected boolean animated;
    protected ArrayList<BufferedImage> images;
    protected Point2D.Double location;
    
    protected void draw(Graphics g, Camera camera) // ovveride method if needed
    {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.drawImage(images.get(0), (int)(location.x - camera.getLocation().x),
                (int)(location.y - camera.getLocation().y), null);
    }
    
    protected void loadImages(ArrayList<String> imagePaths)
    {
        images = imageLoader.loadImages(imagePaths);
    }
    
}
