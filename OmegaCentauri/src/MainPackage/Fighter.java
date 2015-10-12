package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael Kieburtz
 */
public class Fighter extends Ally implements GameEntity, Controllable {

    // make sure image loading order is correct!
    private final int IDLE = 0;
    private final int THRUSTING = 1;
    private final int TURNINGLEFT = 2;
    private final int TURNINGRIGHT = 3;
    private final int TURNINGLEFTTHRUSTING = 4;
    private final int TURNINGRIGHTTHRUSTING = 5;
    
    private final Dimension EMFDimensions = new Dimension(160, 378);
    
    private Resources resources;
    
    private BufferedImage highlightImage;
    private boolean highlighted;
    
    public Fighter(int x, int y, Type shipType, double maxVel, double maxAngleVel,
            double angleIncrement, double acceleration, int shootingDelay, int health,
            GameActionListener actionListener) {
        
        super(x, y, shipType, maxVel, maxAngleVel, angleIncrement, acceleration, shootingDelay, health, actionListener);
        resources = gameData.getResources();
        imagePaths.add("resources/FighterIdle.png");
        imagePaths.add("resources/FighterThrust.png");
        imagePaths.add("resources/FighterLeft.png");
        imagePaths.add("resources/FighterRight.png");
        imagePaths.add("resources/FighterThrustLeft.png");
        imagePaths.add("resources/FighterThrustRight.png");
        images = resources.getImagesForObject(imagePaths);

        highlightImage = resources.getImageForObject("resources/FighterHighlight.png");
        
        activeImage = images.get(0);
        shield = new Shield(location, Shield.Type.fighter, new Point(activeImage.getWidth(), activeImage.getHeight()),
                10, 100);
        setUpHitbox();

        soundPaths.add("resources/Pulse.wav");
        
        sounds = resources.getSoundsForObject(soundPaths);
        
        explosion = new Explosion(Explosion.Type.fighter, new Dimension(activeImage.getWidth(), activeImage.getHeight()));
        
        faceAngle = 0;
    }

    @Override
    public void shoot() {
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

        shots.add(new PulseShot(5, ShotStartingPos, ShotStartingVel, angle, false, this));

        ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void update() // A.I
    {
        super.update();
        
        ArrayList<Enemy> enemyShips = gameData.getEnemyShips();
        Enemy targetShip = null;
        
        double distanceToTarget = 100000; // just a big number
        
        double targetAngle; // the angle to move towards
        
        for (Enemy enemyShip : enemyShips) // reevaluate the closest ship
        {
            double distance = Calculator.getDistance(location, enemyShip.getLocation());
            if (distance < distanceToTarget)
            {
                distanceToTarget = distance;
                targetShip = enemyShip;
            }
        }
        if (targetShip instanceof EnemyFighter)
        {
            targetAngle = Calculator.getAngleBetweenTwoPoints(location, targetShip.getLocation());
        }
        else if (targetShip instanceof EnemyMediumFighter)
        {
            Point2D.Double shipLocation = Calculator.getGameLocationMiddle(targetShip.getLocation(), EMFDimensions.width, EMFDimensions.height);
            targetAngle = Calculator.getAngleBetweenTwoPoints(location, shipLocation);
        }
        else
        {
            targetAngle = 0;
        }
        
        rotateToAngle(targetAngle);
        
        if (Math.abs(targetAngle - faceAngle) < 30 && distanceToTarget < 700)
        {
            if (canshoot)
            {
                shoot();
            }
        }
        
        if (distanceToTarget > 100)
        {
            changeImage(ImageMovementState.Thrusting, imageRotationState);
            move(MovementState.Thrusting);
        }
        else
        {
            changeImage(ImageMovementState.Idle, imageRotationState);
            move(moving() ? MovementState.Drifting : MovementState.Idle);
        }
        
    }
    
    @Override
    public void draw(Graphics2D g2d)
    {
        AffineTransform original = g2d.getTransform();
        
        super.draw(g2d);

        g2d.setTransform(original);
        //g2d.setColor(Color.red);
        //hitbox.draw(g2d, camera.getLocation());
        
        Point2D.Double middle = new Point2D.Double(Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).x,
                    Calculator.getScreenLocationMiddle(gameData.getCameraLocation(), location, activeImage.getWidth(), activeImage.getHeight()).y);
        
        if (shield.isActive())
        {
            shield.draw(g2d, middle, location, faceAngle);
        }
    }
    
    @Override
    public void update(ArrayList<Command> commands)
    {
        super.update(commands);
        boolean noMovementCommand = true;
        boolean noRotationCommand = true;
        for (Command c : commands)
        {
            if (c instanceof MovementCommand)
            {
                noMovementCommand = false;
                move(MovementState.Thrusting);
                changeImage(ImageMovementState.Thrusting, imageRotationState);
            }
            else if (c instanceof RotationCommand)
            {
                noRotationCommand = false;
                switch(c.getValue())
                {
                    case 0: // rotate left
                        rotate(RotationState.TurningLeft);
                        changeImage(imageMovementState, ImageRotationState.rotatingLeft);
                        break;
                    case 1: // rotate right
                        rotate(RotationState.TurningRight);
                        changeImage(imageMovementState, ImageRotationState.rotatingRight);
                        break;
                }
            }
            else if (c instanceof ShootingCommand)
            {
                if (canshoot)
                {
                    shoot();
                }
            }
        }
        // check for drifts
        if (noMovementCommand)
        {
            if (moving())
            {
                move(MovementState.Drifting);
                changeImage(ImageMovementState.Idle, imageRotationState);
            }
        }
        if (noRotationCommand)
        {
            if (rotating())
            {
                if (rotationState == RotationState.TurningRight || rotationState == RotationState.TurningRightDrifting)
                {
                    rotate(RotationState.TurningRightDrifting);
                    changeImage(imageMovementState, ImageRotationState.Idle);
                }
                else if (rotationState == RotationState.TurningLeft || rotationState == RotationState.TurningLeftDrifting)
                {
                    rotate(RotationState.TurningLeftDrifting);
                    changeImage(imageMovementState, ImageRotationState.Idle);
                }
            }
        }
    }

    @Override
    public void changeImage(ImageMovementState movementState, ImageRotationState rotationState) 
    {
        if (movementState == imageMovementState && rotationState == imageRotationState)
        {
            return;
        }
        //System.out.println("called");
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
    public void setHighlighted(boolean highlighted)
    {
        this.highlighted = highlighted;
    }
}
