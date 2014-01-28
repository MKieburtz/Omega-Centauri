package MainPackage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.sound.sampled.*;

// @author Michael Kieburtz and Davis Freeman
// might refractor to playerShip
public class Player extends Ship {
    // x and y are game positions
    public Player(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation) {
        
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, cameraLocation);
        
        imagePaths.add("src/resources/FighterIdle.png");
        imagePaths.add("src/resources/FighterThrust.png");
        imagePaths.add("src/resources/FighterLeft.png");
        imagePaths.add("src/resources/FighterRight.png");
        imagePaths.add("src/resources/FPSbackground.png");
        imagePaths.add("src/resources/GoButton.png");
        images = mediaLoader.loadImages(imagePaths);
        activeImage = images.get(0);
        
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


    
    public String getName() {
        return this.name;
    }
    
}
