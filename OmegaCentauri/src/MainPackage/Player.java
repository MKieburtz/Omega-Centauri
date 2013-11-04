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
    private final double MaxSpeed = 9.0;
    private final double velocityIncrease = .07;
    private final double velocityDecrease = .08;
    
    
    
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
            angle += 5;
        } else if (!positive && angle == 0) {
            angle = 360;
            angle -= 5;
        } else {
            angle -= 5;
        }

        if (angle == 360) {
            angle = 0;
        }

    }

    @SuppressWarnings("empty-statement")
    public void move(boolean Slowingdown, double driftAngle) {
            if (!Slowingdown) {
                this.driftAngle = driftAngle;
                if (speed < MaxSpeed) {
                    if (speed + velocityIncrease > MaxSpeed) {
                        speed = MaxSpeed;
                    } else {
                        speed += velocityIncrease;
                    }
                }
            nextLocation.x = location.x + (speed * Math.sin(Math.toRadians(angle)));
            nextLocation.y = location.y + (speed * -Math.cos(Math.toRadians(angle)));

            }// end if
            else {
                
                if (speed > 0) {
                    if (speed - velocityDecrease < 0) {
                        speed = 0;
                    } else {
                        speed -= velocityDecrease;
                    }
                } else {
                    ;
                }
            nextLocation.x = location.x + (speed * Math.sin(Math.toRadians(this.driftAngle)));
            nextLocation.y = location.y + (speed * -Math.cos(Math.toRadians(this.driftAngle)));
            }
            
            location = nextLocation;
        
}
    public double getAngle() {
        return angle;
    }
}
