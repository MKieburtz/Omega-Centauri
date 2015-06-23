package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.concurrent.*;

public class EnemyFighter extends EnemyShip 
{
    // make sure image loading order is correct!
    private final int IDLE = 0;
    private final int THRUSTING = 1;
    private final int TURNINGLEFT = 2;
    private final int TURNINGRIGHT = 3;
    private final int TURNINGLEFTTHRUSTING = 4;
    private final int TURNINGRIGHTTHRUSTING = 5;
    
    private Point playerDimensions = new Point(0, 0);
    private ArrayList<EnemyShip> others = new ArrayList<>();
    //private boolean incorrectAngle = false;
    private boolean movingAway;
    private int id; // for use with formations
    
    private final boolean startingRight = new Random().nextBoolean();

    private double targetingAngle = 0;
    
    private Resources resources;

    public EnemyFighter(int x, int y, Type shipType, double maxVel, double maxAngleVelocity,
            double angleIncrement, double acceleration, int shootingDelay, int health, int id,
            GameActionListener actionListener)
    {
        super(x, y, shipType, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelay, health, actionListener);
        
        this.resources = gameData.getResources();
        
        imagePaths.add("resources/EnemyFighterIdle.png");
        imagePaths.add("resources/EnemyFighterThrusting.png");
        imagePaths.add("resources/EnemyFighterTurningLeft.png");
        imagePaths.add("resources/EnemyFighterTurningRight.png");
        imagePaths.add("resources/EnemyFighterThrustingLeft.png");
        imagePaths.add("resources/EnemyFighterThrustingRight.png");
        
        images = resources.getImagesForObject(imagePaths);
        activeImage = images.get(0);
        
        setUpHitbox();
        shield = new Shield(location, true, new Point(activeImage.getWidth(),
                activeImage.getHeight()), 10, 50, activeImage.getWidth() == activeImage.getHeight());
        
        this.id = id;
        
        explosion = new Explosion(Explosion.Type.fighter, new Dimension(activeImage.getWidth(), activeImage.getHeight()));
        
    }
    
    @Override
    public void update() 
    {
        super.update();
        // main AI goes here
        this.playerDimensions.x = gameData.getPlayerShip().getActiveImage().getWidth();
        this.playerDimensions.y = gameData.getPlayerShip().getActiveImage().getHeight();

        // move in the direction of the ship if it is far away
        // and shoot if it is in range.
        double distanceToPlayer = Calculator.getDistance(location, gameData.getPlayerShip().getLocation());

        double angleToPlayer = Calculator.getAngleBetweenTwoPoints(location, gameData.getPlayerShip().getLocation());
        //System.out.println(angleToPlayer + " " + faceAngle);

        //System.out.println(angleToPlayer);
        others = (ArrayList<EnemyShip>) gameData.getEnemyShips().clone();
        others.remove(this);

        // this block sets movingAway
        if (!movingAway) 
        {
            targetingAngle = angleToPlayer;
        }
        
        if (!movingAway && distanceToPlayer < 200) 
        {
            movingAway = true;
            if (hullDurability < 30) 
            {
                targetingAngle = (angleToPlayer + 180) % 360;
            } 
            else if (distanceToPlayer < 200) 
            {
                targetingAngle = (angleToPlayer + 90) % 360;
            }
        } 
        else if (distanceToPlayer > 400 && movingAway) 
        {
            movingAway = false;
            targetingAngle = angleToPlayer;
        }
        else if (distanceToPlayer < 250 && movingAway)
        {
            if (Math.abs((angleToPlayer + 180) % 360 - faceAngle) > 5) 
            {
                targetingAngle = (angleToPlayer + 180) % 360;
            }
        }
        // this block performs logic based on movingAway
        if (!movingAway) 
        {
            for (EnemyShip ship : others) 
            {
                if (Calculator.getDistance(location, ship.getLocation()) < 200) 
                {
                    double angle = Calculator.getAngleBetweenTwoPoints(location, ship.getLocation());
                    if (id < ship.getID()) 
                    {
                        targetingAngle = angle > faceAngle ? targetingAngle - 45 : targetingAngle + 45;
                    }
                }
            }
            rotateToAngle(targetingAngle); // calls changeImage

            if (Math.abs(angleToPlayer - faceAngle) < 45) 
            {
                shoot();
            }
        } 
        else 
        {
            rotateToAngle(targetingAngle); // calls changeImage
        }

        if ((movingAway && Math.abs(faceAngle - targetingAngle) < 15) || (distanceToPlayer > 200 && !movingAway))
        {
            move(MovementState.Thrusting);
            changeImage(ImageMovementState.Thrusting, imageRotationState);
        } 
        else 
        {
            move(MovementState.Drifting);
            changeImage(ImageMovementState.Idle, imageRotationState);
        }
    }

    private boolean right = startingRight;
    
    @Override
    public void shoot() 
    {

        if (canshoot) 
        {
            Random rand = new Random();

            double angle = 360 - faceAngle + rand.nextInt(5) - 5;

            Point2D.Double shotStartingVel
                    = new Point2D.Double(movementVelocity.x + Calculator.CalcAngleMoveX(angle) * 10,
                            movementVelocity.y + Calculator.CalcAngleMoveY(angle) * 10);

            Point2D.Double shotStartingPos = new Point2D.Double();
                                                                                                         
            if (right) 
            {
                shotStartingPos.x = Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()).x + Calculator.CalcAngleMoveX(360 - faceAngle + 45) * 25;
                shotStartingPos.y = Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()).y + Calculator.CalcAngleMoveY(360 - faceAngle + 45) * 25;
            }
            else {
                shotStartingPos.x = Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()).x + Calculator.CalcAngleMoveX(360 - faceAngle - 45) * 25;
                shotStartingPos.y = Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()).y + Calculator.CalcAngleMoveY(360 - faceAngle - 45) * 25;
            }
            right = !right;
            
            canshoot = false;

            shots.add(new PulseShot(5, shotStartingPos, shotStartingVel, angle, true, this));

            ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        
        AffineTransform original = g2d.getTransform();
        
        super.draw(g2d);
        
        g2d.setTransform(original);
        
        Point2D.Double middle = new Point2D.Double(Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y);
        
        shield.draw(g2d, location, middle, middle, faceAngle);
        
        g2d.setColor(Color.red);
        //hitbox.draw(g2d, camera.getLocation());
    }

    public boolean isMovingAway() 
    {
        return movingAway;
    }

    @Override
    public int getID() 
    {
        return id;
    }
    
    public void changeImage(ImageMovementState movementState, ImageRotationState rotationState) 
    {
        switch (movementState) 
        {
            case Idle:
                imageMovementState = ImageMovementState.Idle;
                switch (rotationState)
                {
                    case Idle:
                        imageRotationState = ImageRotationState.Idle;
                        activeImage = images.get(IDLE);
                        break;
                    case rotatingLeft:
                        imageRotationState = ImageRotationState.rotatingLeft;
                        activeImage = images.get(TURNINGLEFT);
                        break;
                    case rotatingRight:
                        imageRotationState = ImageRotationState.rotatingRight;
                        activeImage = images.get(TURNINGRIGHT);
                        break;
                }
                break;
            case Thrusting:
                imageMovementState = ImageMovementState.Thrusting;
                switch (rotationState)
                {
                    case Idle:
                        imageRotationState = ImageRotationState.Idle;
                        activeImage = images.get(THRUSTING);
                        break;
                    case rotatingLeft:
                        imageRotationState = ImageRotationState.rotatingLeft;
                        activeImage = images.get(TURNINGLEFTTHRUSTING);
                        break;
                    case rotatingRight: 
                        imageRotationState = ImageRotationState.rotatingRight;
                        activeImage = images.get(TURNINGRIGHTTHRUSTING);
                        break;
                }
                break;         
        }
    }

    @Override
    public Point2D.Double getLocation() 
    {
        return location;
    }
}
