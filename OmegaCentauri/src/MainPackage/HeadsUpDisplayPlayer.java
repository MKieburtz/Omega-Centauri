package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.GraphicAttribute;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */

public class HeadsUpDisplayPlayer {

    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    
    private final int SCANNERMODULE = 0;
    private final int HEALTHBACKGROUND = 1;
    
    public HeadsUpDisplayPlayer()
    {
        images = Resources.getImagesForHUD();
    }
    
    public void draw(Graphics2D g2d, Camera camera)
    {
        g2d.drawImage(images.get(HEALTHBACKGROUND), null, 0, 0);
        
        g2d.drawImage(images.get(SCANNERMODULE), null, 0, camera.getSize().y - 113);
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(camera.getSize().x - 201, camera.getSize().y - 225, 200, 200);

        g2d.setColor(new Color(0, 255, 0, 50));
        g2d.fillRect(camera.getSize().x - 201, camera.getSize().y - 225, 200, 200);

        g2d.setColor(Color.GREEN);
        g2d.drawRect(camera.getSize().x - 201, camera.getSize().y - 225, 200, 200);
    }
}
