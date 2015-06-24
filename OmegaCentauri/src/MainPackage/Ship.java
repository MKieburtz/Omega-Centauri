package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import java.util.concurrent.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public abstract class Ship implements GameEntity
{   
    protected int hullDurability;
    protected int maxhullDurabilty;
    protected int fuel;
    protected int power;
    protected Type type;
    protected double faceAngle = 0;
    protected double moveAngle = 0.0;
    protected final int collisionDamage = 50;
    protected Point2D.Double location;
    protected Point2D.Double nextLocation;
    protected Point2D.Double movementVelocity = new Point2D.Double(0, 0);
    protected double angularVelocity = 0;
    protected double maxAngularVel;
    protected EllipseHitbox shieldHitbox;
    protected ShapeHitbox hullHitbox = null;
    protected String name;
    protected double maxVel;
    protected double angleIcrement;
    protected double acceleration = .15;
    protected ImageRotationState imageRotationState; 
    protected ImageMovementState imageMovementState;  
    protected RotationState rotationState;
    protected MovementState movementState;
    protected GameData gameData;
    protected GameActionListener actionListener;
    
    protected ArrayList<String> imagePaths = new ArrayList<>();
    protected ArrayList<String> soundPaths = new ArrayList<>();
    protected ArrayList<BufferedImage> images = new ArrayList<>();
    protected ArrayList<Clip> sounds = new ArrayList<>();
    protected BufferedImage activeImage;
    protected ArrayList<Shot> shots = new ArrayList<>();
    
    protected boolean canshoot = true;
    protected int shootingDelay;
    protected Shield shield;
    protected boolean colliding = false;
    protected ScheduledExecutorService ex;
    
    protected Explosion explosion;
    
    protected boolean exploding;
    
    public Ship(int x, int y, Type shipType, double maxVel, double maxAngularVelocity,
            double angleIncrement, double acceleration, int shootingDelay, int health,
            MainPackage.GameActionListener actionListener)
    {
        location = new Point2D.Double(x, y);
        nextLocation = new Point2D.Double();
        type = shipType;
        this.maxVel = maxVel;
        this.angleIcrement = angleIncrement;
        this.maxAngularVel = maxAngularVelocity;
        this.acceleration = acceleration;
        this.shootingDelay = shootingDelay;
        this.hullDurability = health;
        this.maxhullDurabilty = health;
        ex = Executors.newSingleThreadScheduledExecutor();
        imageRotationState = ImageRotationState.Idle;
        imageMovementState = ImageMovementState.Idle;
        rotationState = RotationState.Idle;
        movementState = MovementState.Idle;
        gameData = new GameData();
        this.actionListener = actionListener;
    }
    
    public BufferedImage getImage()
    {
        return activeImage;
    }
    
    public BufferedImage getImage(int index)
    {
        return this.images.get(index);
    }
    
    @Override
    public void draw(Graphics2D g2d)
    {
        if (!exploding)
        {
            g2d.rotate(Math.toRadians(360 - faceAngle),
                    Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y);
            
            g2d.translate(Calculator.getScreenLocation(gameData.getCameraLocation(), location).x,
                    Calculator.getScreenLocation(gameData.getCameraLocation(), location).y);
            
            g2d.drawImage(activeImage, 0, 0, null);
        }
        else
        {
            explosion.draw(g2d, false);
            if (explosion.isDone())
            {
                exploding = false;
                actionListener.entityDoneExploding(this);
            }
        }
    }
    
    protected void move(MovementState state)
    {
        
        if (state == MovementState.Thrusting)
        {
            movementState = MovementState.Thrusting;
            movementVelocity.x += Calculator.CalcAngleMoveX(360 - faceAngle) * acceleration;
            
            if (movementVelocity.x > maxVel)
            {
                movementVelocity.x = maxVel;
            } 
            else if (movementVelocity.x < -maxVel)
            {
                movementVelocity.x = -maxVel;
            }
            
            movementVelocity.y += Calculator.CalcAngleMoveY(360 - faceAngle) * acceleration;
            
            if (movementVelocity.y > maxVel) 
            {
                movementVelocity.y = maxVel;
            } 
            else if (movementVelocity.y < -maxVel)
            {
                movementVelocity.y = -maxVel;
            }
        }
        
        movementVelocity.x *= .99;
        movementVelocity.y *= .99;
        
        if (state == MovementState.Drifting)
        {
            movementState = MovementState.Drifting;
            if (Math.abs(movementVelocity.x) < .1)
            {
                movementVelocity.x = 0;
            }
            if (Math.abs(movementVelocity.y) < .1) 
            {
                movementVelocity.y = 0;
            }
            
            if (movementVelocity.x == 0 && movementVelocity.y == 0)
            {
                movementState = MovementState.Idle;
            }
        }
        
        updatePosition();
    }
    
    protected void updatePosition() 
    {
        location.x += movementVelocity.x;
        location.y += movementVelocity.y;
    }
    
    public abstract void shoot();
    
    public Point2D.Double getLocation()
    {
        return location;
    }
    
    protected void updateAngle(RotationState state) 
    {
        double beforeUpdate = faceAngle;
        if (state == RotationState.TurningRight || state == RotationState.TurningRightDrifting)
        {
            if (state == RotationState.TurningRightDrifting)
            {
                rotationState = RotationState.TurningRightDrifting;
            }
            else
            {
                rotationState = RotationState.TurningRight;
            }
            
            faceAngle -= angularVelocity;
            if (faceAngle <= 0)
            {
                faceAngle = 360 + faceAngle;
            }
        } 
        else if (state == RotationState.TurningLeft || state == RotationState.TurningLeftDrifting) 
        {
            if (state == RotationState.TurningLeftDrifting)
            {
                rotationState = RotationState.TurningLeftDrifting;
            }
            else
            {
                rotationState = RotationState.TurningLeft;
            }
            
            faceAngle += angularVelocity;
            if (faceAngle >= 360) 
            {
                faceAngle = faceAngle - 360;
            }
        }
        if (shield.isActive())
        {
            double[] distances = Calculator.getDistancesBetweenAngles(beforeUpdate, faceAngle);
            double change;
            if (state == RotationState.TurningRight || state == RotationState.TurningRightDrifting)
            {
                change = distances[0] > distances[1] ? distances[0] : distances[1];
            }
            else
            {
                change = distances[0] > distances[1] ? -distances[0] : -distances[1];
            }
            shield.updateSegments(change);
        }
    }
    
    public void rotate(RotationState state) 
    {   
        
        if (state != RotationState.TurningLeftDrifting && state != RotationState.TurningRightDrifting) 
        {
            angularVelocity += angleIcrement * .1;
            
            if (angularVelocity > maxAngularVel) 
            {
                angularVelocity = maxAngularVel;
            } 
            else if (angularVelocity < -maxAngularVel) 
            {
                angularVelocity = -maxAngularVel;
            }
            
            if ((angularVelocity < .01 && angularVelocity > 0) || (angularVelocity > -.01 && angularVelocity < 0)) 
            {
                angularVelocity = 0;
                rotationState = RotationState.Idle;
            }
        }
        else 
        {
            angularVelocity *= .90; // causes acceleration
        }
        
        updateAngle(state);
    }
    
    public void setUpHitbox() 
    {        
        try
        {
            shieldHitbox = new EllipseHitbox(activeImage.getWidth(), activeImage.getHeight());
        } 
        catch (NullPointerException ex)
        {
            System.err.println("active image not initialized!");
        }
    }
    
    protected void updateHitbox() 
    {
        shieldHitbox.moveToLocation(Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()));
        shieldHitbox.rotateToAngle(faceAngle);
    }
    
    public ArrayList<Shot> getShots() 
    {
        return shots;
    }
    
    public boolean[] CollisionEventWithShotWithShield(Ship ship, Shot shot, ArrayList<Ship> allShips, double collisionAngleShield, double collisionAngle) 
    {
        boolean[] removed = {false, false}; // first is ship, second is shot.
        
        for (Ship s : allShips) 
        {
            if (shot.getOwner().equals(s) && !s.equals(ship)) // if s fired the shot and s isn't the ship that collided with the shot...
            {  
                if (!(ship instanceof EnemyShip && s instanceof EnemyShip)) 
                { 
                    shot.explode(true);
                    s.removeShot(shot); // removing because it collided
                    removed[1] = true;
                } 
            }
        }
        
        if (!ship.getShots().contains(shot)) 
        {
            if (!(ship instanceof EnemyShip && shot.getOwner() instanceof EnemyShip))
            {
                if (ship instanceof EnemyMediumFighter)
                {
                    takeDamageShield(shot.getDamage(), collisionAngleShield, collisionAngle, 30);
                }
                else
                {
                    takeDamageShield(shot.getDamage(), collisionAngleShield, collisionAngle, 0);
                }
                if (hullDurability <= 0) 
                {
                    removed[0] = true;
                }
            }
        }
        
        return removed;
    }
    public boolean[] CollisionEventWithShotWithHull(Ship ship, Shot shot, ArrayList<Ship> allShips)
    {
        boolean[] removed = {false, false}; // first is ship, second is shot.
        
        for (Ship s : allShips)
        {
            if (shot.getOwner().equals(s) && !s.equals(ship)) // if s fired the shot and s isn't the ship that collided with the shot...
            {  
                if (!(ship instanceof EnemyShip && s instanceof EnemyShip))
                { 
                    removed[1] = true;
                } 
            }
        }
        
        if (!ship.getShots().contains(shot)) 
        {
            if (!(ship instanceof EnemyShip && shot.getOwner() instanceof EnemyShip))
            {
                takeDamageHull(shot.getDamage());
                if (hullDurability <= 0) 
                {
                    removed[0] = true;
                }
            }
        }
        
        return removed;
    }
    
    public Hitbox returnHitbox() 
    {
        if (hullHitbox == null || shield.getEnergy() > 0)
        {
            return shieldHitbox;
        }
        return hullHitbox;
    }
    
    public boolean canShoot()
    {
        return canshoot;
    }
    
    class ShootingService implements Runnable 
    {
        
        @Override
        public void run() 
        {
            canshoot = true;
        }
    }
    
    public Shield getShield() 
    {
        return shield;
    }
    
    public void removeShot(Shot shotToRemove) 
    {
        shots.remove(shotToRemove);
    }
    
    public double getShieldHealth() 
    {
        return shield.getEnergy();
    }
    
    public int getHullHealth() 
    {
        return hullDurability;
    }
    
    public void reduceHull(double damage) 
    {
        hullDurability -= damage;
        
        if (hullDurability <= 0)
        {
            exploding = true;
        }
    }
    
    public void purgeShots(Dimension screensize) 
    {
        for (int i = shots.size() - 1; i > -1; i--) 
        {
            if (shots.get(i).outsideScreen(screensize)) 
            {
                shots.remove(i);
            }
        }
    }
    
    public void takeDamageShield(int damage, double collisionAngleShield, double collisionAngle, int extra) 
    {
        if (shield.getEnergy() - damage >= 0)
        {
            shield.activate(damage, collisionAngleShield, collisionAngle, faceAngle, extra);
        }
        else
        {
            double healthToLoseShield = damage - Math.abs(shield.getEnergy() - damage);
            double healthToLoseHull = Math.abs(shield.getEnergy() - damage);
            
            if (healthToLoseShield > 0) 
            {
                shield.activate(healthToLoseShield, collisionAngleShield, collisionAngle, faceAngle, extra);
            }
            reduceHull(healthToLoseHull);
        }
    }
    
    public void takeDamageHull(int damage)
    {
        double healthToLoseHull = Math.abs(shield.getEnergy() - damage);
            
        reduceHull(healthToLoseHull);
    }
    
    public double getMaxHull()
    {
        return maxhullDurabilty;
    }
    
    public double getMaxShield()
    {
        return shield.getMaxEnergy();
    }
    
    public boolean isColliding() {
        return colliding;
    }
    
    public BufferedImage getActiveImage() {
        return activeImage;
    }
    
    public double getFaceAngle() {
        return faceAngle;
    }
    
    public boolean isExploding()
    {
        return exploding;
    }
    
    public abstract void changeImage(ImageMovementState movementState, ImageRotationState rotationState);
    
    public boolean moving() 
    {
        return movementVelocity.x != 0 || movementVelocity.y != 0;
    }
    
    public boolean rotating()
    {
        return angularVelocity != 0;
    }
    
    public ImageRotationState getImageRotationState()
    {
        return imageRotationState;
    }
    
    public ImageMovementState getImageMovementState()
    {
        return imageMovementState;
    }
}
