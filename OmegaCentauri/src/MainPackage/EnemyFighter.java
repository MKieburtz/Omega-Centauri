package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.concurrent.*;

public class EnemyFighter extends EnemyShip {

    private Point dimensions = new Point(0, 0);
    private ArrayList<EnemyShip> others = new ArrayList<>();
    //private boolean incorrectAngle = false;
    private boolean movingAway;
    private int id; // for use with formations
    
    private final boolean startingRight = new Random().nextBoolean();

    // protected?
    private boolean rotating = false;
    private boolean thrusting = false;

    private double targetingAngle = 0;

    public EnemyFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int shootingDelay, int health, int id) // add for image repo
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health);
        
        imagePaths.add("resources/EnemyFighterIdle.png");
        imagePaths.add("resources/EnemyFighterThrusting.png");
        imagePaths.add("resources/EnemyFighterTurningLeft.png");
        imagePaths.add("resources/EnemyFighterTurningRight.png");
        imagePaths.add("resources/EnemyFighterThrustingLeft.png");
        imagePaths.add("resources/EnemyFighterThrustingRight.png");
        images = mediaLoader.loadImages(imagePaths);
        images = Calculator.toCompatibleImages(images);
        activeImage = images.get(0);
        
        setUpHitbox(cameraLocation);

        soundPaths.add("resources/Pulse.wav");
        sounds = mediaLoader.loadSounds(soundPaths);

        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), true, new Point(activeImage.getWidth(), activeImage.getHeight()), 0, 0);
        
        this.id = id;
        
    }
    @Override
    public void update(Player player, Point2D.Double cameraLocation, ArrayList<EnemyShip> otherShips) {
        shield.setRegenRate(.05);
        // main AI goes here
        this.dimensions.x = player.getActiveImage().getWidth();
        this.dimensions.y = player.getActiveImage().getHeight();

        // move in the direction of the ship if it is far away
        // and shoot if it is in range.
        double distanceToPlayer = Calculator.getDistance(location, player.getLocation());

        double angleToPlayer = Calculator.getAngleBetweenTwoPoints(location, player.getLocation());
        //System.out.println(angleToPlayer + " " + faceAngle);

        //System.out.println(angleToPlayer);
        others = (ArrayList<EnemyShip>) otherShips.clone();
        others.remove(this);

        // this block sets movingAway
        if (!movingAway) {
            targetingAngle = angleToPlayer;
        }

        if (!movingAway && distanceToPlayer < 200) {
            movingAway = true;
            if (hullDurability < 30) {
                targetingAngle = (angleToPlayer + 180) % 360;
            } else if (distanceToPlayer < 200) {
                targetingAngle = (angleToPlayer + 90) % 360;
            }
        } else if (distanceToPlayer > 400 && movingAway) {
            movingAway = false;
            targetingAngle = angleToPlayer;
        } else if (distanceToPlayer < 250 && movingAway) {
            if (Math.abs((angleToPlayer + 180) % 360 - faceAngle) > 5) {
                targetingAngle = (angleToPlayer + 180) % 360;
            }
        }

        // this block performs logic based on movingAway
        if (!movingAway) {
            for (EnemyShip ship : others) {
                if (Calculator.getDistance(location, ship.getLocation()) < 200) {
                    double angle = Calculator.getAngleBetweenTwoPoints(location, ship.getLocation());
                    if (id < ship.getID()) {
                        targetingAngle = angle > faceAngle ? targetingAngle - 45 : targetingAngle + 45;
                    }
                }
            }

            rotateToAngle(targetingAngle);

            if (Math.abs(angleToPlayer - faceAngle) < 45) {
                shoot(cameraLocation);
            }
        } else {
            rotateToAngle(targetingAngle);
        }

        if (distanceToPlayer > 200) {
            move(ShipState.Thrusting);
            thrusting = true;
        } else {
            move(ShipState.Drifting);
            thrusting = false;
        }

        setImage();

        // regen shield
        if (shield.getEnergy() <= 100) {
            shield.regen();
        }
    }

    private void rotateToAngle(double angle) {
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, angle);

        if (Math.abs(angle - faceAngle) >= 5) {
            rotating = true;
            if (distances[0] < distances[1]) {
                if (distances[0] > angleIcrement) {
                    rotate(ShipState.TurningLeft);
                }
            } else {
                if (distances[1] > angleIcrement) {
                    rotate(ShipState.TurningRight);
                }
            }
        } else {
            rotating = false;
        }
    }

    private boolean right = startingRight;
    
    @Override
    public void shoot(Point2D.Double cameraLocation) {

        if (canshoot) {
            //playSound(0);
            Random rand = new Random();

            double angle = 360 - faceAngle + rand.nextInt(10) - 5;

            Point2D.Double shotStartingVel
                    = new Point2D.Double(movementVelocity.x + Calculator.CalcAngleMoveX(angle) * 20,
                            movementVelocity.y + Calculator.CalcAngleMoveY(angle) * 20);

            Point2D.Double shotStartingPos = new Point2D.Double();
            
            
            
            if (right) 
            {
                shotStartingPos.x = Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x + Calculator.CalcAngleMoveX(360 - faceAngle + 45) * 25;
                shotStartingPos.y = Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y + Calculator.CalcAngleMoveY(360 - faceAngle + 45) * 25;
            }
            else
            {
                shotStartingPos.x = Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x + Calculator.CalcAngleMoveX(360 - faceAngle - 45) * 25;
                shotStartingPos.y = Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y + Calculator.CalcAngleMoveY(360 - faceAngle - 45) * 25;
            }
            right = !right;
            
            canshoot = false;

            shots.add(new PulseShot(5, 100, false, shotStartingPos, shotStartingVel, angle, true, cameraLocation)); // enemies ovveride

            ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
        }
    }
    
    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);

//        Point2D.Double middle = Calculator.getScreenLocationMiddle(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight());
//        
//        g2d.setColor(Color.BLUE);
//        g2d.drawLine((int)middle.x, (int)middle.y, (int)(middle.x + Calculator.CalcAngleMoveX(360 - faceAngle - 45) * 25), (int)(middle.y + Calculator.CalcAngleMoveY(360 - faceAngle - 45) * 25));       
        shield.draw(g2d, camera.getLocation(), location);

//        Rectangle2D.Float paintRectShield = new Rectangle2D.Float((float) (camera.getSize().x - (camera.getSize().x - 10)),
//                (float) (camera.getSize().y - 85), (float) shield.getEnergy() * 1.5f, 5f);
//        GradientPaint paintShield = new GradientPaint(paintRectShield.x, paintRectShield.y, Color.BLUE, paintRectShield.x + paintRectShield.width,
//                paintRectShield.y + paintRectShield.height, Color.CYAN);
        Rectangle2D.Float paintRectHull = new Rectangle2D.Float((float) (camera.getSize().x - (camera.getSize().x - 10)),
                (float) (camera.getSize().y - 55), (float) hullDurability * 1.5f, 5f);

        GradientPaint paintHull = new GradientPaint(paintRectHull.x, paintRectHull.y, new Color(100, 0, 0), paintRectHull.x + paintRectHull.width,
                paintRectHull.y + paintRectHull.height, new Color(255, 0, 0));
//        g2d.setPaint(paintShield);
//        g2d.fill(paintRectShield);
//
        g2d.setPaint(paintHull);
        g2d.fill(paintRectHull);
    }

    public boolean isMovingAway() {
        return movingAway;
    }

    public int getID() {
        return id;
    }

    private void setImage() {
        if (rotating && thrusting && rotatingRight) {
            changeImage(ShipState.TurningRightThrusting);
        } else if (rotating && thrusting && !rotatingRight) {
            changeImage(ShipState.TurningLeftThrusting);
        } else if (rotating && !thrusting && rotatingRight) {
            changeImage(ShipState.TurningRight);
        } else if (rotating && !thrusting && !rotatingRight) {
            changeImage(ShipState.TurningLeft);
        } else if (!rotating && thrusting) {
            changeImage(ShipState.Thrusting);
        } else if (!rotating && !thrusting) {
            changeImage(ShipState.Idle);
        }
    }

    @Override
    public Point2D.Double getLocation() {
        return location;
    }
}
