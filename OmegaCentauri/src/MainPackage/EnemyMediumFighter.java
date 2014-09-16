package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Kieburtz
 */
public class EnemyMediumFighter extends EnemyShip {

    private int id;

    private Turret[] turrets = new Turret[2];
    private int shootingDelayMissile;

    private ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();

    public EnemyMediumFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel, double maxAngleVelocity,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int shootingDelayTurret, int shootingDelayMissile, int health, int id, Player player) {
        super(x, y, shipType, baseMaxVel, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelayTurret, health);

        this.id = id;

        images = Resources.getImagesForMediumEnemyFighter();
        activeImage = images.get(0);

        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), true, new Point(activeImage.getWidth(), activeImage.getHeight()), 15, 50);

        setUpHitbox(cameraLocation);

        turrets[0] = new Turret(25, 335, 45, new Point2D.Double(93, 115), new Dimension(activeImage.getWidth(), activeImage.getHeight()),
                cameraLocation, new Point2D.Double(65, 70), 65, shootingDelayTurret, faceAngle, this);

        turrets[1] = new Turret(25, 315, 35, new Point2D.Double(93, 240), new Dimension(activeImage.getWidth(), activeImage.getHeight()),
                cameraLocation, new Point2D.Double(95, 75), -65, shootingDelayTurret, faceAngle, this);

        this.targetShip = player;
        
        this.shootingDelayMissile = shootingDelayMissile;

        ex.schedule(new ShootingService(), shootingDelayMissile, TimeUnit.MILLISECONDS);
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) {
        for (Turret t : turrets) {
            if (t.canShoot()) {
                shots.add(t.shoot(cameraLocation, movementVelocity));
            }
        }
        
        if (canshoot) {

            double angle = faceAngle;

            Point2D.Double startingLocation = Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight());
            
            shots.add(new Missile(60, startingLocation, null, 360 - angle, cameraLocation, targetShip, this));

            canshoot = false;

            ex.schedule(new ShootingService(), shootingDelayMissile, TimeUnit.MILLISECONDS);
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

        //g2d.draw(hitbox);
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

    class ShootingService implements Runnable {

        @Override
        public void run() {
            canshoot = true;
        }
    }

}
