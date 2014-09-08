package MainPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public abstract class Shot {

    protected int range;
    protected double distanceTraveled = 0;
    protected int life;
    protected int damage;
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected ArrayList<String> imagePaths = new ArrayList<String>();
    protected BufferedImage activeImage;
    protected Point2D.Double location;
    protected double faceAngle;
    protected Point2D.Double velocity;
    protected int maxVel;
    protected Hitbox hitbox;

    private Ship owner; // the ship that fired the shot

    public Shot(int damage, int range, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner) {
        life = 0;
        this.damage = damage;
        this.range = range;
        this.location = location;
        this.velocity = velocity;

        this.faceAngle = angle;
        this.maxVel = 5;

        this.owner = owner;
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation) 
    {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform) original.clone();

        transform.setToIdentity();

        transform.rotate(Math.toRadians(faceAngle),
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x,
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y);

        transform.translate(Calculator.getScreenLocation(cameraLocation, location).x, Calculator.getScreenLocation(cameraLocation, location).y);

        g2d.transform(transform);

        g2d.drawImage(activeImage, 0, 0, null);

        g2d.setTransform(original);

        //g2d.setColor(Color.red);
        //g2d.draw(hitbox);
    }

    public void update() {}

    protected void setUpHitbox(Point2D.Double cameraLocation) {
        ArrayList<Point2D.Double> hitboxPoints = new ArrayList<>();

        try {
            hitboxPoints.add(new Point2D.Double(0, 0));
            hitboxPoints.add(new Point2D.Double(activeImage.getWidth(), 0));
            hitboxPoints.add(new Point2D.Double(activeImage.getWidth(), activeImage.getHeight()));
            hitboxPoints.add(new Point2D.Double(0, activeImage.getHeight()));

            Point2D.Double centerPoint = new Point2D.Double(activeImage.getWidth() / 2, activeImage.getHeight() / 2);
            hitbox = new Hitbox(hitboxPoints, centerPoint);

            hitbox.rotateToAngle(360 - faceAngle);

        } catch (NullPointerException e) {
            System.err.println("activeimage not initialized!");
        }
    }

    public boolean collisionEventWithShot(Shot shot, Shot otherShot, ArrayList<Ship> allShips) { // the return value is only useful to subclasses
        boolean removed = false;
        //TODO: missiles can collide with eachother if they're different (Enemy, Ally).
        if (shot instanceof PhysicalShot ^ otherShot instanceof PhysicalShot) { // ^ means one or the other but not both
            // enemy ship's shots shouldn't destroy eachother
            if (!(shot.getOwner() instanceof EnemyShip && otherShot.getOwner() instanceof EnemyShip)) {
                for (Ship ship : allShips) {
                    if (shot.getOwner().equals(ship)) {
                        ship.removeShot(shot);
                        removed = true;
                    }
                }
            }

        }
        return removed;
    }

    protected void updateHitbox(Point2D.Double cameraLocation) {
        hitbox.moveToLocation(Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()));
    }

    public Hitbox returnHitbox() {
        return hitbox;
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public Point getSize() // ovveride if animated
    {
        return new Point(images.get(0).getWidth(), images.get(0).getHeight());
    }

    public BufferedImage getImage() {
        return images.get(0);
    }

    public boolean outsideScreen() // assumes a 20 thousand by 20 thousand screen
    {
        if (location.x < 10000 && location.x > -10000) {
            if (location.y < 10000 && location.y > -10000) {
                return false;
            }
        }
        return true;
    }

    public boolean imagesLoaded() {
        return !images.isEmpty();
    }

    public int getDamage() {
        return damage;
    }

    public Ship getOwner() {
        return owner;
    }
    
    public boolean exceededRange()
    {
        return distanceTraveled > range;
    }
}
