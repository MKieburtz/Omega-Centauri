package MainPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
abstract class Shot {

    protected int range;
    protected int life;
    protected int damage;
    protected MediaLoader imageLoader = new MediaLoader();
    protected boolean animated;
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected ArrayList<String> imagePaths = new ArrayList<String>();
    protected BufferedImage activeImage;
    protected Point2D.Double location;
    protected double faceAngle;
    protected Point2D.Double velocity;
    protected int maxVel;
    protected Rectangle2D.Double hitbox;

    protected void draw(Graphics2D g2d, Point2D.Double cameraLocation) // ovveride method if needed
    {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform) original.clone();

        transform.setToIdentity();

        transform.rotate(Math.toRadians(faceAngle),
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x,
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y);
        
        g2d.setTransform(transform);
        
        g2d.drawImage(activeImage, (int)Calculator.getScreenLocation(cameraLocation, location).x,
               (int)Calculator.getScreenLocation(cameraLocation, location).y, null);
        
        updateHitbox(cameraLocation);
        
        
        g2d.setTransform(original);

    }

    protected void loadImages(ArrayList<String> imagePaths) {
        images = imageLoader.loadImages(imagePaths);
    }

    protected void updateLocation() {
        location.x += velocity.x;
        location.y += velocity.y;


    }
    
    
    public void setUpHitbox(Point2D.Double cameraLocation) {
        try {
            hitbox = new Rectangle2D.Double(Calculator.getScreenLocation(cameraLocation, location).x,
                    Calculator.getScreenLocation(cameraLocation, location).y,
                    images.get(0).getWidth(), images.get(0).getHeight());
        } catch (NullPointerException e) {
            System.err.println("activeimage not initialized!");
        }
    }

    protected void updateHitbox(Point2D.Double cameraLocation) {
        hitbox.x = Calculator.getScreenLocation(cameraLocation, location).x;
        hitbox.y = Calculator.getScreenLocation(cameraLocation, location).y;
    }
    
    public Rectangle2D.Double returnHitbox()
    {
        return hitbox;
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public Point getSize() // ovveride if animated
    {
        return new Point(images.get(0).getWidth(), images.get(0).getHeight());
    }

    public BufferedImage getImage() {
        return images.get(0);
    }

    private Point2D.Double getScreenLocationMiddle(Point2D.Double cameraLocation) {
        return new Point2D.Double((location.x - cameraLocation.x) + (images.get(0).getWidth() / 2),
                (location.y - cameraLocation.y) + (images.get(0).getHeight() / 2));
    }

    public boolean outsideScreen() // assumes a 20 thousand by 20 thousand screen
    {
        if (location.x < 10000 && location.x > -10000) {
            if (location.y < 10000 && location.y > -10000) {
                return false;
            }
        }
        return true;
    }

    public boolean imagesLoaded() {
        return !images.isEmpty();
    }
    
    public int getDamage()
    {
        return damage;
    }
}
