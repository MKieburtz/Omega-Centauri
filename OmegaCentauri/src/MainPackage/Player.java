package MainPackage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.sound.sampled.*;

// @author Michael Kieburtz and Davis Freeman
// might refractor to playerShip
public class Player extends Ship {

    private String name;
    private final double baseMaxVel = 5.0;
    private double maxVel = 5.0;
    private final double angleIcrement = 5;
    
    private final double acceleration = .15;

    public String getName() {
        return this.name;
    }

    public Player(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration) {
        
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration);
        
        imagePaths.add("src/resources/FighterIdle.png");
        imagePaths.add("src/resources/FighterThrust.png");
        imagePaths.add("src/resources/FighterLeft.png");
        imagePaths.add("src/resources/FighterRight.png");
        imagePaths.add("src/resources/FPSbackground.png");
        imagePaths.add("src/resources/GoButton.png");
        images = mediaLoader.loadImages(imagePaths);
        activeImage = images.get(0);
        
        soundPaths.add("src/resources/Pulse.wav");
        
        sounds = mediaLoader.loadSounds(soundPaths);
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

    public void changeImage(int index) {
        activeImage = images.get(index);
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

    public void shoot(Point2D.Double cameraLocation) // have to pass the angle for some reason
    {
        sounds.get(0).setFramePosition(0);
        Point2D.Double ShotStartingVel;

        ShotStartingVel =
                new Point2D.Double(velocity.x + Calculator.CalcAngleMoveX(faceAngle - 90) * 20,
                        velocity.y + Calculator.CalcAngleMoveY(faceAngle - 90) * 20);

        Point2D.Double ShotStartingPos = new Point2D.Double(getScreenLocationMiddle(cameraLocation).x - 3.5,
                getScreenLocationMiddle(cameraLocation).y - 3.5);
        
        sounds.get(0).start();
        
        shots.add(new PulseShot(5, 100, false, ShotStartingPos, ShotStartingVel, faceAngle));

    }

    public ArrayList<Shot> getShots() {
        return shots;
    }
    
}
