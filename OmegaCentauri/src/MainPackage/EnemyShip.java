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

public abstract class EnemyShip extends Ship {

    public EnemyShip(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay, int health) // delegate assigning images to the types of ships
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health);
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
                new Point2D.Double(movementVelocity.x + Calculator.CalcAngleMoveX(faceAngle - 90) * 20,
        movementVelocity.y + Calculator.CalcAngleMoveY(faceAngle - 90) * 20);

        Point2D.Double ShotStartingPos = new Point2D.Double(Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x - 5
                + Calculator.CalcAngleMoveX(faceAngle - 90) * 20,
                Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y - 10 + Calculator.CalcAngleMoveY(faceAngle - 90) * 20);


        shots.add(new PulseShot(5, 100, false, ShotStartingPos, ShotStartingVel, faceAngle, true, cameraLocation));
        canshoot = false;
        shootingTimer.schedule(new ShootingTimerTask(), shootingDelay);
    }
    
    @Override
    public void draw(Graphics2D g2d, Camera camera)
    {
        super.draw(g2d, camera);
        shield.draw(g2d, camera.getLocation(), location);
        
        Rectangle2D.Float paintRectShield = new Rectangle2D.Float((float)(camera.getSize().x - (camera.getSize().x - 10)),
        (float)(camera.getSize().y - 85), (float)shield.getHealth() * 1.5f, 5f);
        
        GradientPaint paintShield = new GradientPaint(paintRectShield.x, paintRectShield.y, Color.BLUE, paintRectShield.x + paintRectShield.width,
                paintRectShield.y + paintRectShield.height, Color.CYAN);
        
        Rectangle2D.Float paintRectHull = new Rectangle2D.Float((float)(camera.getSize().x - (camera.getSize().x - 10)),
        (float)(camera.getSize().y - 55), (float)hull * 1.5f, 5f);
        
        GradientPaint paintHull = new GradientPaint(paintRectHull.x, paintRectHull.y, new Color(100,0,0), paintRectHull.x + paintRectHull.width,
                paintRectHull.y + paintRectHull.height, new Color(255,0,0));
        
        g2d.setPaint(paintShield);
        g2d.fill(paintRectShield);
        
        g2d.setPaint(paintHull);
        g2d.fill(paintRectHull);
    }
}
