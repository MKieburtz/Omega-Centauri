package MainPackage;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

// @author Michael Kieburtz

public class Renderer {
    Font fpsFont;
    public Renderer() {
        fpsFont = new Font("OCR A Std", Font.BOLD, 16);
    }

    public void drawScreen(Graphics g, Player player, double xRot, double yRot, double fps, ArrayList<Dust> dust) {
        Graphics2D g2d = (Graphics2D) g; // turns it into 2d graphics
        
        g2d.drawImage(player.getImage(4), null, 0, 0);
        for (int i = 0; i < dust.size(); i++)
        {
            dust.get(i).draw(g2d);
        }
        
        g2d.setFont(fpsFont);
        g2d.setColor(Color.CYAN);
        
        g2d.drawString("FPS: " + String.valueOf(fps), 10, 20);
        
        
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
    
}