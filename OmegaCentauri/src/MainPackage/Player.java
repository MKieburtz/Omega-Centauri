package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;

// @author Michael Kieburtz
// might refractor to playerShip
public class Player extends Ship {

    private String name;
    private double faceAngle = 360.0; // maybe move to Ship Class
    private double moveAngle = 0.0;
    private double speed = 0.0;
    private final double maxVel = 5.0;
    private final double velocityIncrease = .07;
    private final double velocityDecrease = .02;
    private double angleIcrement = 2.5;
    private double driftSpeed = 0;
    private boolean drifting = false;
    private Point2D.Double velocity = new Point2D.Double(0, 0);
    private double acceleration = .05;
    
    public String getName() {
        return this.name;
    }

    public Player(int x, int y, Type shipType) {
        location = new Point2D.Double(x, y);
        nextLocation = new Point2D.Double();
        type = shipType;
        imageFile = new File("resources/FighterGrey.png");
        setUpShipImage();
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public void moveTo(double x, double y) {
        location.x = x;
        location.y = y;
    }

    public void moveTo(Point2D.Double location) {
        this.location.x = location.x;
        this.location.y = location.y;
    }

    public void moveRelitive(double dx, double dy) {
        this.location.x += dx;
        this.location.y += dy;
    }

    public void rotate(boolean positive) {
        if (positive)
        {
            faceAngle += angleIcrement;
            if (faceAngle > 360)
                faceAngle = angleIcrement;
        }
        else
        {
            faceAngle -= angleIcrement;
            if (faceAngle < 0)
                faceAngle = 360 - angleIcrement;
        }
    }

    public void rotate(double amount) {
        faceAngle = amount;
    }

    public void move() {
        moveAngle = faceAngle - 90;
        
        velocity.x += CalcAngleMoveX(moveAngle) * acceleration;
        
        if (velocity.x > maxVel) 
            velocity.x = maxVel;
        
        else if (velocity.x < -maxVel)
            velocity.x = -maxVel;
        
        velocity.y += CalcAngleMoveY(faceAngle) * acceleration;
        
        if (velocity.y > maxVel)
            velocity.y = maxVel;
        
        else if (velocity.x < -maxVel)
            velocity.x = -maxVel;
        
        
        location.x += velocity.x;
        location.y += velocity.y;
    }

    public double getAngle() {
        return faceAngle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private void increaseSpeed() 
    {
        if (speed < maxVel) {
                if (speed + velocityIncrease > maxVel) {
                    speed = maxVel;
                } else {
                    speed += velocityIncrease;
                }
            }
    }

    private void increaseSpeed(double amount) 
    {
        if (speed < maxVel) {
                if (speed + amount > maxVel) {
                    speed = maxVel;
                } else {
                    speed += amount;
                            
                }
            }
    }
    
    private void decreaseSpeed() 
    {
         if (speed > 0) {
                if (speed - velocityDecrease < 0) {
                    speed = 0;
                } else {
                    speed -= velocityDecrease;
                }
            } else {
                drifting = false;
            }
    }
    
    private void decreaseSpeed(double amount) 
    {
        if (speed > 0) {

            if (speed - velocityDecrease + amount < 0) {
                speed = 0;
            } else {
                speed -= velocityDecrease + amount;
            }
        } else {
            drifting = false;
        }
    }
    
    private double CalcAngleMoveX(double angle)
    {
        return (double)(Math.cos(angle * Math.PI / 180));
    }
    
    private double CalcAngleMoveY(double angle)
    {
        return (double)(Math.sin(angle * Math.PI / 180));
    }

}
