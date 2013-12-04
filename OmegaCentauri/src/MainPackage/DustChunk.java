package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
//@author Michael Kieburtz

public class DustChunk {
    private Point2D.Double location;
    private final int dimension = 2;
    private Rectangle2D.Double[] rects = new Rectangle2D.Double[10];
    private Random random = new Random();
    
    public DustChunk(double x, double y)
    {
        location = new Point2D.Double(x, y);
        double num1, num2 = 0;
        for (int i = 0; i < 3; i++)
        {
            num1 = ((double)random.nextDouble() + 1) * (x + 100);
            num2 = ((double)random.nextDouble() + 1) * (y + 100);
            rects[i] = new Rectangle2D.Double(num1, num2, dimension, dimension);
        }
    }
    
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {   
        for (int i = 0; i < 3; i++)
        {
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int)(rects[i].x - cameraLocation.x), (int)(rects[i].y - cameraLocation.y), dimension, dimension);
        }
    }
    
    public Point2D.Double getLocation()
    {
        return location;
    }
    
    public Rectangle2D.Double[] stars()
    {
        return rects;
    }
    
    public Point getSize()
    {
        return new Point(100, 100);
    }
}
