package MainPackage;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public abstract class Shot {

    protected int range;
    protected double distanceTraveled = 0;
    protected int damage;
    protected ArrayList<BufferedImage> images = new ArrayList<>();
    protected ArrayList<String> imagePaths = new ArrayList<>();
    protected BufferedImage activeImage;
    protected Point2D.Double location;
    protected double faceAngle;
    protected Point2D.Double velocity;
    protected int maxVel;
    protected RectangularHitbox hitbox;

    protected Ship owner; // the ship that fired the shot

    public Shot(int damage, int range, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner) {
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

        transform.rotate(Math.toRadians(faceAngle),
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x,
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y);

        transform.translate(Calculator.getScreenLocation(cameraLocation, location).x, Calculator.getScreenLocation(cameraLocation, location).y);

        g2d.transform(transform);

        g2d.drawImage(activeImage, 0, 0, null);

        g2d.setTransform(original);
        
        
       // hitbox.draw(g2d, cameraLocation);
    }

    public void update() {}

    protected void setUpHitbox(Point2D.Double cameraLocation) {
        Point2D.Double[] hitboxPoints = new Point2D.Double[4]; 

        try {
            hitboxPoints[0] = new Point2D.Double(0, 0);
            hitboxPoints[1] = new Point2D.Double(activeImage.getWidth(), 0);
            hitboxPoints[2] = new Point2D.Double(activeImage.getWidth(), activeImage.getHeight());
            hitboxPoints[3] = new Point2D.Double(0, activeImage.getHeight());
            hitbox = new RectangularHitbox(hitboxPoints);

        } catch (NullPointerException e) {
            System.err.println("activeimage not initialized!");
        }
    }

    public boolean collisionEventWithShot(Shot shot, Shot otherShot, ArrayList<Ship> allShips) { // the return value is only useful to subclasses
        boolean removed = false;
        //TODO: missiles can collide with eachother if they're different (Enemy, Ally).
        if (shot instanceof PhysicalShot ^ otherShot instanceof PhysicalShot) { // ^ means one or the other but not both (exclusive OR) (XOR)
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
        hitbox.moveToLocation(Calculator.getGameLocationMiddle(location, activeImage.getWidth(), activeImage.getHeight()));
    }

    public RectangularHitbox returnHitbox() {
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

    public boolean outsideScreen(Dimension screensize) 
    {
        if (location.x < screensize.width && location.x > 0) {
            if (location.y < screensize.height && location.y > 0) {
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
    
    public boolean isDying(){return false;}
}
