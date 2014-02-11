package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
import java.awt.geom.Point2D;

public abstract class EnemyShip extends Ship {

    public EnemyShip(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay) // delegate assigning images to the types of ships
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay);
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

        double dist1 = 0, dist2 = 0;


        if (faceAngle > targetAngle) {
            if (faceAngle + 180 >= 360) // CASE 1
            {
                dist1 = Math.abs((360 - faceAngle) + targetAngle);
                dist2 = Math.abs(faceAngle - targetAngle);
            } else if (faceAngle + 180 < 360) // CASE 2
            {
                dist1 = Math.abs((360 - targetAngle) + faceAngle);
                dist2 = Math.abs(targetAngle - faceAngle);
            }
        } else if (targetAngle > faceAngle) {
            if (targetAngle + 180 >= 360) // CASE 3
            {
                dist1 = Math.abs(targetAngle - faceAngle);
                dist2 = Math.abs(360 - (targetAngle - faceAngle));
            } else if (targetAngle + 180 < 360) // CASE 4
            {
                dist1 = Math.abs(targetAngle - faceAngle);
                dist2 = Math.abs(360 - (targetAngle - faceAngle));
            }
        }
        
        
        //System.out.println(dist1 + " " + dist2 + " " + targetAngle + " " + faceAngle);

        if (dist1 < dist2) {
            if (dist1 > angleIcrement) {
                rotate(ShipState.TurningRight);
            }
        } else {
            if (dist2 > angleIcrement) {
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

        Point2D.Double ShotStartingPos = new Point2D.Double(getScreenLocationMiddle(cameraLocation).x - 5
                + Calculator.CalcAngleMoveX(faceAngle - 90) * 20,
                getScreenLocationMiddle(cameraLocation).y - 10 + Calculator.CalcAngleMoveY(faceAngle - 90) * 20);


        shots.add(new PulseShot(5, 100, false, ShotStartingPos, ShotStartingVel, faceAngle, true));
        canshoot = false;
        shootingTimer.schedule(new ShootingTimerTask(), shootingDelay);
    }
}
