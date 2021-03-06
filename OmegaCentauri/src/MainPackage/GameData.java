package MainPackage;

import java.awt.Point;
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
    
    public static volatile ArrayList<Enemy> enemyShips = new ArrayList<>();
    
    public void updateEnemyShips(ArrayList<Enemy> newShips)
    {
        enemyShips = newShips;
    }
    
    public ArrayList<Enemy> getEnemyShips()
    {
        return enemyShips;
    }
    
    public static volatile ArrayList<Ally> allyShips = new ArrayList<>();
    
    public void updateAllyShips(ArrayList<Ally> newShips)
    {
        allyShips = newShips;
    }
    
    public ArrayList<Ally> getAllyShips()
    {
        return allyShips;
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
    
    public static volatile Point cameraSize;
    
    public void updateCameraSize(Point newSize)
    {
        cameraSize = newSize;
    }
    
    public Point getCameraSize()
    {
        return cameraSize;
    }
}
