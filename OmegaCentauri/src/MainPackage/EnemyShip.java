package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public abstract class EnemyShip extends Ship {

    private Point2D.Double playerLocation = new Point2D.Double(0, 0);
    private Point dimensions = new Point(0, 0);
    //private ArrayList<EnemyShip> ships = new ArrayList<EnemyShip>();
    private ArrayList<EnemyShip> others = new ArrayList<EnemyShip>();
    private boolean incorrectAngle = false;
    private int steps = 0;

    private double targetingAngle = 0;

    public EnemyShip(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay, int health) // delegate assigning images to the types of ships
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health);
    }

    protected void update(Player player, Point2D.Double cameraLocation, ArrayList<EnemyShip> enemyShips) {
        shield.regenRate = .05;
        // main AI goes here
        this.playerLocation = player.getLocation();
        this.dimensions.x = player.getActiveImage().getWidth();
        this.dimensions.y = player.getActiveImage().getHeight();

        // move in the direction of the ship if it is far away
        // and shoot if it is in range.
        double distance = Calculator.getDistance(location, player.getLocation());

        double angle = Calculator.getAngleBetweenTwoPoints(location, player.getLocation());
        //System.out.println(angle + " " + faceAngle);

        //System.out.println(angle);
        others = (ArrayList<EnemyShip>) enemyShips.clone();
        others.remove(this);
        int[] stepArray = {5,10,15};
        if (!others.isEmpty()) {
            double shortest = Calculator.getDistance(location, others.get(0).getLocation());
            for (EnemyShip s : others) {
                double distanceBetweenOther = Calculator.getDistance(location, s.getLocation());
                if (Math.abs(Calculator.getAngleBetweenTwoPoints(location, s.getLocation()) - faceAngle) < 45
                        || distanceBetweenOther < 400) {
                    if (!incorrectAngle) {
                        incorrectAngle = true;
                        targetingAngle = faceAngle - 45;
                        if (distanceBetweenOther < shortest) {
                            shortest = distanceBetweenOther;
                        }
                    }
                } else {
                    targetingAngle = angle;
                    incorrectAngle = false;
                }

            }
            if (incorrectAngle && !Arrays.asList(stepArray).contains(steps)) { // if steps != 5,10,15
                if (shortest <= 100) {
                    steps = 150;
                } else if (shortest > 100 && shortest <= 300) {
                    steps = 75;
                } else if (shortest > 300 && shortest <= 600)
                {
                    steps = 50;
                }
            }
        } else {
            targetingAngle = angle;
        }

        if (hull > 30) {
            if (steps == 0) {
                rotateToAngle(targetingAngle);
            }

            if (distance < 500 && Math.abs(angle - faceAngle) < 45) {
                shoot(cameraLocation);
            }
            if (distance > 200) {
                move(ShipState.Thrusting);
                if (steps > 0)
                    steps--;
            } else {
                move(ShipState.Drifting);
            }
        } else {
            if (steps == 0) {
                rotateToAngle((targetingAngle + 180) % 360);
            }

            if (distance > 500) {
                move(ShipState.Drifting);
            } else {
                move(ShipState.Thrusting);
                if (steps > 0)
                    steps--;
            }

        }
        if (shield.getHealth() < 100) {
            shield.setHealth(shield.getHealth() + shield.regenRate);
        }
    }

    protected void rotateToAngle(double angle) {
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, angle);

        if (Math.abs(angle - faceAngle) >= 5) {
            if (distances[0] < distances[1]) {
                if (distances[0] > angleIcrement) {
                    rotate(ShipState.TurningLeft);
                }
            } else {
                if (distances[1] > angleIcrement) {
                    rotate(ShipState.TurningRight);
                }
            }
        }
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) {

        if (canshoot) {
            Random rand = new Random();

            double angle = 360 - faceAngle + rand.nextInt(20) - 10;

            Point2D.Double ShotStartingVel
                    = new Point2D.Double(movementVelocity.x + Calculator.CalcAngleMoveX(angle) * 20,
                            movementVelocity.y + Calculator.CalcAngleMoveY(angle) * 20);

            Point2D.Double ShotStartingPos = new Point2D.Double(
                    Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x - 2.5
                    + Calculator.CalcAngleMoveX(angle) * 20,
                    Calculator.getScreenLocationMiddleForPlayer(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y - 8 + Calculator.CalcAngleMoveY(angle) * 20);

            shots.add(new PulseShot(5, 100, false, ShotStartingPos, ShotStartingVel, angle, true, cameraLocation));
            canshoot = false;

            ex.schedule(new ShootingService(), shootingDelay, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);

        Point2D.Double middleOfPlayer = Calculator.getScreenLocationMiddleForPlayer(camera.getLocation(), playerLocation, dimensions.x, dimensions.y);
        Point2D.Double middleOfSelf = Calculator.getScreenLocationMiddleForPlayer(camera.getLocation(), location, activeImage.getWidth(), activeImage.getHeight());

        for (EnemyShip s : others) {
//            g2d.setColor(Calculator.getDistance(location, s.getLocation()) < 400 ? Color.RED : Color.GREEN);
//            g2d.drawLine((int) (location.x - camera.getLocation().x), (int) (location.y - camera.getLocation().y),
//                    (int) (s.getLocation().x - camera.getLocation().x), (int) (s.getLocation().y - camera.getLocation().y));
//
//            g2d.setColor(Color.YELLOW);
//
//            g2d.drawLine((int) (location.x - camera.getLocation().x), (int) (location.y - camera.getLocation().y),
//                    (int) (s.getLocation().x - camera.getLocation().x), (int) (location.y - camera.getLocation().y));
//
//            g2d.drawLine((int) (s.getLocation().x - camera.getLocation().x), (int) (s.getLocation().y - camera.getLocation().y),
//                    (int) (s.getLocation().x - camera.getLocation().x), (int) (location.y - camera.getLocation().y));
//
//            g2d.setColor(Color.BLUE);
//
//            g2d.drawLine((int) (location.x - camera.getLocation().x), (int) (location.y - camera.getLocation().y),
//                    (int) ((middleOfSelf.x + Math.cos(Math.toRadians(targetingAngle)) * Calculator.getDistance(location, playerLocation)) - camera.getLocation().x),
//                    (int) ((middleOfSelf.y - Math.sin(Math.toRadians(targetingAngle)) * Calculator.getDistance(location, playerLocation)) - camera.getLocation().y));
//
//            DecimalFormat f = new DecimalFormat("0.#");
//
//            g2d.setColor(Color.GREEN);
//
//            g2d.drawString("\u03F4 = " + f.format(Calculator.getAngleBetweenTwoPoints(location, s.getLocation())),
//                    (int) ((location.x - camera.getLocation().x) + Math.cos(Math.toRadians(Calculator.getAngleBetweenTwoPoints(location, s.getLocation()))) * 70),
//                    (int) ((location.y - camera.getLocation().y) + Math.sin(Math.toRadians(Calculator.getAngleBetweenTwoPoints(location, s.getLocation()))) * 70));

//            g2d.setColor(Color.BLUE);
//
//            g2d.drawString("Hypotnuse distance = " + f.format(Calculator.getDistance(location, s.getLocation())), 100, 100);
//
//            double legDistH = Math.cos(Math.toRadians(Calculator.getAngleBetweenTwoPoints(location, s.getLocation()) % 90)) * Calculator.getDistance(location, s.getLocation());
//            double legDistV = Math.sin(Math.toRadians(Calculator.getAngleBetweenTwoPoints(location, s.getLocation()) % 90)) * Calculator.getDistance(location, s.getLocation());
//
//            g2d.drawString("Leg distance Horizontal = " + f.format(legDistH), 100, 120);
//
//            g2d.drawString("Leg distance Vertical = " + f.format(legDistV), 100, 140);
        }

        shield.draw(g2d, camera.getLocation(), location);

        Rectangle2D.Float paintRectShield = new Rectangle2D.Float((float) (camera.getSize().x - (camera.getSize().x - 10)),
                (float) (camera.getSize().y - 85), (float) shield.getHealth() * 1.5f, 5f);

        GradientPaint paintShield = new GradientPaint(paintRectShield.x, paintRectShield.y, Color.BLUE, paintRectShield.x + paintRectShield.width,
                paintRectShield.y + paintRectShield.height, Color.CYAN);

        Rectangle2D.Float paintRectHull = new Rectangle2D.Float((float) (camera.getSize().x - (camera.getSize().x - 10)),
                (float) (camera.getSize().y - 55), (float) hull * 1.5f, 5f);

        GradientPaint paintHull = new GradientPaint(paintRectHull.x, paintRectHull.y, new Color(100, 0, 0), paintRectHull.x + paintRectHull.width,
                paintRectHull.y + paintRectHull.height, new Color(255, 0, 0));

        g2d.setPaint(paintShield);
        g2d.fill(paintRectShield);

        g2d.setPaint(paintHull);
        g2d.fill(paintRectHull);
    }
}
