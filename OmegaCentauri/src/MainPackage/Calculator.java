package MainPackage;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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


    public static double CalcAngleMoveX(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double CalcAngleMoveY(double angle) {
        return Math.sin(Math.toRadians(angle));
    }
    /**
     * @returns distance between two points
     */
    public static double getDistance(Point2D.Double pt1, Point2D.Double pt2) {
        return Math.sqrt(Math.pow(Math.abs(pt2.x - pt1.x), 2) + Math.pow(Math.abs(pt2.y - pt1.y), 2));
    }

    /**
     * @returns angle between two points IN DEGREES
     */ 
    static double angle;
    public static double getAngleBetweenTwoPoints(Point2D.Double pt1, Point2D.Double pt2) {
        angle = (double) Math.toDegrees(Math.atan2(pt2.x - pt1.x, pt2.y - pt1.y));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
    
    public static boolean collisionCheck(Rectangle2D.Double hitbox1,Rectangle2D.Double hitbox2)
    {
            return hitbox1.intersects(hitbox2);
    }
    
    public static double[] getDistancesBetweenAngles(double angle1, double angle2)
    {
        if (angle1 > angle2) {
            if (angle1 + 180 >= 360) // CASE 1
            {
                return new double[] { Math.abs((360 - angle1) + angle2), Math.abs(angle1 - angle2) };
            } else if (angle1 + 180 < 360) // CASE 2
            {
                return new double[] { Math.abs((360 - angle2) + angle1), Math.abs(angle2 - angle1) };
            }
        } else if (angle2 > angle1) {
            if (angle2 + 180 >= 360) // CASE 3
            {
                return new double[] { Math.abs(angle2 - angle1), Math.abs(360 - (angle2 - angle1)) };
            } else if (angle2 + 180 < 360) // CASE 4
            {
                return new double[] { Math.abs(angle2 - angle1), Math.abs(360 - (angle2 - angle1)) };
            }
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
    
    public static Point2D.Double getScreenLocationMiddleForPlayer(Point2D.Double cameraLocation, Point2D.Double location,
            double imageWidth, double imageHeight) {
        double x = getScreenLocation(cameraLocation, location).x + cameraLocation.x + imageWidth / 2;
        double y = getScreenLocation(cameraLocation, location).y + cameraLocation.y + imageHeight / 2;

        return new Point2D.Double(x, y);
    }
    
    public static ArrayList<BufferedImage> toCompatibleImages(ArrayList<BufferedImage> images)
    {
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        for (int i = 0; i < images.size(); i++)
        {
            BufferedImage tempImage = config.createCompatibleImage(images.get(i).getWidth(), images.get(i).getHeight());
            Graphics2D g2d = tempImage.createGraphics();
            
            g2d.drawImage(images.get(i), 0, 0, null);
            images.set(i, tempImage);

            g2d.dispose();
        }
        
        return images;
    }
}
