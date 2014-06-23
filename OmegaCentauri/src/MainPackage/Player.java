package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
// might refractor to playerShip
public class Player extends Ship {

    private final int IDLE = 0;
    private final int THRUSTING = 1;
    private final int TURNINGLEFT = 2;
    private final int TURNINGRIGHT = 3;
    private final int TURNINGLEFTTHRUSTING = 4;
    private final int TURNINGRIGHTTHRUSTING = 5;
    
    

    // x and y are game positions
    public Player(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int timerDelay, int health) {

        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, timerDelay, health);
        
        
        
        imagePaths.add("resources/FighterIdle.png");
        imagePaths.add("resources/FighterThrust.png");
        imagePaths.add("resources/FighterLeft.png");
        imagePaths.add("resources/FighterRight.png");
        imagePaths.add("resources/FighterThrustLeft.png");
        imagePaths.add("resources/FighterThrustRight.png");
        
        images = mediaLoader.loadImages(imagePaths);
        images = Calculator.toCompatibleImages(images);
        
        activeImage = images.get(0);
        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), false, new Point(activeImage.getWidth(), activeImage.getHeight()));
        setUpHitbox(cameraLocation);

        soundPaths.add("resources/Pulse.wav");

        sounds = mediaLoader.loadSounds(soundPaths);
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

    public void rotate(double amount) {
        faceAngle = amount;
    }

    public double getAngle() {
        return faceAngle;
    }

    public Point2D.Double getVel() {
        return this.movementVelocity;
    }

    public void setVel(int vert, int hor) {
        this.movementVelocity.x = vert;
        this.movementVelocity.y = hor;
    }

    public ArrayList getImages() {
        return images;
    }

    public void changeImage(ShipState state) {
        switch (state) {
            case Idle: {
                activeImage = images.get(IDLE);
                break;
            }
            case Thrusting: {
                activeImage = images.get(THRUSTING);
                break;
            }
            case TurningLeft: {
                activeImage = images.get(TURNINGLEFT);
                break;
            }
            case TurningRight: {
                activeImage = images.get(TURNINGRIGHT);
                break;
            }
            case TurningLeftThrusting: {
                activeImage = images.get(TURNINGLEFTTHRUSTING);
                break;
            }
            case TurningRightThrusting: {
                activeImage = images.get(TURNINGRIGHTTHRUSTING);
                break;
            }

        }
    }

    public boolean isMoving() {
        return movementVelocity.x != 0 || movementVelocity.y != 0;
    }

    public boolean isRotating()
    {
        return angularVelocity != 0;
    }
    
    public boolean rotatingRight()
    {
        return rotatingRight;
    }
    
    public void speedBoost() {
        if (maxVel == baseMaxVel) {
            maxVel *= 2;
        }
    }

    public void stopSpeedBoosting() {
        while (maxVel > baseMaxVel) {
            maxVel *= .98;
        }
        maxVel = 5.0;
    }

    public String getName() {
        return this.name;
    }
    
    
    
    @Override
    public void draw(Graphics2D g2d, Camera camera)
    {
        super.draw(g2d, camera);
        shield.draw(g2d, camera.getLocation(), location);
        
        
        g2d.setColor(Color.CYAN);
        g2d.drawString("Enemy Shield Integrity:", 10, camera.getSize().y - 97);
        g2d.drawString("Enemy Hull Integrity:", 10, camera.getSize().y - 63);
        
        
        g2d.setColor(Color.CYAN);
        
         g2d.drawString("Shield Integrity: " + shield.getHealth() + "%", 10, 60);
         g2d.drawString("Hull Integrity: " + hull + "%", 10, 75);
    }
}
