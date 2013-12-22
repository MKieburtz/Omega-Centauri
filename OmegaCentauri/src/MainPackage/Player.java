package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;

// @author Michael Kieburtz and Davis Freeman
// might refractor to playerShip
public class Player extends Ship {

    private String name;
    private double faceAngle = 90.0; // maybe move to Ship Class
    private double moveAngle = 0.0;
    private final double maxVel = 5.0;
    private final double angleIcrement = 4;
    private Point2D.Double velocity = new Point2D.Double(0, 0);
    private final double acceleration = .15;

    public String getName() {
        return this.name;
    }

    public Player(int x, int y, Type shipType) {
        location = new Point2D.Double(x, y);
        nextLocation = new Point2D.Double();
        type = shipType;
        imagePaths.add("resources/FighterGreyIdle.png");
        imagePaths.add("resources/FighterGreyMoving.png");
        imagePaths.add("resources/FighterGreyTurningLeft.png");
        imagePaths.add("resources/FighterGreyTurningRight.png");
        imagePaths.add("resources/FillerBackground.png");
        imagePaths.add("resources/FPSbackground.png");
        imagePaths.add("resources/GoButton.png");
        images = imageLoader.loadImages(imagePaths);
        activeImage = images.get(0);
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
            faceAngle += angleIcrement;
            if (faceAngle > 360) {
                faceAngle = angleIcrement;
            }
        } else {
            faceAngle -= angleIcrement;
            if (faceAngle <= 0) {
                faceAngle = 360 - angleIcrement;
            }
        }
    }

    public void rotate(double amount) {
        faceAngle = amount;
    }
    
    public void move(boolean thrusting) {
        
        moveAngle = faceAngle - 90;
        
        if (thrusting)
        {
            velocity.x += CalcAngleMoveX(moveAngle) * acceleration;

            if (velocity.x > maxVel) {
                velocity.x = maxVel;
            } else if (velocity.x < -maxVel) {
                velocity.x = -maxVel;
            }

            velocity.y += CalcAngleMoveY(moveAngle) * acceleration;

            if (velocity.y > maxVel) {
                velocity.y = maxVel;
            } else if (velocity.y < -maxVel) {
                velocity.y = -maxVel;
            }
            
        }
        
        velocity.x *= .99;
        velocity.y *= .99;
        
        if (Math.abs(velocity.x) < .1)
            velocity.x = 0;
        
        if (Math.abs(velocity.y) < .1)
            velocity.y = 0;
        
        updatePosition();
        
        }
    private void updatePosition() {
        location.x += velocity.x;
        location.y += velocity.y;
    }

    public double getAngle() {
        return faceAngle;
    }

    public Point2D.Double getVel()
    {
        return this.velocity;
    }
    
    public void setVel(int vert, int hor)
    {
        this.velocity.x = vert;
        this.velocity.y = hor;
    }

    private double CalcAngleMoveX(double angle) {
        return (double) (Math.cos(Math.toRadians(angle)));
    }

    private double CalcAngleMoveY(double angle) {
        return (double) (Math.sin(Math.toRadians(angle)));
    }

    public ArrayList getImages() {
        return images;
    }
    
    public void changeImage(int index)
    {
        activeImage = images.get(index);
    }
    
    public boolean isMoving()
    {
        return velocity.x != 0 || velocity.y != 0;
    }
}
