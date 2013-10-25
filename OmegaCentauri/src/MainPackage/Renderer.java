package MainPackage;

import java.awt.*;
import java.awt.geom.*;

// @author Michael Kieburtz

public class Renderer {
    
    private double radius;
    private double angle;
   // private Line2D.Double directionLine = new Line2D.Double();
    
    public Renderer() {
        radius = 0;
        angle = 0;
    }

    public void drawScreen(Graphics g, Player player, double xRot, double yRot, Ellipse2D.Double playerCircle) {
        radius = playerCircle.getWidth() / 2;
        angle = player.getAngle();
        Graphics2D g2d = (Graphics2D) g; // turns it into 2d graphics
        
        
        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform) (origXform.clone());
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setPaint(new TexturePaint(player.getImage(), new Rectangle2D.Float(0, 0, player.getImage().getWidth(), player.getImage().getHeight())));
        newXform.setToIdentity();
        
        newXform.rotate(Math.toRadians(player.getAngle()), xRot, yRot);
        
        g2d.setTransform(newXform);
        
        g2d.drawImage(player.getImage(), (int)player.getLocation().x, (int)player.getLocation().y, null);
    }
    
//    public Line2D.Double getLine()
//    {
//        return directionLine;
//    }        

    
}