package MainPackage;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public class GameData {
    public static volatile Point2D.Double cameraLocation = new Point2D.Double();
    
    public void updateCameraLocation(double x, double y)
    {
        cameraLocation.x = x;
        cameraLocation.y = y;
    }
    
    public Point2D.Double getCameraLocation()
    {
        return cameraLocation;
    }
    
    public static volatile ArrayList<Ship> allShips = new ArrayList<>();
    
    public void updateShips(ArrayList<Ship> newShips)
    {
        allShips = newShips;
    }
    
    public ArrayList<Ship> getShips()
    {
        return allShips;
    }
}
