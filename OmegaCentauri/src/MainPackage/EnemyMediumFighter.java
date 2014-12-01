package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Kieburtz
 */
public class EnemyMediumFighter extends EnemyShip {

    private int id;

    private Turret[] turrets = new Turret[2];
    private int shootingDelayMissile;
    private int shootingDelayTurret;

    private Resources resources;
    
    private boolean right = true;
    
    private boolean canShootMissile = true;
    private boolean canShootTurret = true;
    
    public EnemyMediumFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel, double maxAngleVelocity,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int shootingDelayTurret, int shootingDelayMissile, int health, int id, Player player, Resources resources) {
        super(x, y, shipType, baseMaxVel, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelayTurret, health);

        this.id = id;
        this.resources = resources;
       
        activeImage = resources.getImageForObject("resources/MediumEnemyFighter.png");

        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), true,
                new Point(activeImage.getWidth(), activeImage.getHeight()), 15, 500, resources, false);

        setUpHitbox(cameraLocation);

        turrets[0] = new Turret(25, 335, 45, new Point2D.Double(93, 115), new Dimension(activeImage.getWidth(), activeImage.getHeight()),
                cameraLocation, new Point2D.Double(65, 70), 65,faceAngle, this, resources);

        turrets[1] = new Turret(25, 315, 35, new Point2D.Double(93, 240), new Dimension(activeImage.getWidth(), activeImage.getHeight()),
                cameraLocation, new Point2D.Double(95, 75), -65, faceAngle, this, resources);

        this.targetShip = player;
        
        this.shootingDelayMissile = shootingDelayMissile;
        this.shootingDelayTurret = shootingDelayTurret;

        ex.schedule(new ShootingService(), shootingDelayMissile, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void setUpHitbox(Point2D.Double cameraLocation)
    {
        try {
            shieldHitbox = new EllipseHitbox(activeImage.getWidth() + 50, activeImage.getHeight() + 50); // constants added to the end compensate for the wings
            Point2D.Double points[] = new Point2D.Double[8];
            points[0] = new Point2D.Double(27, 0);
            points[1] = new Point2D.Double(activeImage.getWidth(), 0);
            points[2] = new Point2D.Double(activeImage.getWidth(), activeImage.getHeight());
            //points[3] = new Point2D.Double(27, activeImage.getHeight())
            //hullHitbox = new ShapeHitbox(points, location);
        } catch (NullPointerException ex) {
            System.err.println("active image not initialized!");
        }
    }
    
    @Override
    protected void updateHitbox(Point2D.Double cameraLocation)
    {
        super.updateHitbox(cameraLocation);
        hullHitbox.moveToLocation(Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()));
        hullHitbox.rotateToAngle(faceAngle);
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) {
        if (canShootTurret)
        {
            if (right)
            {
                shots.add(turrets[0].shoot(cameraLocation, movementVelocity));
                right = !right;
            }
            else
            {
                shots.add(turrets[1].shoot(cameraLocation, movementVelocity));
                right = !right;
            }
            canShootTurret = false;
            ex.schedule(new ShootingServiceTurrets(), shootingDelayTurret, TimeUnit.MILLISECONDS);
        }
        
        if (canShootMissile) {

            double angle = faceAngle;

            Point2D.Double startingLocation = Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight());
            
            shots.add(new Missile(60, startingLocation, null, 360 - angle, cameraLocation, targetShip, this, resources));

            canShootMissile = false;

            ex.schedule(new ShootingServiceMisisle(), shootingDelayMissile, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        AffineTransform original = g2d.getTransform();

        super.draw(g2d, camera);

        for (Turret t : turrets) {
            t.draw(g2d, camera.getLocation(), location);
        }

        g2d.setTransform(original);

        shield.draw(g2d, camera.getLocation(), location, 
                new Point2D.Double(Calculator.getScreenLocationMiddle(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y),
                new Point2D.Double(Calculator.getScreenLocationMiddle(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y), original,
                    faceAngle);
//        g2d.setColor(Color.red);
//        hitbox.draw(g2d, camera.getLocation());
    }

    @Override
    public void update(Player player, Point2D.Double cameraLocation, ArrayList<EnemyShip> otherShips) {

        double distance = Calculator.getDistance(location, player.getLocation());

        double angleToPlayer = Calculator.getAngleBetweenTwoPoints(Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()),
                player.getLocation());

        rotateToAngle(angleToPlayer);
        
        for (Turret t : turrets) {
            t.update(Calculator.getGameLocationMiddle(player.getLocation(), player.getActiveImage().getWidth(), player.getActiveImage().getHeight()),
                    Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()),
                    faceAngle, cameraLocation);

        }

        if (Math.abs(angleToPlayer - faceAngle) <= 45) {
            shoot(cameraLocation);
        }

        if (distance > 500) {
            move(ShipState.Thrusting);
        } else {
            move(ShipState.Drifting);
        }
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Point2D.Double getLocation() {
        return location;
    }

    class ShootingServiceMisisle implements Runnable {

        @Override
        public void run() {
            canShootMissile = true;
        }
    }

    class ShootingServiceTurrets implements Runnable {

        @Override
        public void run() {
            canShootTurret = true;
        }
    }
}
