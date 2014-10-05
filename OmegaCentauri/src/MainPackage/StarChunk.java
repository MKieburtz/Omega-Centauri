package MainPackage;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class StarChunk {

    protected Point2D.Double location;
    protected Ellipse2D.Double[] stars;
    protected Rectangle2D.Double boundingRect;
    protected Random random = new Random();
    
    
    public StarChunk(double x, double y, int size, int amount) {
        stars = new Ellipse2D.Double[amount];
        location = new Point2D.Double(x, y);
        double num1 = 0, num2 = 0;
        for (int i = 0; i < stars.length; i++) {
            num1 = ((double) random.nextInt((int) ((x + size) - x)) + x);
            num2 = ((double) random.nextInt((int) ((y + size) - y)) + y);
            stars[i] = new Ellipse2D.Double(num1, num2, 1, 1);
        }
        
        boundingRect = new Rectangle2D.Double(location.x, location.y, size, size);
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation) {
        for (Ellipse2D.Double star : stars) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval((int) (star.x - cameraLocation.x), (int) (star.y - cameraLocation.y), 1, 1);
        }
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public Point getSize() {
        return new Point(100, 100);
    }
    
    public Rectangle2D.Double getBoundingRect()
    {
        return boundingRect;
    }
}
