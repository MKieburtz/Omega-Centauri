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

    public Camera(int width, int height) {
        size = new Point(width, height);
        location = new Point2D.Double(0.0, 0.0);
    }

    public void move(double x, double y) {
        location.x = x;
        location.y = y;
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
    }

    public boolean insideView(Point2D.Double point, Point size) {
        // use nested if statements because the conditionals are so long.

        double x = point.x - location.x + size.x;
        double y = point.y - location.y + size.y;
        
        
        if (x > 0 && x <= this.size.x + size.x) {
            if (y > 0 && y <= this.size.y + size.y) {
                return true;
            }
        }
        return false;
    }
}
