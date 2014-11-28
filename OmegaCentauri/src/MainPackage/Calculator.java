package MainPackage;

import java.awt.*;
import java.awt.Transparency;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Calculator {
    /* 
     * this class that will have all sorts of useful methods to help
     * calculate stuff
     */

    private Calculator(){}
    

    public static double CalcAngleMoveX(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double CalcAngleMoveY(double angle) {
        return Math.sin(Math.toRadians(angle));
    }
    /**
     * @param pt1 First point   
     * @param pt2 Second point
     * @return distance between two points
     */
    public static double getDistance(Point2D.Double pt1, Point2D.Double pt2) {
        return Math.sqrt(Math.pow(Math.abs(pt2.x - pt1.x), 2) + Math.pow(Math.abs(pt2.y - pt1.y), 2));
    }

    /**
     * @return angle between two points IN DEGREES
     */ 
    public static double getAngleBetweenTwoPoints(Point2D.Double pt1, Point2D.Double pt2) {
        double angle = (double) Math.toDegrees(Math.atan2(pt2.x - pt1.x, pt2.y - pt1.y)) - 90;

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
    
    public static double[] getDistancesBetweenAngles(double currentAngle, double targetAngle) // decides whether an angle or coterminal angle is faster
    {
        if (targetAngle > currentAngle)
        {
            return new double[] {targetAngle - currentAngle, 360 - (targetAngle - currentAngle)};
            
        } else if (currentAngle > targetAngle)
        {
            return new double[] {360 - (currentAngle - targetAngle), currentAngle - targetAngle };
        }
        
        return new double[] {0.0, 360};
    }
    
    public static Point2D.Double getScreenLocation(Point2D.Double cameraLocation, Point2D.Double location) {
        double x = location.x - cameraLocation.x;
        double y = location.y - cameraLocation.y;

        return new Point2D.Double(x, y);
    }
    
    public static Point2D.Double getScreenLocationMiddle(Point2D.Double cameraLocation, Point2D.Double location,
            double imageWidth, double imageHeight) {
        double x = location.x - cameraLocation.x + (imageWidth / 2);
        double y = location.y - cameraLocation.y + (imageHeight / 2);
        
        return new Point2D.Double(x, y);
    }
    
    public static Point2D.Double getGameLocationMiddle(Point2D.Double location,
            double imageWidth, double imageHeight) {
        double x = location.x + imageWidth / 2;
        double y = location.y + imageHeight / 2;

        return new Point2D.Double(x, y);
    }
    
    public static ArrayList<BufferedImage> toCompatibleImages(ArrayList<BufferedImage> images)
    {
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        for (int i = 0; i < images.size(); i++)
        {
            BufferedImage tempImage = config.createCompatibleImage(images.get(i).getWidth(), images.get(i).getHeight(), System.getProperty("os.name").contains("OS X") ? Transparency.TRANSLUCENT : images.get(i).getTransparency());
            Graphics2D g2d = tempImage.createGraphics();
            
            g2d.drawImage(images.get(i), 0, 0, null);
            images.set(i, tempImage);

            g2d.dispose();
        }
        
        return images;
    }
    
    public static BufferedImage toCompatibleImage(BufferedImage image)
    {
            GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
             
            BufferedImage tempImage = config.createCompatibleImage(image.getWidth(), image.getHeight(), System.getProperty("os.name").contains("OS X") ? Transparency.TRANSLUCENT : image.getTransparency());
            Graphics2D g2d = tempImage.createGraphics();
            
            g2d.drawImage(image, 0, 0, null);

            g2d.dispose();
            
            return image;
    }
    
    public static double confineAngleToRange(double angle) // makes the angle into the range (0 - 360]
    {
        double newAngle = angle;
        if (newAngle <= 0)
        {
            newAngle += 360;
        }
        newAngle %= 360;
        
        return newAngle;
    }
    
    public static double clamp(double value, double max, double min)
    {
        return Math.max(min, Math.min(max, value));
    }
    
    public static double min(double... values) // must have parameter
    {
        if (values.length == 0)
        {
            throw new IllegalArgumentException();
        }
        double lowest = values[0];
        for (int i = 0; i < values.length; i++)
        {
            if (values[i] < lowest)
            {
                lowest = i;
            }
        }
        
        return lowest;
    }
    
    public static Point2D.Double rotatePointAroundPoint(Point2D.Double point, Point2D.Double rotationPoint, double angle)
    {
        double newX = rotationPoint.x + (point.x - rotationPoint.x) * Math.cos(Math.toRadians(360 - angle)) - (point.y - rotationPoint.y) * Math.sin(Math.toRadians(360 - angle));
        double newY = rotationPoint.y + (point.x - rotationPoint.x) * Math.sin(Math.toRadians(360 - angle)) + (point.y - rotationPoint.y) * Math.cos(Math.toRadians(360 - angle));
        
        return new Point2D.Double(newX, newY);
    }
    
    public static double getDistanceToEdgeOfEllipseAtAngle(double horizontalLength, double verticalLength, double angle)
    {
        double top = horizontalLength * verticalLength;
        double bottomX = Math.pow(horizontalLength, 2) * Math.pow(Math.sin(Math.toRadians(angle)), 2);
        double bottomY = Math.pow(verticalLength, 2) * Math.pow(Math.cos(Math.toRadians(angle)), 2);
        
        return (top)/(Math.sqrt(bottomX + bottomY));
    }
    
    public static double getAngleOfEllipseAtAngle(double angleFromCenter, double horizontalAxis, double verticalAxis)
    {
        if (horizontalAxis == verticalAxis)
        {
            return angleFromCenter;
        }
        //System.out.println(angleFromCenter);
        double axisLengths = Math.pow(horizontalAxis / 2, 2) / Math.pow(verticalAxis / 2, 2);
        System.err.println(angleFromCenter + " " + axisLengths);
        if (angleFromCenter <= 90 && angleFromCenter >= 0)
        {
            System.out.println(Math.toDegrees(Math.atan(axisLengths * Math.tan(Math.toRadians(angleFromCenter)))));
            return Math.toDegrees(Math.atan(axisLengths * Math.tan(Math.toRadians(angleFromCenter))));
        }
        else if (angleFromCenter <= 180 && angleFromCenter > 90)
        {
            return 180 + Math.toDegrees(Math.atan(axisLengths * Math.tan(Math.toRadians(angleFromCenter))));
        }
        else if (angleFromCenter <= 270 && angleFromCenter > 180)
        {
            return 270 - (90 - Math.toDegrees(Math.atan(axisLengths * Math.tan(Math.toRadians(angleFromCenter)))));
        }
        else if (angleFromCenter <= 360 && angleFromCenter > 270)
        {
            return 360 + Math.toDegrees(Math.atan(axisLengths * Math.tan(Math.toRadians(angleFromCenter))));
        }
        System.err.println("ERROR");
        return Double.NaN;
    }
}
