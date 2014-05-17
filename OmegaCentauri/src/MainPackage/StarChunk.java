package MainPackage;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class StarChunk {

    protected Point2D.Double location;
    protected Ellipse2D.Double[] stars = new Ellipse2D.Double[3];
    protected Rectangle2D.Double boundingRect;
    protected Random random = new Random();
    
    
    public StarChunk(double x, double y) {
        location = new Point2D.Double(x, y);
        double num1 = 0, num2 = 0;
        for (int i = 0; i < 3; i++) {
            num1 = ((double) random.nextInt((int) ((x + 100) - x)) + x);
            num2 = ((double) random.nextInt((int) ((y + 100) - y)) + y);
            stars[i] = new Ellipse2D.Double(num1, num2, 1, 1);
        }
        
        boundingRect = new Rectangle2D.Double(location.x, location.y, 100, 100);
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation) {
        for (int i = 0; i < 3; i++) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval((int) (stars[i].x - cameraLocation.x), (int) (stars[i].y - cameraLocation.y), 1, 1);
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
