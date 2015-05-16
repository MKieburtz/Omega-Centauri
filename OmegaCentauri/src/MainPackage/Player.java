package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
// might refractor to playerShip
public class Player extends Ship
{
    
    private Resources resources;
    
    public Player(int x, int y, Type shipType, double maxVel, double maxAngleVelocity,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int timerDelay, int health, Resources resources) 
    {

        super(x, y, shipType, maxVel, maxAngleVelocity, angleIncrement, acceleration, timerDelay, health);

        this.resources = resources;
        
        imagePaths.add("resources/FighterIdle.png");
        imagePaths.add("resources/FighterThrust.png");
        imagePaths.add("resources/FighterLeft.png");
        imagePaths.add("resources/FighterRight.png");
        imagePaths.add("resources/FighterThrustLeft.png");
        imagePaths.add("resources/FighterThrustRight.png");
        
        images = resources.getImagesForObject(imagePaths);

        activeImage = images.get(0);
        shield = new Shield(location, new Point2D.Double(0, 0), false, new Point(activeImage.getWidth(), activeImage.getHeight()),
                10, 500, resources, true);
        setUpHitbox(cameraLocation);

        soundPaths.add("resources/Pulse.wav");
        
        sounds = resources.getSoundsForObject(soundPaths);
        
        explosion = new Explosion(Explosion.Type.fighter, new Dimension(activeImage.getWidth(), activeImage.getHeight()), resources);
        
        faceAngle = 180;
    }

    public void moveTo(double x, double y) 
    {
        location.x = x;
        location.y = y;
    }

    public void moveTo(Point2D.Double location) 
    {
        this.location.x = location.x;
        this.location.y = location.y;
    }

    public void moveRelitive(double dx, double dy) 
    {
        this.location.x += dx;
        this.location.y += dy;
    }

    public void rotate(double amount) 
    {
        faceAngle = amount;
    }

    public double getAngle() 
    {
        return faceAngle;
    }

    public Point2D.Double getVel() 
    {
        return this.movementVelocity;
    }

    public void setVel(int vert, int hor) 
    {
        this.movementVelocity.x = vert;
        this.movementVelocity.y = hor;
    }

    public ArrayList getImages() 
    {
        return images;
    }

    public boolean isMoving() 
    {
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

    public String getName() 
    {
        return this.name;
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) 
    {
        AffineTransform original = g2d.getTransform();
        
        super.draw(g2d, camera);

        g2d.setTransform(original);
        //g2d.setColor(Color.red);
        //hitbox.draw(g2d, camera.getLocation());
        
        Point2D.Double shipMiddle = new Point2D.Double(Calculator.getScreenLocationMiddle(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y);
        if (shield.isActive())
        {
            shield.draw(g2d, camera.getLocation(), location,
                    shipMiddle,
                    shipMiddle,
                    faceAngle);
        }
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) 
    {
        //playSound(0);
        Random rand = new Random();

        double angle = 360 - faceAngle + rand.nextInt(10) - 5;

        Point2D.Double ShotStartingVel
                = new Point2D.Double(movementVelocity.x + Calculator.CalcAngleMoveX(angle) * 20,
                        movementVelocity.y + Calculator.CalcAngleMoveY(angle) * 20);

        Point2D.Double ShotStartingPos = new Point2D.Double(
                Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()).x - 6
                + Calculator.CalcAngleMoveX(angle) * 20,
                Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()).y
                + Calculator.CalcAngleMoveY(angle) * 20);

        canshoot = false;

        shots.add(new PulseShot(5, ShotStartingPos, ShotStartingVel, angle, false, cameraLocation, this, resources));

        ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
    }
}
