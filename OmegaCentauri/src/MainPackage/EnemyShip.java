package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class EnemyShip extends Ship {

    public EnemyShip(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay, int health) // delegate assigning images to the types of ships
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health);
        
        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), true);
        
    }

    protected void update(Point2D.Double playerLocation, Point2D.Double cameraLocation) {
        // main AI goes here
        
        // move in the direction of the ship if it is far away
        // and shoot if it is in range.

        double distance = Calculator.getDistance(location, playerLocation);

        double angle = Calculator.getAngleBetweenTwoPoints(playerLocation, location);

        //System.out.println(angle);
        RotateToPlayer(angle);

        if (distance < 500 && Math.abs((360 - angle) - faceAngle) < 45 && canshoot) {
            shoot(cameraLocation);
        }

        if (distance > 200) {
            move(ShipState.Thrusting);
        } else {
            move(ShipState.Drifting);
        }
    }

    protected void RotateToPlayer(double angle) {

        double targetAngle = 360 - angle;
        
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, targetAngle);
        
        
        //System.out.println(dist1 + " " + dist2 + " " + targetAngle + " " + faceAngle);

        if (distances[0] < distances[1]) {
            if (distances[0] > angleIcrement) {
                rotate(ShipState.TurningRight);
            }
        } else {
            if (distances[1] > angleIcrement) {
                rotate(ShipState.TurningLeft);
            }
        }
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) {
        // playSound(0);

        Point2D.Double ShotStartingVel =
                new Point2D.Double(velocity.x + Calculator.CalcAngleMoveX(faceAngle - 90) * 20,
                velocity.y + Calculator.CalcAngleMoveY(faceAngle - 90) * 20);

        Point2D.Double ShotStartingPos = new Point2D.Double(Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x - 5
                + Calculator.CalcAngleMoveX(faceAngle - 90) * 20,
                Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y - 10 + Calculator.CalcAngleMoveY(faceAngle - 90) * 20);


        shots.add(new PulseShot(5, 100, false, ShotStartingPos, ShotStartingVel, faceAngle, true, cameraLocation));
        canshoot = false;
        shootingTimer.schedule(new ShootingTimerTask(), shootingDelay);
    }
    
    @Override
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        super.draw(g2d, cameraLocation);
        shield.draw(g2d, cameraLocation, location);
    }
    
    public void activateShield()
    {
        shield.activate();
    }
}
