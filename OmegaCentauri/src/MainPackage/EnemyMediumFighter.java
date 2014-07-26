package MainPackage;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public class EnemyMediumFighter  extends EnemyShip {
    
    private int id;
    
    
    public EnemyMediumFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel, double maxAngleVelocity,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int shootingDelay, int health, int id)
    {
        super(x, y, shipType, baseMaxVel, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelay, health);
        
        this.id = id;
        
        imagePaths.add("resources/MediumEnemyFighter.png");
        
        images = Calculator.toCompatibleImages(mediaLoader.loadImages(imagePaths));
        activeImage = images.get(0);
        
        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), true, new Point(activeImage.getWidth(), activeImage.getHeight()), 15, 50);

        setUpHitbox(cameraLocation);
        
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) {
    }
   
    
    @Override
    public void draw(Graphics2D g2d, Camera camera)
    {
        super.draw(g2d, camera);
    }

    @Override
    public void update(Player player, Point2D.Double cameraLocation, ArrayList<EnemyShip> otherShips) {
        
        double distance = Calculator.getDistance(location, player.getLocation());
        
        double angleToPlayer = Calculator.getAngleBetweenTwoPoints(location, player.getLocation());
        
        rotateToAngle(angleToPlayer);
        
        move(ShipState.Thrusting);
    }

    @Override
    public int getID() {
        return id;
    }
    
    @Override
    public Point2D.Double getLocation()
    {
        return location;
    }
    
}
