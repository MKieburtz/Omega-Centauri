package MainPackage;

import java.awt.Graphics2D;

/**
 * @author Michael Kieburtz
 */
public interface GameEntity 
{   
    public void update();
    
    public void draw(Graphics2D g2d);
}
