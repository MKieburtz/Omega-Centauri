package MainPackage;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.swing.text.AttributeSet;

// @author Michael Kieburtz and Davis Freeman
public class Renderer {

    File fontFile;
    private Font fpsFont;
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public Renderer(int cameraWidth, int cameraHeight) {

        fontFile = new File("resources/BlackHoleBB_ital.ttf");

        try {
            fpsFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(36f);
        } catch (FontFormatException ex) {
            System.err.println("Bad font");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Bad file");
            ex.printStackTrace();
        }

    }

    public void drawScreen(Graphics g, Player player, double xRot, double yRot, double fps,
            ArrayList<DustChunk> dust, Camera camera) {
        Graphics2D g2d = (Graphics2D) g; // turns it into 2d graphics
        
        // draw backround rectangle
        g.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 1000, 1000);
        
        // enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // draw dust/stars
        for (int i = 0; i < dust.size(); i++) {
            if (camera.insideView(dust.get(i).getLocation(), dust.get(i).getSize())) {
                dust.get(i).draw(g2d, camera.getLocation());
            }
        }
        // draw fps info
        g2d.drawImage(player.getImage(5), null, 0, 0);
        g2d.setFont(fpsFont.deriveFont(32f));
        g2d.setColor(Color.CYAN);

        g2d.drawString(String.valueOf(fps), 155, 33);

        g2d.setFont(fpsFont.deriveFont(60f));
        
        g2d.drawString("FPS:", 10, 50);
        
        // draw the minimap
        g2d.setColor(Color.BLACK);
        g2d.fillRect(794, 382, 200, 190);
        
        g2d.setColor(new Color(0, 255, 0, 50));
        g2d.fillRect(794, 382, 200, 190);
        
        g2d.setColor(Color.GREEN);
        g2d.drawRect(794, 382, 200, 190);
        
        // transform the player and draw it
        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform) (origXform.clone());

        g2d.setPaint(new TexturePaint(player.getImage(), new Rectangle2D.Float(0, 0, player.getImage().getWidth(), player.getImage().getHeight())));
        newXform.setToIdentity();

        newXform.rotate(Math.toRadians(player.getAngle()), xRot, yRot);

        g2d.setTransform(newXform);
        
        g2d.drawImage(player.getImage(), (int) (player.getLocation().x - camera.getLocation().x),
                (int) (player.getLocation().y - camera.getLocation().y), null);
        
        
    }
    
    public void drawLauncher(Graphics g, BufferedImage startButtonImage)
    {
        g.drawImage(startButtonImage, 100, 0, null);
    }
    
    public void drawLoadingScreen(Graphics g, int percentDone, int width, int height)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        
        g.setColor(Color.RED);
        g.drawRect((width / 2) - 200, (height / 2) - 50, 200, 50);
        g.setColor(Color.GREEN);
        g.fillRect((width / 2) - 200, (height / 2) - 50, percentDone * 2, 50);
    }
}
