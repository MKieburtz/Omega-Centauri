package MainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */

public class HeadsUpDisplayPlayer {

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<BufferedImage> images = new ArrayList<>();
    private DecimalFormat format = new DecimalFormat("#.0");
    
    private final int DANGERBAR = 0;
    private final int WARNINGBAR = 1;
    private final int HEALTHYBAR = 2;
    private final int TOPLEFTHUD = 3;
    private final int BADSHIELD = 4;
    private final int WARNINGSHIELD = 5;
    private final int GOODSHIELD = 6;
    private final int SHIPSTATUSGOOD = 7;
    private final int SHIPSTATUSWARNING = 8;
    private final int SHIPSTATUSBAD = 9;
    
    
    private final int HEALTHSTARTINGY = 150;
    private final int HEALTHSTARTINGX = 40;
    private final int SPACEBETWEENY = 20;
    private final int SPACEBETWEENX = 80;
    
    private Font font;
    
    public HeadsUpDisplayPlayer(Resources resources)
    {
        font = resources.getFontForObject(new FontInfo("resources/Orbitron-Regular.ttf", 12f));
        
        imagePaths.add("resources/DangerBar.png");
        imagePaths.add("resources/WarningBar.png");
        imagePaths.add("resources/HealthyBar.png");
        imagePaths.add("resources/HUDBackground.png");
        imagePaths.add("resources/ShieldStatusBad.png");
        imagePaths.add("resources/ShieldStatusWarning.png");
        imagePaths.add("resources/ShieldStatusGood.png");
        imagePaths.add("resources/ShipStatusGood.png");
        imagePaths.add("resources/ShipStatusWarning.png");
        imagePaths.add("resources/ShipStatusBad.png");

        images = resources.getImagesForObject(imagePaths);
    }
    
    public void draw(Graphics2D g2d, Camera camera, double shieldHealth, double hullHealth)
    {
        //g2d.drawLine(HEALTHSTARTINGX, HEALTHSTARTINGY, HEALTHSTARTINGX, HEALTHSTARTINGY - 200);
        g2d.drawImage(images.get(TOPLEFTHUD), -10, -35, null); // wierd coords. I know
        g2d.drawImage(images.get(GOODSHIELD), 40, 25, null);
        g2d.drawImage(images.get(SHIPSTATUSGOOD), 53, 30, null);
        for (int col = 0; col < 2; col++)
        {
            for (int i = 0; i < 5; i++)
            {
                g2d.drawImage(images.get(HEALTHYBAR), HEALTHSTARTINGX + (SPACEBETWEENX * col), HEALTHSTARTINGY + (SPACEBETWEENY * i), null);
            }

            for (int i = 0; i < 3; i++)
            {
                g2d.drawImage(images.get(WARNINGBAR), HEALTHSTARTINGX + (SPACEBETWEENX * col), HEALTHSTARTINGY + 100 + (SPACEBETWEENY * i), null);
            }

            for (int i = 0; i < 2; i++)
            {
                g2d.drawImage(images.get(DANGERBAR), HEALTHSTARTINGX + (SPACEBETWEENX * col), HEALTHSTARTINGY + 100 + 60 + (SPACEBETWEENY * i), null);
            }
        }
        g2d.setFont(font);
        if (shieldHealth >= 50)
        {
            g2d.setColor(Color.green);
        }
        else if (shieldHealth >= 30)
        {
            g2d.setColor(Color.ORANGE);
        }
        else if (shieldHealth < 30)
        {
            g2d.setColor(Color.red);
        }
        g2d.drawString("%" + format.format(shieldHealth), 100, 360); // draw shield percent
        
        if (hullHealth >= 50)
        {
            g2d.setColor(Color.green);
        }
        else if (hullHealth >= 30)
        {
            g2d.setColor(Color.ORANGE);
        }
        else if (hullHealth < 30)
        {
            g2d.setColor(Color.red);
        }
        g2d.drawString("%" + format.format(hullHealth), HEALTHSTARTINGX - 20, 360); // draw health percent
        
    }
}
