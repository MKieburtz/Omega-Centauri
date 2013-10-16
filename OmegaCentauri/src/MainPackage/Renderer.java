package MainPackage;

import java.awt.*;
import java.awt.geom.*;
// import javax.vecmath.Vector2d;

// @author Michael Kieburtz

public class Renderer {
    
    private double radius;
    private double angle;
    private Line2D.Double directionLine = new Line2D.Double();
    
    public Renderer() {
        radius = 0;
        angle = 0;
    }

    public void drawScreen(Graphics g, Player player, int xRot, int yRot, Ellipse2D.Double playerCircle) {
        radius = playerCircle.getWidth() / 2;
        angle = player.getAngle();
        
        directionLine.x1 = xRot;
        directionLine.y1 = yRot;
        directionLine.x2 = xRot + radius + Math.cos(Math.toRadians(player.getAngle())) - playerCircle.getWidth() / 2;
        directionLine.y2 = yRot + radius + Math.sin(Math.toRadians(player.getAngle())) - playerCircle.getHeight();

        
        Graphics2D g2d = (Graphics2D) g; // turns it into 2d graphics
        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform) (origXform.clone());

        newXform.rotate(Math.toRadians(player.getAngle()), xRot, yRot);
        g2d.setTransform(newXform);
        
        //g2d.draw(directionLine);
        
        g2d.drawImage(player.getImage(), player.getLocation().x, player.getLocation().y, null);
        //g2d.draw(playerCircle);
        System.out.println(directionLine.getX1() + " " + directionLine.getY1()+ "----" + directionLine.getX2() + " " + directionLine.getY2());
        /*
         * a point on the outer edge of a circle given the center of the circle
         * (cx, cy), the radius (r) and the angle where the ship is pointing
         * (a) is
         * x = cx + r + Math.cos(Math.toRadians(a));
         * y is cy + r + Math.sin(Math.toRadians(a));
         * 
         * 
         */
    }
    
    public Line2D.Double getLine()
    {
        return directionLine;
    }        

    
}