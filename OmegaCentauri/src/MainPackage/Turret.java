package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Kieburtz
 */
public class Turret {

    private ArrayList<String> imagePaths = new ArrayList<String>();
    
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    
    private MediaLoader loader = new MediaLoader();
    
    private int maxDurability;
    private int durability;
    
    private int maxRotation;
    private int minRotation;
    
    private int angle = 0;
    
    public Turret(int maxDurability, int maxRotation, int minRotation)
    {
        this.maxDurability = maxDurability;
        this.durability = maxDurability;
        
        this.maxRotation = maxRotation;
        this.minRotation = minRotation;
        
        imagePaths.add("resources/Turret.png");
        
        images = loader.loadImages(imagePaths);
        
        images = Calculator.toCompatibleImages(images);
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        
    }
    
    public void update(int angleToRotate)
    {
        
    }
}
