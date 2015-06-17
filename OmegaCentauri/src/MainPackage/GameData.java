package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class GameData {
    public static volatile Point2D.Double cameraLocation = new Point2D.Double();
    
    public void updateCameraLocation(int x, int y)
    {
        cameraLocation.x = x;
        cameraLocation.y = y;
    }
    
    public Point2D.Double getCameraLocation()
    {
        return cameraLocation;
    }
}
