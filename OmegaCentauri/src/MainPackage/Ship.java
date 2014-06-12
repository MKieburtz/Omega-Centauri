package MainPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.Clip;
import java.util.concurrent.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public abstract class Ship implements CollisionListener {

    protected int hull;
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
    protected double maxAngularVel = 5;
    protected Rectangle2D.Double hitbox;
    protected String name;
    protected double baseMaxVel;
    protected double maxVel;
    protected double angleIcrement;
    protected double acceleration = .15;
    // File -> FileInputStream -> ImageIO -> buffered image
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected ArrayList<Clip> sounds = new ArrayList<Clip>();
    protected BufferedImage activeImage;
    protected ArrayList<String> imagePaths = new ArrayList<String>();
    protected ArrayList<String> soundPaths = new ArrayList<String>();
    protected MediaLoader mediaLoader = new MediaLoader();
    protected ArrayList<Shot> shots = new ArrayList<Shot>();
    protected boolean canshoot = true;
    protected int shootingDelay;
    protected Shield shield;
    protected boolean rotatingRight = false;
    protected boolean colliding = false;
    protected ScheduledExecutorService ex;

    public Ship(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay, int health) {

        location = new Point2D.Double(x, y);
        nextLocation = new Point2D.Double();
        type = shipType;

        this.baseMaxVel = baseMaxVel;
        this.maxVel = maxVel;
        this.angleIcrement = angleIncrement;
        this.acceleration = acceleration;
        this.shootingDelay = shootingDelay;
        this.hull = health;
        ex = Executors.newSingleThreadScheduledExecutor();
    }

    public BufferedImage getImage() {
        return activeImage;
    }

    public BufferedImage getImage(int index) {
        return this.images.get(index);
    }

    public void draw(Graphics2D g2d, Camera camera) {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform) original.clone();
        
        transform.setToIdentity();
        
        transform.rotate(Math.toRadians(360 - faceAngle),
                Calculator.getScreenLocation(camera.getLocation(), location).x + activeImage.getWidth() / 2,
                Calculator.getScreenLocation(camera.getLocation(), location).y + activeImage.getHeight() / 2);

        transform.translate(Calculator.getScreenLocation(camera.getLocation(), location).x,
                Calculator.getScreenLocation(camera.getLocation(), location).y);

        updateHitbox(camera.getLocation());

        g2d.drawImage(activeImage, transform, null);
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

    public void shoot(Point2D.Double cameraLocation) {
        //playSound(0);

        Random rand = new Random();
        
        double angle = 360 - faceAngle + rand.nextInt(20) - 10;
        
        Point2D.Double ShotStartingVel
                = new Point2D.Double(movementVelocity.x + Calculator.CalcAngleMoveX(angle) * 20,
                        movementVelocity.y + Calculator.CalcAngleMoveY(angle) * 20);

        Point2D.Double ShotStartingPos = new Point2D.Double(
                Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x - 2.5
                + Calculator.CalcAngleMoveX(angle) * 20,
                Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y - 8 + Calculator.CalcAngleMoveY(angle) * 20);

        shots.add(new PulseShot(5, 100, false, ShotStartingPos, ShotStartingVel, angle, false, cameraLocation)); // enemies ovveride
        canshoot = false;

        ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
    }

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

    protected void playSound(int index) {
        sounds.get(index).setFramePosition(0);
        if (!sounds.get(index).isActive()) {
            sounds.get(index).setFramePosition(0);
            sounds.get(index).start();
        }
    }

    public void setUpHitbox(Point2D.Double cameraLocation) {
        try {
            hitbox = new Rectangle2D.Double(Calculator.getScreenLocation(cameraLocation, location).x,
                    Calculator.getScreenLocation(cameraLocation, location).y,
                    activeImage.getWidth(), activeImage.getHeight());
        } catch (NullPointerException ex) {
            System.err.println("activeimage not initialized!");
        }
    }

    protected void updateHitbox(Point2D.Double cameraLocation) {
        hitbox.x = Calculator.getScreenLocation(cameraLocation, location).x;
        hitbox.y = Calculator.getScreenLocation(cameraLocation, location).y;
    }

    public ArrayList<Shot> getShots() {
        return shots;
    }

    @Override
    public boolean CollisionEventWithShot(Ship ship, Shot shot, ArrayList<Ship> allShips) {

        if (ship instanceof Player || ship instanceof EnemyShip) {
            if (!ship.getShots().contains(shot)) {
                if (ship.getShield().getHealth() > 0) {
                    ship.activateShield(shot.getDamage());
                } else {
                    ship.reduceHull(shot.getDamage());
                    
                    if (hull <= 0)
                        return true;
                }
            }
        }

        for (Ship collisionShip : allShips) {
            if (collisionShip.getShots().contains(shot) && !collisionShip.equals(ship)) {
                collisionShip.removeShot(shot);
            }
        }
        return false;
    }

    public boolean CollisionEventWithShip() {
        return setColliding(true);
    }

    public Rectangle2D.Double returnHitbox() {
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

    public int getShieldHealth() {
        return shield.getHealth();
    }

    public int getHullHealth() {
        return hull;
    }

    public void reduceHull(int damage) {
        hull -= damage;
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
            this.colliding = colliding;
            
            if (shield.getHealth() - collisionDamage >= 0)
            {
                shield.activate(collisionDamage);
            }
            else
            {
                int healthToLoseShield = collisionDamage - Math.abs(shield.getHealth() - collisionDamage);
                int healthToLoseHull = Math.abs(shield.getHealth() - collisionDamage);
                
                if (healthToLoseShield > 0)
                    shield.activate(healthToLoseShield);
                
                reduceHull(healthToLoseHull);
                
                if (hull <= 0)
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
}
