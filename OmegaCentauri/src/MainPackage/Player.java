package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;

// @author Michael Kieburtz
// might refractor to playerShip
public class Player extends Ship {

    private String name;
    private double angle = 0; // maybe move to Ship Class
    private double driftAngle = 0;
    private double speed = 0.0;
    private final double MaxSpeed = 5.0;
    private final double velocityIncrease = .07;
    private final double velocityDecrease = .02;
    private double angleIcrement = 2.5;
    private double driftSpeed = 0;
    private boolean drifting = false;
    
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
        if (positive) {
            angle += angleIcrement;
        } else if (!positive && angle == 0) {
            angle = 360;
            angle -= angleIcrement;
        } else {
            angle -= angleIcrement;
        }

        if (angle == 360) {
            angle = 0;
        }

    }

    public void rotate(double amount) {
        angle = amount;
    }

    public void move(boolean Slowingdown, double driftAngle, boolean driftMove) {
        if (!Slowingdown) {
            this.driftAngle = driftAngle;
            increaseSpeed();

            nextLocation.x = location.x + (speed * Math.sin(Math.toRadians(angle)));
            nextLocation.y = location.y + (speed * -Math.cos(Math.toRadians(angle)));

        }// end if
        else if (driftMove)
        {
            if (drifting)
            {
            driftSpeed = speed;
            speed = 0;
            }
            increaseSpeed();
            nextLocation.x = location.x + (driftSpeed * Math.sin(Math.toRadians(this.driftAngle)));
            nextLocation.y = location.y + (driftSpeed * -Math.cos(Math.toRadians(this.driftAngle)));
            
            nextLocation.x = location.x + (speed * Math.sin(Math.toRadians(angle)));
            nextLocation.y = location.y + (speed * -Math.cos(Math.toRadians(angle)));
        }
        
        else {
            decreaseSpeed();
            nextLocation.x = location.x + (speed * Math.sin(Math.toRadians(this.driftAngle)));
            nextLocation.y = location.y + (speed * -Math.cos(Math.toRadians(this.driftAngle)));
        }
        
        
        location = nextLocation;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private void increaseSpeed() 
    {
        if (speed < MaxSpeed) {
                if (speed + velocityIncrease > MaxSpeed) {
                    speed = MaxSpeed;
                } else {
                    speed += velocityIncrease;
                }
            }
    }

    private void increaseSpeed(double amount) 
    {
        if (speed < MaxSpeed) {
                if (speed + amount > MaxSpeed) {
                    speed = MaxSpeed;
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

}
