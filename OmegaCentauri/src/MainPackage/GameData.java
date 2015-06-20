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
    
    public static volatile ArrayList<EnemyShip> enemyShips = new ArrayList<>();
    
    public void updateShips(ArrayList<EnemyShip> newShips)
    {
        enemyShips = newShips;
    }
    
    public ArrayList<EnemyShip> getEnemyShips()
    {
        return enemyShips;
    }
    
    public static volatile Ship playerShip;
    
    public void updatePlayer(Ship controllingShip)
    {
        playerShip = controllingShip;
    }
    
    public Ship getPlayerShip()
    {
        return playerShip;
    }
    
    public static Resources resources;
    
    public void updateResources(Resources r) // should only be called once!
    {
        resources = r;
    }
    
    public Resources getResources()
    {
        return resources;
    }
}
