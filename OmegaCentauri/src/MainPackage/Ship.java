package MainPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import java.util.concurrent.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public abstract class Ship implements CollisionListener {

    // make sure image loading order is correct!
    protected final int IDLE = 0;
    protected final int THRUSTING = 1;
    protected final int TURNINGLEFT = 2;
    protected final int TURNINGRIGHT = 3;
    protected final int TURNINGLEFTTHRUSTING = 4;
    protected final int TURNINGRIGHTTHRUSTING = 5;
    
    protected int hullDurability;
    protected int maxhullDurabilty;
    protected int fuel;
    protected int power;
    protected Type type;
    protected double faceAngle = 90;
    protected double moveAngle = 0.0;
    protected final int collisionDamage = 50;
    protected Point2D.Double location;
    protected Point2D.Double nextLocation;
    protected Point2D.Double movementVelocity = new Point2D.Double(0, 0);
    protected double angularVelocity = 0;
    protected double maxAngularVel;
    protected Hitbox hitbox;
    protected String name;
    protected double baseMaxVel;
    protected double maxVel;
    protected double angleIcrement;
    protected double acceleration = .15;
    
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected ArrayList<Clip> sounds = new ArrayList<Clip>();
    protected BufferedImage activeImage;
    protected ArrayList<Shot> shots = new ArrayList<Shot>();
    
    protected boolean canshoot = true;
    protected int shootingDelay;
    protected Shield shield;
    protected boolean rotatingRight = false;
    protected boolean colliding = false;
    protected ScheduledExecutorService ex;
    
    protected ArrayList<Point2D.Double> hitboxStartingPoints = new ArrayList<>();
    protected Point2D.Double hitboxRotationPoint = null;
    
    protected Ship targetShip;

    public Ship(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double maxAngularVelocity, double angleIncrement, double acceleration, int shootingDelay, int health) {

        location = new Point2D.Double(x, y);
        nextLocation = new Point2D.Double();
        type = shipType;

        this.baseMaxVel = baseMaxVel;
        this.maxVel = maxVel;
        this.angleIcrement = angleIncrement;
        this.maxAngularVel = maxAngularVelocity;
        this.acceleration = acceleration;
        this.shootingDelay = shootingDelay;
        this.hullDurability = health;
        this.maxhullDurabilty = health;
        ex = Executors.newSingleThreadScheduledExecutor();
    }

    public BufferedImage getImage() {
        return activeImage;
    }

    public BufferedImage getImage(int index) {
        return this.images.get(index);
    }

    public void draw(Graphics2D g2d, Camera camera) {
        AffineTransform transform = (AffineTransform)g2d.getTransform().clone();
        
        transform.rotate(Math.toRadians(360 - faceAngle),
                Calculator.getScreenLocation(camera.getLocation(), location).x + activeImage.getWidth() / 2,
                Calculator.getScreenLocation(camera.getLocation(), location).y + activeImage.getHeight() / 2);

        transform.translate(Calculator.getScreenLocation(camera.getLocation(), location).x,
                Calculator.getScreenLocation(camera.getLocation(), location).y);

        //hitbox.draw(g2d);
        
        g2d.transform(transform);

        g2d.drawImage(activeImage, 0, 0, null);
        
    }

    protected void move(ShipState state) {

        if (state == ShipState.Thrusting) {
            movementVelocity.x += Calculator.CalcAngleMoveX(360 - faceAngle) * acceleration;

            if (movementVelocity.x > maxVel) {
                movementVelocity.x = maxVel;
            } else if (movementVelocity.x < -maxVel) {
                movementVelocity.x = -maxVel;
            }

            movementVelocity.y += Calculator.CalcAngleMoveY(360 - faceAngle) * acceleration;

            if (movementVelocity.y > maxVel) {
                movementVelocity.y = maxVel;
            } else if (movementVelocity.y < -maxVel) {
                movementVelocity.y = -maxVel;
            }
        }

        movementVelocity.x *= .99;
        movementVelocity.y *= .99;

        if (state == ShipState.Drifting) {
            if (Math.abs(movementVelocity.x) < .1) {
                movementVelocity.x = 0;
            }

            if (Math.abs(movementVelocity.y) < .1) {
                movementVelocity.y = 0;
            }
        }

        updatePosition();

    }

    protected void updatePosition() {
        location.x += movementVelocity.x;
        location.y += movementVelocity.y;
    }

    public abstract void shoot(Point2D.Double cameraLocation);

    public Point2D.Double getLocation() {
        return location;
    }

    protected void updateAngle(ShipState state) {
        if (state == ShipState.TurningRight || state == ShipState.AngleDriftingRight) {
            faceAngle -= angularVelocity;
            if (faceAngle <= 0) {
                faceAngle = 360 + faceAngle;
            }
        } else if (state == ShipState.TurningLeft || state == ShipState.AngleDriftingLeft) {
            faceAngle += angularVelocity;
            if (faceAngle >= 360) {
                faceAngle = faceAngle - 360;
            }
        }

    }

    public void rotate(ShipState state) {

        rotatingRight = state == ShipState.AngleDriftingRight || state == ShipState.TurningRight;

        if (state != ShipState.AngleDriftingLeft && state != ShipState.AngleDriftingRight) {
            
            angularVelocity += angleIcrement * .1;

            if (angularVelocity > maxAngularVel) {
                angularVelocity = maxAngularVel;
            } else if (angularVelocity < -maxAngularVel) {
                angularVelocity = -maxAngularVel;
            }

            if ((angularVelocity < .01 && angularVelocity > 0) || (angularVelocity > -.01 && angularVelocity < 0)) {
                angularVelocity = 0;
            }
        } else {
            angularVelocity *= .90;
        }
        
        updateAngle(state);
    }

    public void setUpHitbox(Point2D.Double cameraLocation) {
        
        ArrayList<Point2D.Double> hitboxPoints = new ArrayList<>();
        
        try {
        
        hitboxPoints.add(new Point2D.Double(0, 0));
        hitboxPoints.add(new Point2D.Double(activeImage.getWidth(), 0));
        hitboxPoints.add(new Point2D.Double(activeImage.getWidth(), activeImage.getHeight()));
        hitboxPoints.add(new Point2D.Double(0, activeImage.getHeight()));
        
        Point2D.Double centerPoint = new Point2D.Double(activeImage.getWidth() / 2, activeImage.getHeight() / 2);
            hitbox = new Hitbox(hitboxPoints, centerPoint);
        } catch (NullPointerException ex) {
            System.err.println("active image not initialized!");
        }
        
       
    }

    protected void updateHitbox(Point2D.Double cameraLocation) {

        hitbox.moveToLocation(Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()));
        hitbox.rotateToAngle(faceAngle);
    }

    public ArrayList<Shot> getShots() {
        return shots;
    }

    @Override
    public boolean CollisionEventWithShot(Ship ship, Shot shot, ArrayList<Ship> allShips) {

        for (Ship s : allShips) {
            if (s.getShots().contains(shot) && !s.equals(ship)) {
                if (!(ship instanceof EnemyShip && s instanceof EnemyShip))
                    s.removeShot(shot); // removing because it collided
                else
                    return false;
            }
        }
        
            if (!ship.getShots().contains(shot)) {
                if (ship.getShield().getEnergy() > 0) {
                    ship.activateShield(shot.getDamage());
                } else {
                    ship.reduceHull(shot.getDamage());
                    
                    if (hullDurability <= 0)
                        return true;
                }
            }

        return false;
    }

    public boolean CollisionEventWithShip() { 
        return setColliding(true);
    }

    public Hitbox returnHitbox() {
        return hitbox; 
    }

    public boolean canShoot() {
        return canshoot;
    }

    class ShootingService implements Runnable {

        @Override
        public void run() {
            canshoot = true;
        }
    }

    public Shield getShield() {
        return shield;
    }

    public void activateShield(int damage) {
        shield.activate(damage);
    }

    public void removeShot(Shot shotToRemove) {
        shots.remove(shotToRemove);
    }

    public double getShieldHealth() {
        return shield.getEnergy();
    }

    public int getHullHealth() {
        return hullDurability;
    }

    public void reduceHull(double damage) {
        hullDurability -= damage;
    }

    public void purgeShots() {
        for (int i = shots.size() - 1; i > -1; i--) {
            if (shots.get(i).outsideScreen()) {
                shots.remove(i);
            }
        }
    }

    public boolean setColliding(boolean colliding) {
        if (!this.colliding && colliding) {
            this.colliding = true;
            
            if (shield.getEnergy() - collisionDamage >= 0)
            {
                shield.activate(collisionDamage);
            }
            else
            {
                double healthToLoseShield = collisionDamage - Math.abs(shield.getEnergy() - collisionDamage);
                double healthToLoseHull = Math.abs(shield.getEnergy() - collisionDamage);
                
                if (healthToLoseShield > 0)
                    shield.activate(healthToLoseShield);
                
                reduceHull(healthToLoseHull);
                
                if (hullDurability <= 0)
                    return true;
            }
            
        } else if (this.colliding && !colliding) {
            this.colliding = colliding;
        }
        
        return false;
    }

    public boolean isColliding() {
        return colliding;
    }
    
    public BufferedImage getActiveImage()
    {
        return activeImage;
    }
    
    public double getFaceAngle()
    {
        return faceAngle;
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
}
