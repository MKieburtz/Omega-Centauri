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
    private final int SCANNERMODULE = 6;
    private final int HEALTHBACKGROUND = 7;
    

    // x and y are game positions
    public Player(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int timerDelay, int health) {

        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, timerDelay, health);
        
        
        
        imagePaths.add("src/resources/FighterIdle.png");
        imagePaths.add("src/resources/FighterThrust.png");
        imagePaths.add("src/resources/FighterLeft.png");
        imagePaths.add("src/resources/FighterRight.png");
        imagePaths.add("src/resources/FighterThrustLeft.png");
        imagePaths.add("src/resources/FighterThrustRight.png");
        imagePaths.add("src/resources/TempScannerModule.png");
        imagePaths.add("src/resources/healthbackground.png");
        imagePaths.add("src/resources/HealthyTick.png");
        imagePaths.add("src/resources/WarningTick.png");
        imagePaths.add("src/resources/DangerTick.png");
        images = mediaLoader.loadImages(imagePaths);
        activeImage = images.get(0);
        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), false, new Point(activeImage.getWidth(), activeImage.getHeight()));
        setUpHitbox(cameraLocation);

        soundPaths.add("src/resources/Pulse.wav");

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
        return this.velocity;
    }

    public void setVel(int vert, int hor) {
        this.velocity.x = vert;
        this.velocity.y = hor;
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
        return velocity.x != 0 || velocity.y != 0;
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
        
        g2d.drawImage(images.get(SCANNERMODULE), null, 0, camera.getSize().y - 113);
        g2d.setColor(Color.CYAN);
        g2d.drawString("Enemy Shield Integrity:", 10, camera.getSize().y - 97);
        g2d.drawString("Enemy Hull Integrity:", 10, camera.getSize().y - 63);
        
        g2d.drawImage(images.get(HEALTHBACKGROUND), null, 0, 0);
        g2d.setColor(Color.CYAN);
        
         g2d.drawString("Shield Integrity: " + shield.getHealth() + "%", 10, 60);
         g2d.drawString("Hull Integrity: " + hull + "%", 10, 75);
    }
}
