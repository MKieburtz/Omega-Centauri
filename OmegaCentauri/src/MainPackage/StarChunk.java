package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class StarChunk {

    protected Point2D.Double location;
    protected final int dimension = 1;
    protected Rectangle2D.Double[] rects = new Rectangle2D.Double[3];
    protected Rectangle2D.Double boundingRect;
    protected Random random = new Random();

    public StarChunk(double x, double y) {
        location = new Point2D.Double(x, y);
        double num1 = 0, num2 = 0;
        for (int i = 0; i < 3; i++) {
            num1 = ((double) random.nextInt((int) ((x + 100) - x)) + x);
            num2 = ((double) random.nextInt((int) ((y + 100) - y)) + y);
            rects[i] = new Rectangle2D.Double(num1, num2, dimension, dimension);
        }
        
        boundingRect = new Rectangle2D.Double(location.x, location.y, 100, 100);
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation) {
        for (int i = 0; i < 3; i++) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval((int) (rects[i].x - cameraLocation.x), (int) (rects[i].y - cameraLocation.y), dimension, dimension);
        }
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public Rectangle2D.Double[] getStars() {
        return rects;
    }

    public Point getSize() {
        return new Point(100, 100);
    }
    
    public Rectangle2D.Double getBoundingRect()
    {
        return boundingRect;
    }
}
