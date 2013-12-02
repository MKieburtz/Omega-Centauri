package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
//@author Michael Kieburtz

public class DustChunk {
    private Point2D.Double[] locations = new Point2D.Double[10];
    private final int dimension = 2;
    private Rectangle2D.Double[] rects = new Rectangle2D.Double[10];
    private Random random = new Random();
    private Point2D.Double chunkLocation;
    
    public DustChunk(double x, double y)
    {   
        chunkLocation = new Point2D.Double(x, y);
        for (int i = 0; i < 10; i++)
        {
            locations[i] = new Point2D.Double((double)random.nextInt(100), (double)random.nextInt(100));
            locations[i].x *= x + 1;
            locations[i].y *= y + 1;
            rects[i] = new Rectangle2D.Double(locations[i].x, locations[i].y, dimension, dimension);
        }
        
        
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {   
        g2d.setColor(Color.WHITE);
        
        for (int i = 0; i < 10; i++)
        {
            rects[i].x = rects[i].x - cameraLocation.x;
            rects[i].y = rects[i].y - cameraLocation.y;
            g2d.draw(rects[i]);
        }
    }
    
    public Point2D.Double getLocation()
    {
        return chunkLocation;
    }
}
