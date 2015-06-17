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
    protected Point2D.Double location;
    protected Point2D.Double faceAngle;
    protected ArrayList<BufferedImage> images;
    protected Explosion explosion; // might not be needed
    protected GameData gameData;
    
    public void update()
    {
        
    }
    
    public void draw(Graphics2D g2d)
    {
        
    }
}
