package MainPackage;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class EllipseHitbox {

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

    public void rotateToAngle(double angle) {
        this.angle = angle;
        this.angle = Calculator.confineAngleToRange(this.angle);
    }

    public void rotateRelative(double amount) {
        angle += amount;
        angle = Calculator.confineAngleToRange(angle);
    }

    public void moveToLocation(Point2D.Double location) {
        this.centerPoint = location;
    }

    public void moveRelative(Point2D.Double distance) {
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
        g2d.setTransform(original);
    }

    public boolean collides(RectangularHitbox other) {
        Point2D.Double rotatedCenter = Calculator.rotatePointAroundPoint(centerPoint, other.getCenterPoint(), -other.getAngle());

        Point2D.Double closestRectPoint = getClosestPointOnEdgeOfRectangle(other.getTopLeftPoint().x,
                other.getTopLeftPoint().y, other.getDimensions().width, other.getDimensions().height, rotatedCenter);

        if (!circle) {
            closestRectPoint = Calculator.rotatePointAroundPoint(closestRectPoint, centerPoint, -angle);
            return pointInsideEllipse(centerPoint, semiMajorAxisLength, semiMinorAxisLength, closestRectPoint);
        } else {
            return Calculator.getDistance(closestRectPoint, centerPoint) < semiMajorAxisLength; // semiMajor == semiMinor so we could use either
        }
    }

    private Point2D.Double getClosestPointOnEdgeOfRectangle(double rectX, double rectY, double rectwidth, double rectHeight, Point2D.Double location) {
        double right = rectX + rectwidth;
        double bottom = rectY + rectHeight;

        double x = Calculator.clamp(location.x, rectX, right);
        double y = Calculator.clamp(location.y, rectY, bottom);
        double distanceLeft = Math.abs(x - rectX);
        double distanceRight = Math.abs(x - right);
        double distanceTop = Math.abs(y - rectY);
        double distanceBottom = Math.abs(y - bottom);

        double min = Calculator.min(distanceLeft, distanceRight, distanceTop, distanceBottom);

        if (min == distanceTop) {
            return new Point2D.Double(x, rectY);
        }
        if (min == distanceBottom) {
            return new Point2D.Double(x, bottom);
        }
        if (min == distanceLeft) {
            return new Point2D.Double(rectX, y);
        }

        return new Point2D.Double(right, y); // else...
    }

    private boolean pointInsideEllipse(Point2D.Double ellipseCenter, double semiMajorLength, double semiMinorLength, Point2D.Double point) {
        double xPart = Math.pow(point.x - ellipseCenter.x, 2) / Math.pow(horizontalRadiusLength / 2, 2);
        double yPart = Math.pow(point.y - ellipseCenter.y, 2) / Math.pow(verticalRadiusLength / 2, 2);

        return (xPart + yPart) <= 1;
    }
}
