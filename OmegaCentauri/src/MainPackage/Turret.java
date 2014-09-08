package MainPackage;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Kieburtz
 */
public class Turret {

    private BufferedImage activeImage;
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

    private int maxDurability;
    private int durability;

    private int maxRotation;
    private int minRotation;

    private double angle; // angle of rotation
    private double displayAngle; // angle of rotation + the angle of rotation of the ship that the turret is on.
    //This is the angle that shots are fired from.

    private final Point2D.Double distanceFromCenter;
    private Dimension imageDimensions;

    private Point2D.Double rotationPoint = new Point2D.Double();
    private Point2D.Double shotSpawnPoint = new Point2D.Double();
    private Point2D.Double distanceToShotSpawnPoint = new Point2D.Double();
    private double angleFromCenter;

    private final int angularVelocity = 3;
    private boolean canShoot = false;
    private int shootingDelay;

    private final int TURRETIMAGE = 0;
    
    private Ship owner;
    
    private ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();

    public Turret(int maxDurability, int maxRotation, int minRotation, Point2D.Double distanceFromCenter,
            Dimension imageDimensions, Point2D.Double cameraLocation, Point2D.Double distanceToShotSpawnPoint,
            double angleFromCenter, int shootingDelay, double shipAngle, Ship owner) {
        this.maxDurability = maxDurability;
        this.durability = maxDurability;

        this.maxRotation = maxRotation;
        this.minRotation = minRotation;
        
        this.shootingDelay = shootingDelay;

        angle = Math.abs(-minRotation - maxRotation);

        angle = Calculator.confineAngleToRange(angle);

        displayAngle = angle + shipAngle;
        
        displayAngle = Calculator.confineAngleToRange(displayAngle);
        
        this.distanceFromCenter = distanceFromCenter;
        this.imageDimensions = imageDimensions;
        this.distanceToShotSpawnPoint = distanceToShotSpawnPoint;
        rotationPoint = distanceFromCenter;
        this.angleFromCenter = angleFromCenter;
        
        images = Resources.getImagesForTurret();

        activeImage = images.get(TURRETIMAGE);
        
        ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
        
        this.owner = owner;
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double entityLocation) {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = new AffineTransform();

        transform.rotate(Math.toRadians(360 - angle), rotationPoint.x + activeImage.getWidth() / 2 - 2.5, rotationPoint.y + activeImage.getHeight() / 2);

        g2d.transform(transform);

        g2d.drawImage(activeImage, (int) distanceFromCenter.x, (int) distanceFromCenter.y, null);

        g2d.setTransform(original);
    }

    public void update(Point2D.Double playerLocation, Point2D.Double shipLocationMiddle, double shipAngle, Point2D.Double cameraLocation) {
        shotSpawnPoint.x = shipLocationMiddle.x + distanceToShotSpawnPoint.x * Math.cos(Math.toRadians(360 - shipAngle + angleFromCenter));

        shotSpawnPoint.y = shipLocationMiddle.y + distanceToShotSpawnPoint.y * Math.sin(Math.toRadians(360 - shipAngle + angleFromCenter));

        double targetAngle = 360 - (shipAngle - Calculator.getAngleBetweenTwoPoints(shotSpawnPoint, playerLocation));

        targetAngle = Calculator.confineAngleToRange(targetAngle);

        rotateToAngle(targetAngle, shipAngle);

    }

    private void rotateToAngle(double angleToRotate, double shipAngle) {
        double[] distances = Calculator.getDistancesBetweenAngles(angle, angleToRotate);

        double nextAngle = distances[0] < distances[1] ?
                               angle + angularVelocity : 
                               angle - angularVelocity;
        
        if (!(nextAngle < maxRotation && nextAngle > minRotation)) {
            if (distances[0] < distances[1]) {
                angle += angularVelocity;
            } else {
                angle -= angularVelocity;
            }
            
            angle = Calculator.confineAngleToRange(angle);
            
            displayAngle = angle + shipAngle;
            
            displayAngle = Calculator.confineAngleToRange(displayAngle);
        }

    }
    
    public Shot shoot(Point2D.Double cameraLocation, Point2D.Double velocity) { // ASSUMES THAT CANSHOOT IS TESTED BEFORE THIS IS CALLED!
        
        Point2D.Double shotStartingPos = (Point2D.Double) shotSpawnPoint.clone();

        Point2D.Double shotStartingVel = new Point2D.Double(velocity.x + 20 * Calculator.CalcAngleMoveX(360 - displayAngle),
                velocity.y + 20 * Calculator.CalcAngleMoveY(360 - displayAngle));
        canShoot = false;
        ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
        
        return new TurretShot(10, shotStartingPos, shotStartingVel, 360 - displayAngle, cameraLocation, owner);
    }
    
    public boolean canShoot()
    {
        return canShoot;
    }
    
    class ShootingService implements Runnable
    {
        @Override
        public void run() {
            canShoot = true;
        }
    }
}
