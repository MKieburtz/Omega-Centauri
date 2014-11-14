package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */

public class HeadsUpDisplayPlayer {

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<BufferedImage> images = new ArrayList<>();
    
    private int TOPLEFTHUD = 2;
    
    public HeadsUpDisplayPlayer(Resources resources)
    {
        imagePaths.add("resources/DangerBar.png");
        imagePaths.add("resources/HealthyBar.png");
        imagePaths.add("resources/HUDTopLeft.png");
        imagePaths.add("resources/ShieldStatusBad.png");
        imagePaths.add("resources/ShieldStatusGood.png");
        imagePaths.add("resources/ShieldStatusWarning.png");
        imagePaths.add("resources/WarningBar.png");
        images = resources.getImagesForObject(imagePaths);
    }
    
    public void draw(Graphics2D g2d, Camera camera)
    {
        g2d.drawImage(images.get(TOPLEFTHUD), -10, -10, null);
    }
}
