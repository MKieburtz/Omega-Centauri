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
        for (int i = 0; i < 10; i++)
        {
            num1 = (double)random.nextInt(2) * 100;
            num2 = (double)random.nextInt(2) * 100;
            rects[i] = new Rectangle2D.Double(num1, num2, dimension, dimension);
            System.out.println(rects[i] + " " + i);
        }
    }
    
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {   
        for (int i = 0; i < 10; i++)
        {
        rects[i].x = location.x - cameraLocation.x;
        rects[i].y = location.y - cameraLocation.y;
        
        g2d.setColor(Color.GRAY);
        g2d.fill(rects[i]);
            
        }
    }
    
    public Point2D.Double getLocation()
    {
        return location;
    }
}
