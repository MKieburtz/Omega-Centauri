package MainPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;
import javax.sound.sampled.Clip;

// @author Michael Kieburtz and Davis Freeman
public abstract class Ship {

    protected int hull;
    protected int fuel;
    protected int power;
    protected Type type;
    protected double faceAngle = 360.0; // maybe move to Ship Class
    protected double moveAngle = 0.0;
    protected Point2D.Double location;
    protected Point2D.Double nextLocation;
    // File -> FileInputStream -> ImageIO -> buffered image
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected ArrayList<Clip> sounds = new ArrayList<Clip>();
    
    protected BufferedImage activeImage;
    protected ArrayList<String> imagePaths = new ArrayList<String>();
    protected ArrayList<String> soundPaths = new ArrayList<String>();
    protected MediaLoader mediaLoader = new MediaLoader();
    
    protected ArrayList<Shot> shots = new ArrayList<Shot>();
    
    public Ship(int x, int y, Type shipType)
    {
        location = new Point2D.Double(x, y);
        nextLocation = new Point2D.Double();
        type = shipType;
    }
    
    public BufferedImage getImage() {
        return activeImage;
    }

    public BufferedImage getImage(int index) {
        return this.images.get(index);
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform)original.clone();
        
        transform.setToIdentity();
        transform.rotate(Math.toRadians(faceAngle),
                getScreenLocation(cameraLocation).x + activeImage.getWidth() / 2,
                getScreenLocation(cameraLocation).y + activeImage.getHeight() / 2);
        
        transform.translate(getScreenLocation(cameraLocation).x, getScreenLocation(cameraLocation).y);
        
        transform.scale(1, 1);
        
        g2d.drawImage(activeImage, transform, null);
        
    }
    
    public Point2D.Double getScreenLocation(Point2D.Double cameraLocation)
    {
        double x = location.x - cameraLocation.x;
        double y = location.y - cameraLocation.y;
        
        return new Point2D.Double(x, y);
    }
    

}
