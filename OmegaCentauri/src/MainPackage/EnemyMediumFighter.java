package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Kieburtz
 */
public class EnemyMediumFighter extends Enemy 
{
    private int id;

    private Turret[] turrets = new Turret[2];
    private int shootingDelayMissile;
    private int shootingDelayTurret;

    private Resources resources;
    
    private boolean right = true;
    
    private boolean canShootMissile = true;
    private boolean canShootTurret = true;
    
    private Ally targetShip;
    
    private ExplodableWing topWing;
    private ExplodableWing bottomWing;
    
    private boolean bodyExploding = false;
    private boolean wingsExploding = false;
    
    public EnemyMediumFighter(int x, int y, Type shipType, double maxVel, double maxAngleVelocity,
            double angleIncrement, double acceleration, int shootingDelayTurret, 
            int shootingDelayMissile, int health, int id, GameActionListener actionListener) 
    {
        super(x, y, shipType, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelayTurret, health, actionListener);

        this.id = id;
        resources = gameData.getResources();
       
        activeImage = Calculator.toCompatibleImage(resources.getImageForObject("resources/MediumEnemyFighter.png"));

        shield = new Shield(location, Shield.Type.enemyMediumFighter,
                new Point(activeImage.getWidth(), activeImage.getHeight()), 15, 10);

        setUpHitbox();
        
        explosion = new Explosion(Explosion.Type.mediumFighter, new Dimension(activeImage.getWidth(), activeImage.getHeight()));

        turrets[0] = new Turret(25, 335, 45, new Point2D.Double(93, 115), new Dimension(activeImage.getWidth(), activeImage.getHeight()),
                new Point2D.Double(65, 70), 65,faceAngle, this);

        turrets[1] = new Turret(25, 315, 35, new Point2D.Double(93, 240), new Dimension(activeImage.getWidth(), activeImage.getHeight()),
                new Point2D.Double(95, 75), -65, faceAngle, this);
        
        this.shootingDelayMissile = shootingDelayMissile;
        this.shootingDelayTurret = shootingDelayTurret;
        
        ex.schedule(new ShootingService(), shootingDelayMissile, TimeUnit.MILLISECONDS);
    }
    
    class ExplodableWing
    {
        private String topWingPath = "resources/EMFWingTop.png";
        private String bottomWingPath = "resources/EMFWingBottom.png";
        private Explosion explosion;
        private BufferedImage wingImage = null;
        private boolean top;
        private Point2D.Double location = new Point2D.Double();
        private double angle;
        private int steps; // time until explosion
        private boolean exploding;
        private Point2D.Double shipMiddle;
        
        public ExplodableWing(boolean top, Point2D.Double shipLocation, double angle)
        {
            this.angle = angle;
            this.top = top;
            if (top)
            {
                wingImage = Calculator.toCompatibleImage(resources.getImageForObject(topWingPath));
                location = shipLocation;
            }
            else
            {
                wingImage = Calculator.toCompatibleImage(resources.getImageForObject(bottomWingPath));
                location.x = shipLocation.x;
                location.y = shipLocation.y + activeImage.getHeight() - wingImage.getHeight(); // remember these are game coordinates not screen
            }
            explosion = new Explosion(Explosion.Type.wingExplosion, new Dimension(wingImage.getWidth(), wingImage.getHeight()));
            
            steps = 20; // make random
            
            shipMiddle = Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), shipLocation, activeImage.getWidth(), activeImage.getHeight());
        }
        
        public void update()
        {
            double movementAngle;
            if (top)
            {
                movementAngle = Calculator.confineAngleToRange(angle + 90);
                location.x += Calculator.CalcAngleMoveX(360 - movementAngle);
                location.y += Calculator.CalcAngleMoveY(360 - movementAngle);
                angle += .5;
            }
            else
            {
                movementAngle = Calculator.confineAngleToRange(angle - 90);
                location.x += Calculator.CalcAngleMoveX(360 - movementAngle);
                location.y += Calculator.CalcAngleMoveX(360 - movementAngle);
                angle -= .5;
            }
            location = Calculator.rotatePointAroundPoint(location, shipMiddle, angle);
            explosion.updateLocation(location);
            steps--;
            
            if (steps == 0)
            {
                exploding = true;
                wingExploding(this);
            }
        }
        
        public void draw(Graphics2D g2d)
        {
            AffineTransform transform = g2d.getTransform();
            
            if (!exploding)
            {
                g2d.fillRect((int)shipMiddle.x, (int)shipMiddle.y, 3, 3);
                transform.rotate(Math.toRadians(360 - angle), shipMiddle.x, shipMiddle.y);
            
                transform.translate(Calculator.getScreenLocation(gameData.getCameraLocation(), location).x,
                    Calculator.getScreenLocation(gameData.getCameraLocation(), location).y);
            
                g2d.transform(transform);
                
                g2d.drawImage(wingImage, 0, 0, null);
            }
            else
            {
                explosion.draw(g2d);
                if (explosion.isDone())
                {
                    exploding = false;
                }
            }
        }
        
        public boolean exploding()
        {
            return exploding;
        }
    }
    
    private void wingExploding(ExplodableWing wing)
    {
        wingsExploding = true;
    }
    
    @Override
    public void explode()
    {
        super.explode();
        topWing = new ExplodableWing(true, location, faceAngle);
        bottomWing = new ExplodableWing(false, location, faceAngle);
        bodyExploding = true;
    }

    @Override
    public void shoot() {
        if (canShootTurret)
        {
            if (right)
            {
                shots.add(turrets[0].shoot(movementVelocity));
                right = !right;
            }
            else
            {
                shots.add(turrets[1].shoot(movementVelocity));
                right = !right;
            }
            canShootTurret = false;
            ex.schedule(new ShootingServiceTurrets(), shootingDelayTurret, TimeUnit.MILLISECONDS);
        }
        
        if (canShootMissile) {

            double angle = faceAngle;

            Point2D.Double startingLocation = Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight());
            
            shots.add(new Missile(60, startingLocation, null, 360 - angle, targetShip, this));

            canShootMissile = false;

            ex.schedule(new ShootingServiceMisisle(), shootingDelayMissile, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform)original.clone();
        
        if (!bodyExploding && !wingsExploding)
        {
            transform.rotate(Math.toRadians(360 - faceAngle),
                    Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y);
            
            transform.translate(Calculator.getScreenLocation(gameData.getCameraLocation(), location).x,
                    Calculator.getScreenLocation(gameData.getCameraLocation(), location).y);
            
            g2d.transform(transform);
            
            g2d.drawImage(activeImage, 0, 0, null);
            
            for (Turret t : turrets) 
            {
                t.draw(g2d, location);
            }
        }
        else if (bodyExploding)
        {
            explosion.draw(g2d);
            if (explosion.isDone())
            {
                bodyExploding = false;
            }
            
            topWing.draw(g2d);
            g2d.setTransform(original);
            bottomWing.draw(g2d);
        }
        else if (wingsExploding)
        {
            topWing.draw(g2d);
            g2d.setTransform(original);
            bottomWing.draw(g2d);
            
            if (!topWing.exploding() && !bottomWing.exploding())
            {
                wingsExploding = false;
                exploding = false;
                actionListener.entityDoneExploding(this);
            }
        }
        
        g2d.setTransform(original);

        Point2D.Double middle = new Point2D.Double(Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y);
        
        if (shield.isActive())
        {
            shield.draw(g2d, middle, location, faceAngle);
        }
//        g2d.setColor(Color.red);
//        hullHitbox.draw(g2d, camera.getLocation());
    }

    @Override
    public void update() {
        if (!exploding)
        {
            super.update();

            double distanceToTarget = 1000000; // big number

            ArrayList<Ally> allyShips = gameData.getAllyShips();

            for (Ally allyShip : allyShips)
            {
                double distance = Calculator.getDistance(location, allyShip.getLocation());
                if (distance < distanceToTarget)
                {
                    targetShip = allyShip;
                    distanceToTarget = distance;
                }
            }

            double angleToTarget = Calculator.getAngleBetweenTwoPoints(Calculator.getGameLocationMiddle(location,
                    activeImage.getWidth(), activeImage.getHeight()), targetShip.getLocation());

            rotateToAngle(angleToTarget);

            for (Turret t : turrets) 
            {
                t.update(Calculator.getGameLocationMiddle(targetShip.getLocation(),
                        targetShip.getActiveImage().getWidth(), targetShip.getActiveImage().getWidth()),
                        Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()),
                        faceAngle);

            }

            if (Math.abs(angleToTarget - faceAngle) <= 45)
            {
                shoot();
            }

            if (distanceToTarget > 500)
            {
                move(MovementState.Thrusting);
            } 
            else
            {
                move(MovementState.Drifting);
            }
        }
        else
        {
            if (!wingsExploding)
            {
                topWing.update();
                bottomWing.update();
            }
        }
    }
    
    @Override
    public void setUpHitbox()
    {
        try 
        {
            shieldHitbox = new EllipseHitbox(activeImage.getWidth() + 50, activeImage.getHeight() + 50); // constants added to the end compensate for the wings
            Point2D.Double points[] = new Point2D.Double[8];
            points[0] = new Point2D.Double(27, 0);
            points[1] = new Point2D.Double(activeImage.getWidth(), 0);
            points[2] = new Point2D.Double(activeImage.getWidth(), activeImage.getHeight());
            points[3] = new Point2D.Double(27, activeImage.getHeight());
            points[4] = new Point2D.Double(27, activeImage.getHeight() - 172);
            points[5] = new Point2D.Double(0, activeImage.getHeight() - 172);
            points[6] = new Point2D.Double(0, activeImage.getHeight() - 202);
            points[7] = new Point2D.Double(27, activeImage.getHeight() - 202);
            hullHitbox = new ShapeHitbox(points, new Point2D.Double(activeImage.getWidth() / 2, activeImage.getHeight() / 2));
        } 
        catch (NullPointerException ex) 
        {
            System.err.println("active image not initialized!");
        }
    }
    
    @Override
    protected void updateHitbox()
    {
        super.updateHitbox();
        hullHitbox.moveToLocation(Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()));
        hullHitbox.rotateToAngle(faceAngle);
    }

    @Override
    public int getID() 
    {
        return id;
    }

    @Override
    public Point2D.Double getLocation() 
    {
        return location;
    }

    @Override
    public void changeImage(ImageMovementState movementState, ImageRotationState rotationState) 
    {
        // only one image so far
    }

    class ShootingServiceMisisle implements Runnable 
    {

        @Override
        public void run() 
        {
            canShootMissile = true;
        }
    }

    class ShootingServiceTurrets implements Runnable 
    {

        @Override
        public void run()
        {
            canShootTurret = true;
        }
    }
}
