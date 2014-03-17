package MainPackage;

import java.awt.*;
import java.awt.geom.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Camera {

    private Point size;
    private Point2D.Double location;
    private Rectangle2D.Double screenRect;

    public Camera(int width, int height) {
        size = new Point(width, height);
        location = new Point2D.Double(0.0, 0.0);
        screenRect = new Rectangle2D.Double(location.x, location.y, size.x, size.y);
    }

    public void move(double x, double y) {
        location.x = x;
        location.y = y;
        updateRect();
    }

    public Point2D.Double getLocation() {
        return this.location;
    }

    public Point getSize() {
        return this.size;
    }

    public void setSize(int x, int y) {
        size.x = x;
        size.y = y;
        updateRect();
    }

    public boolean insideView(Rectangle2D.Double r) {
       return screenRect.intersects(r);
    }
    
    private void updateRect()
    {
        screenRect.x = location.x;
        screenRect.y = location.y;
        screenRect.width = size.x;
        screenRect.height = size.y;
    }
}
