package MainPackage;

import java.awt.*;
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
    protected ImageLoader imageLoader = new ImageLoader();
    protected boolean animated;
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected ArrayList<String> imagePaths = new ArrayList<String>();
    protected Point2D.Double location;
    protected double angle;
    protected Point velocity;
    protected int maxVel;

    protected void draw(Graphics2D g2d, Point2D.Double cameraLocation) // ovveride method if needed
    {

        g2d.drawImage(images.get(0), (int) (location.x - cameraLocation.x),
                (int) (location.y - cameraLocation.y), null);
    }

    protected void loadImages(ArrayList<String> imagePaths) {
        images = imageLoader.loadImages(imagePaths);
    }

    protected void updateLocation() {
        location.x += velocity.x;
        location.y += velocity.y;
    }

    protected void move() {
        double moveAngle = angle - 90;

        velocity.x += Calculator.CalcAngleMoveX(moveAngle);

        if (velocity.x > maxVel) {
            velocity.x = maxVel;
        } else if (velocity.x < -maxVel) {
            velocity.x = -maxVel;
        }

        velocity.y += Calculator.CalcAngleMoveY(moveAngle);

        if (velocity.y > maxVel) {
            velocity.y = maxVel;
        } else if (velocity.y < -maxVel) {
            velocity.y = -maxVel;
        }
        
        updateLocation();
    }
    
    public Point2D.Double getLocation()
    {
        return location;
    }
    
    public Point getSize() // ovveride if animated
    {
        return new Point(images.get(0).getWidth(), images.get(0).getHeight());
    }
}
