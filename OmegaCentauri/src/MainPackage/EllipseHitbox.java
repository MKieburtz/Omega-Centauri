package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class EllipseHitbox implements Hitbox{

    private Point2D.Double centerPoint = new Point2D.Double();
    // this is the longer "radius" length
    private double semiMajorAxisLength;
    // this is the shorter "radius" length
    private double semiMinorAxisLength;

    private double horizontalRadiusLength;
    private double verticalRadiusLength;

    private double angle = 0;

    private boolean circle;

    public EllipseHitbox(double horizontalLength, double verticalLength) { // the !entire! horizontal length
        this.horizontalRadiusLength = horizontalLength;
        this.verticalRadiusLength = verticalLength;

        this.semiMajorAxisLength = horizontalLength >= verticalLength ? horizontalLength / 2 : verticalLength / 2;
        this.semiMinorAxisLength = horizontalLength < verticalLength ? horizontalLength / 2 : verticalLength / 2;

        circle = semiMajorAxisLength == semiMinorAxisLength;
        
    }

    @Override
    public void rotateToAngle(double angle) {
        this.angle = angle;
        this.angle = Calculator.confineAngleToRange(this.angle);
    }

    @Override
    public void rotateRelitive(double amount) {
        angle += amount;
        angle = Calculator.confineAngleToRange(this.angle);
    }

    @Override
    public void moveToLocation(Point2D.Double location) {
        this.centerPoint = location;
    }

    @Override
    public void moveRelitive(Point2D.Double distance) {
        centerPoint.x += distance.x;
        centerPoint.y += distance.y;
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation) {
        AffineTransform original = g2d.getTransform();
        g2d.rotate(Math.toRadians(360 - angle),
                Calculator.getScreenLocation(cameraLocation, centerPoint).x,
                Calculator.getScreenLocation(cameraLocation, centerPoint).y);
        Point2D.Double locationOnScreen = Calculator.getScreenLocation(cameraLocation, centerPoint);
        Ellipse2D.Double ellipse = new Ellipse2D.Double(locationOnScreen.x - horizontalRadiusLength / 2, locationOnScreen.y - verticalRadiusLength / 2, horizontalRadiusLength, verticalRadiusLength);
        g2d.draw(ellipse);
        g2d.fillRect((int)Calculator.getScreenLocation(cameraLocation, centerPoint).x, (int)Calculator.getScreenLocation(cameraLocation, centerPoint).y, 2, 2);
        g2d.setTransform(original);
    }

    @Override
    public boolean collides(RectangularHitbox other) {
        Point2D.Double rotatedPoint = Calculator.rotatePointAroundPoint(other.getCollisionPoint(), centerPoint, -angle);
        return pointInsideEllipse(centerPoint, rotatedPoint);
    }
    
    @Override
    public boolean collides(ShapeHitbox other)
    {
        throw new UnsupportedOperationException();
    }

    private boolean pointInsideEllipse(Point2D.Double ellipseCenter, Point2D.Double point) {
        double xPart = Math.pow(point.x - ellipseCenter.x, 2) / Math.pow(horizontalRadiusLength / 2, 2);
        double yPart = Math.pow(point.y - ellipseCenter.y, 2) / Math.pow(verticalRadiusLength / 2, 2);

        return (xPart + yPart) <= 1;
    }
    
    public double getAngleOnEllipse(RectangularHitbox other)
    {
        return Calculator.confineAngleToRange(Calculator.getAngleOfEllipseAtAngle(getAngleToHitbox(other), horizontalRadiusLength, verticalRadiusLength));
    }
    
    public double getAngleToHitbox(RectangularHitbox other)
    {
        //Point2D.Double rotatedPoint =  Calculator.rotatePointAroundPoint(other.getCollisionPoint(), other.getCenterPoint(), -other.getAngle());
        double angleToHitbox = Calculator.getAngleBetweenTwoPoints(centerPoint, other.getCollisionPoint());
        //System.err.println(angleToHitbox);
        return angleToHitbox;
    }
    
    public Point2D.Double getCenterPoint()
    {
        return centerPoint;
    }
    
    public Dimension getDimensions()
    {
        return new Dimension((int)horizontalRadiusLength / 2, (int)verticalRadiusLength / 2);
    }
}
