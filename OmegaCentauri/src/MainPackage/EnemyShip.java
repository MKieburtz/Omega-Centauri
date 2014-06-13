package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class EnemyShip extends Ship {

    public EnemyShip(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay, int health) // delegate assigning images to the types of ships
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health);
    }

    protected void update(Point2D.Double playerLocation, Point2D.Double cameraLocation, double playerAngle) {
        shield.regenRate = .05;
        // main AI goes here

        // move in the direction of the ship if it is far away
        // and shoot if it is in range.
        double distance = Calculator.getDistance(location, playerLocation);

        double angle = Calculator.getAngleBetweenTwoPoints(location, playerLocation);
        //System.out.println(angle + " " + faceAngle);
        
        //System.out.println(angle);
        if (hull > 30) {
            RotateToPlayer(angle);
            
            if (distance < 500 && Math.abs(angle - faceAngle) < 45) {
                shoot(cameraLocation);
            }
            if (distance > 200) {
                move(ShipState.Thrusting);
            } else {
                move(ShipState.Drifting);
            }
        } else {
            RotateFromPlayer(angle);
            if (distance > 500) {
                move(ShipState.Drifting);
            } else {
                move(ShipState.Thrusting);
            }
            
            }
        if (shield.getHealth() < 100) { shield.setHealth(shield.getHealth() + shield.regenRate);        
            }
    }

    protected void RotateFromPlayer(double angle) {
        double targetAngle = (angle + 180) % 360;
        //System.out.println(targetAngle + " " + faceAngle);
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, targetAngle);

        if (Math.abs(targetAngle - faceAngle) >= 5) {
            if (distances[0] <= distances[1]) {
                if (distances[0] > angleIcrement) {
                    rotate(ShipState.TurningLeft);
                }
            } else {
                if (distances[1] > angleIcrement) {
                    rotate(ShipState.TurningRight);
                }
            }
        }
    }

    protected void RotateToPlayer(double angle) {

        double targetAngle = angle;
        
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, targetAngle);
        
        //System.out.println(distances[0] + " " + distances[1] + " " + faceAngle + " " + targetAngle);
        
        //System.out.println(faceAngle + " " + targetAngle + " " + distances[0] + " " + distances[1]);
        
        //System.out.println(distances[0] + " " + distances[1]);
        if (Math.abs(targetAngle - faceAngle) >= 5) {
            if (distances[0] < distances[1]) {
                if (distances[0] > angleIcrement) {
                    rotate(ShipState.TurningLeft);
                }
            } else {
                if (distances[1] > angleIcrement) {
                    rotate(ShipState.TurningRight);
                }
            }
        }
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) {

        if (canshoot) {
            Random rand = new Random();

            double angle = 360 - faceAngle + rand.nextInt(10) - 5;

            Point2D.Double ShotStartingVel
                    = new Point2D.Double(movementVelocity.x + Calculator.CalcAngleMoveX(angle) * 20,
                            movementVelocity.y + Calculator.CalcAngleMoveY(angle) * 20);

            Point2D.Double ShotStartingPos = new Point2D.Double(
                    Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x - 2.5
                    + Calculator.CalcAngleMoveX(angle) * 20,
                    Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y - 8 + Calculator.CalcAngleMoveY(angle) * 20);

            shots.add(new PulseShot(5, 100, false, ShotStartingPos, ShotStartingVel, angle, true, cameraLocation));
            canshoot = false;

            ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);
        shield.draw(g2d, camera.getLocation(), location);

        Rectangle2D.Float paintRectShield = new Rectangle2D.Float((float) (camera.getSize().x - (camera.getSize().x - 10)),
                (float) (camera.getSize().y - 85), (float) shield.getHealth() * 1.5f, 5f);

        GradientPaint paintShield = new GradientPaint(paintRectShield.x, paintRectShield.y, Color.BLUE, paintRectShield.x + paintRectShield.width,
                paintRectShield.y + paintRectShield.height, Color.CYAN);

        Rectangle2D.Float paintRectHull = new Rectangle2D.Float((float) (camera.getSize().x - (camera.getSize().x - 10)),
                (float) (camera.getSize().y - 55), (float) hull * 1.5f, 5f);

        GradientPaint paintHull = new GradientPaint(paintRectHull.x, paintRectHull.y, new Color(100, 0, 0), paintRectHull.x + paintRectHull.width,
                paintRectHull.y + paintRectHull.height, new Color(255, 0, 0));

        g2d.setPaint(paintShield);
        g2d.fill(paintRectShield);

        g2d.setPaint(paintHull);
        g2d.fill(paintRectHull);
    }
}
