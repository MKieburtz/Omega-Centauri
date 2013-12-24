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
    protected double angle;
    
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
    
    public Point2D.Double CalcPositionToStart(Point2D.Double centerLocation, double radius, double shipAngle)
    {
         /*
         * a point on the outer edge of a circle given the center of a rectangle
         * bounding box (cx, cy), the radius (r) and the angle where the ship is pointing
         * (a) is
         * x = cx + r + Math.cos(Math.toRadians(a));
         * y = cy + r + Math.sin(Math.toRadians(a));
         * 
         * 
         */
        
        double x = centerLocation.x + radius + (Math.cos(Math.toRadians(angle)));
        double y = centerLocation.y + radius + (Math.sin(Math.toRadians(angle)));
        
        return new Point2D.Double(x, y);
    }
}
