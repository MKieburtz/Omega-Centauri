package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Kieburtz
 */
public class EnemyMediumFighter extends EnemyShip {

    private int id;

    private Turret[] turrets = new Turret[2];

    public EnemyMediumFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel, double maxAngleVelocity,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int shootingDelay, int health, int id) {
        super(x, y, shipType, baseMaxVel, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelay, health);

        this.id = id;

        imagePaths.add("resources/MediumEnemyFighter.png");

        images = Calculator.toCompatibleImages(mediaLoader.loadImages(imagePaths));
        activeImage = images.get(0);

        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), true, new Point(activeImage.getWidth(), activeImage.getHeight()), 15, 50);

        setUpHitbox(cameraLocation);

        turrets[0] = new Turret(25, 335, 45, new Point2D.Double(93, 115), new Dimension(activeImage.getWidth(), activeImage.getHeight()), cameraLocation, new Point2D.Double(70, 70));
        turrets[1] = new Turret(25, 315, 25, new Point2D.Double(93, 240), new Dimension(activeImage.getWidth(), activeImage.getHeight()), cameraLocation, new Point2D.Double(-70, 70));
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) {
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        AffineTransform original = g2d.getTransform();
        
        super.draw(g2d, camera);
                
        for (Turret t : turrets) {
            t.draw(g2d, camera.getLocation(), location);
        }
        
        g2d.setTransform(original);
    }

    @Override
    public void update(Player player, Point2D.Double cameraLocation, ArrayList<EnemyShip> otherShips) {

        double distance = Calculator.getDistance(location, player.getLocation());

        double angleToPlayer = Calculator.getAngleBetweenTwoPoints(location, player.getLocation());

        rotateToAngle(angleToPlayer);
        for (Turret t : turrets) {
            t.update(Calculator.getGameLocationMiddle(player.getLocation(), player.getActiveImage().getWidth(), player.getActiveImage().getHeight()),
                    Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()),
                    faceAngle, cameraLocation);
            
            if (canshoot)
            {
                shots.add(t.shoot(cameraLocation, movementVelocity));
                canshoot = false;
                ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
            }
            
        }

        move(ShipState.Thrusting);
        
       
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Point2D.Double getLocation() {
        return location;
    }

}
