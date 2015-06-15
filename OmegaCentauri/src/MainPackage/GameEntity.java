package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public abstract class GameEntity 
{
    private Point2D.Double location;
    private Point2D.Double faceAngle;
    private ArrayList<BufferedImage> images;
    private Explosion explosion; // might not be needed
    
    public void update()
    {
        
    }
    
    public void draw(Graphics2D g2d)
    {
        
    }
}
