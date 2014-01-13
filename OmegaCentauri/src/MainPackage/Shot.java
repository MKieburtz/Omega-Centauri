package MainPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
abstract class Shot {

    protected int range;
    protected int life;
    protected int damage;
    protected MediaLoader imageLoader = new MediaLoader();
    protected boolean animated;
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected ArrayList<String> imagePaths = new ArrayList<String>();
    protected Point2D.Double location;
    protected double faceAngle;
    protected Point2D.Double velocity;
    protected int maxVel;

    protected void draw(Graphics2D g2d, Point2D.Double cameraLocation) // ovveride method if needed
    {
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform) original.clone();
        
        transform.rotate(Math.toRadians(faceAngle), getScreenLocationMiddle(cameraLocation).x, getScreenLocationMiddle(cameraLocation).y);
        
        g2d.setTransform(transform);
        
        g2d.drawImage(images.get(0), (int) (location.x - cameraLocation.x),
                (int) (location.y - cameraLocation.y), null);
        
        g2d.setTransform(original);
        
    }

    protected void loadImages(ArrayList<String> imagePaths) {
        images = imageLoader.loadImages(imagePaths);
    }

    protected void updateLocation() {
        location.x += velocity.x;
        location.y += velocity.y;
    }

    
    public Point2D.Double getLocation()
    {
        return location;
    }
    
    public Point getSize() // ovveride if animated
    {
        return new Point(images.get(0).getWidth(), images.get(0).getHeight());
    }
    
    public BufferedImage getImage()
    {
        return images.get(0);
    }
    
    private Point2D.Double getScreenLocationMiddle(Point2D.Double cameraLocation)
    {
        return new Point2D.Double((location.x - cameraLocation.x) + (images.get(0).getWidth() / 2),
                (location.y - cameraLocation.y) + (images.get(0).getHeight() / 2));
    }
}
