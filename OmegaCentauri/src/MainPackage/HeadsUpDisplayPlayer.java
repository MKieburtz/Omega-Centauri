package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public class HeadsUpDisplayPlayer 
{

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
    private final int GAMEOVER = 10;
    private final int RETURNTOBATTLEFIELD = 11;

    private final int HEALTHSTARTINGY = 230;
    private final int HEALTHSTARTINGX = 40;
    private final int SPACEBETWEENY = 20;
    private final int SPACEBETWEENX = 80;

    private Font healthFont;
    private Font dataFont;

    public HeadsUpDisplayPlayer(Resources resources) 
    {
        healthFont = resources.getFontForObject(new FontInfo("resources/Orbitron-Regular.ttf", 12f));
        dataFont = resources.getFontForObject(new FontInfo("resources/OCR A Std.ttf", 10f));
        
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
        imagePaths.add("resources/GameOver.png");
        imagePaths.add("resources/ReturnToTheBattlefield.png");

        images = resources.getImagesForObject(imagePaths);
    }

    public void draw(Graphics2D g2d, Camera camera, ArrayList<Ship> ships, Dimension mapSize, int fps, int ups, String version,
            int numShots) 
    {
        //g2d.drawLine(HEALTHSTARTINGX, HEALTHSTARTINGY, HEALTHSTARTINGX, HEALTHSTARTINGY - 200);
        //g2d.drawImage(images.get(TOPLEFTHUD), -10, -35, null); // wierd coords. I know
        Player playerShip = null;
        
        for (Ship ship : ships)
        {
            if (ship instanceof Player)
            {
                playerShip = (Player)ship;
            }
        }
        
        double shieldPercent = (playerShip.getShieldHealth() / playerShip.getMaxShield()) * 100;
        double hullPercent = (playerShip.getHullHealth() / playerShip.getMaxHull()) * 100;
        
        int amountShield = (int) (10 * Math.ceil(shieldPercent / 10)) / 10; // rounding up to 10's
        int amountHull = (int) (10 * Math.ceil(hullPercent / 10)) / 10;

        int[] shieldAmounts = getAmounts(amountShield);
        int[] hullAmounts = getAmounts(amountHull);

        if (shieldAmounts.length != 3 || hullAmounts.length != 3)
        {
            System.err.println("ERROR: Bad shield or hull values in HUD. Shield: " + shieldAmounts.length + " Hull: " + hullAmounts.length);
        }

        int amountGreenShield = shieldAmounts[0];
        int amountOrangeShield = shieldAmounts[1];
        int amountRedShield = shieldAmounts[2];

        int amountGreenHull = hullAmounts[0];
        int amountOrangeHull = hullAmounts[1];
        int amountRedHull = hullAmounts[2];

        for (Ship ship : ships) 
        {
            ship.draw(g2d, camera);
            if (ship instanceof Player)
            {
                g2d.setColor(Color.CYAN);

                if (ship.getHullHealth() <= 0)
                {
                    g2d.drawImage(images.get(GAMEOVER), null, 250, 125);
                } 
                else if ((ship.getLocation().x > mapSize.width || ship.getLocation().x < 0) ||
                        (ship.getLocation().y > mapSize.height || ship.getLocation().y < 0))
                {
                    g2d.drawImage(images.get(RETURNTOBATTLEFIELD), null, 200, 200);
                }

            } 
            else if (ship instanceof EnemyShip)
            {
                g2d.setColor(Color.RED);
            } 
            else 
            {
                g2d.setColor(Color.YELLOW);
            }
//
//            Ellipse2D.Double minimapShip = new Ellipse2D.Double(camera.getSize().x - 201 + ship.getLocation().x / (mapSize.width / 200),
//                        camera.getSize().y - 225 + ship.getLocation().y / (mapSize.height / 200), 1, 1);
//                g2d.draw(minimapShip);
        }
//        
//        // draw the hull bars
//        for (int i = 0; i < amountGreenHull; i++) 
//        {
//            g2d.drawImage(images.get(HEALTHYBAR), HEALTHSTARTINGX, HEALTHSTARTINGY - (SPACEBETWEENY * i), null);
//        }
//
//        for (int i = 0; i < amountOrangeHull; i++) 
//        {
//            g2d.drawImage(images.get(WARNINGBAR), HEALTHSTARTINGX, HEALTHSTARTINGY + 60 - (SPACEBETWEENY * i), null);
//        }
//
//        for (int i = 0; i < amountRedHull; i++)
//        {
//            g2d.drawImage(images.get(DANGERBAR), HEALTHSTARTINGX, HEALTHSTARTINGY + 100 - (SPACEBETWEENY * i), null);
//        }
//        
//        // draw the shield bars
//        for (int i = 0; i < amountGreenShield; i++) 
//        {
//            g2d.drawImage(images.get(HEALTHYBAR), HEALTHSTARTINGX + SPACEBETWEENX, HEALTHSTARTINGY - (SPACEBETWEENY * i), null);
//        }
//
//        for (int i = 0; i < amountOrangeShield; i++) 
//        {
//            g2d.drawImage(images.get(WARNINGBAR), HEALTHSTARTINGX + SPACEBETWEENX, HEALTHSTARTINGY + 60 - (SPACEBETWEENY * i), null);
//        }
//
//        for (int i = 0; i < amountRedShield; i++)
//        {
//            g2d.drawImage(images.get(DANGERBAR), HEALTHSTARTINGX + SPACEBETWEENX, HEALTHSTARTINGY + 100 - (SPACEBETWEENY * i), null);
//        }
//        
//        g2d.setFont(healthFont);
//        if (shieldPercent >= 50)
//        {
//            g2d.setColor(Color.green);
//            g2d.drawImage(images.get(GOODSHIELD), 40, 25, null);
//        } 
//        else if (shieldPercent > 20)
//        {
//            g2d.setColor(Color.ORANGE);
//            g2d.drawImage(images.get(WARNINGSHIELD), 40, 25, null);
//        } 
//        else if (shieldPercent <= 20)
//        {
//            g2d.setColor(Color.red);
//            g2d.drawImage(images.get(BADSHIELD), 40, 25, null);
//        }
//
//        if (shieldPercent == 0) 
//        {
//            g2d.drawString("%0.0", 105, 360); // draw shield percent
//        } 
//        else 
//        {
//            g2d.drawString("%" + format.format(shieldPercent), 100, 360); // draw shield percent
//        }
//
//        if (hullPercent >= 50) 
//        {
//            g2d.setColor(Color.green);
//            g2d.drawImage(images.get(SHIPSTATUSGOOD), 53, 30, null);
//        } 
//        else if (hullPercent >= 30)
//        {
//            g2d.setColor(Color.ORANGE);
//            g2d.drawImage(images.get(SHIPSTATUSWARNING), 53, 30, null);
//        } 
//        else if (hullPercent < 30) 
//        {
//            g2d.setColor(Color.red);
//            g2d.drawImage(images.get(SHIPSTATUSBAD), 53, 30, null);
//        }
//
//        if (hullPercent == 0)
//        {
//            g2d.drawString("%0.0", HEALTHSTARTINGX - 15, 360); // draw shield percent
//        } 
//        else 
//        {
//            g2d.drawString("%" + format.format(hullPercent), HEALTHSTARTINGX - 20, 360); // draw health percent
//        }
//        
//        g2d.setFont(dataFont);
//        g2d.setColor(Color.WHITE);
//            
//        // draw the stats n stuff.
//        g2d.drawString("Version: " + version, camera.getSize().x - 150, 10);
//        //fps
//        g2d.drawString("FPS: " + fps, camera.getSize().x - 130, 20);
//        //ups
//        g2d.drawString("UPS: " + ups, camera.getSize().x - 130, 30);
//        //shots on screen
//        g2d.drawString("Shots: " + numShots, camera.getSize().x - 130, 40);
    }

    private int[] getAmounts(int amount) 
    {
        int[] values = new int[3];
        values[0] = amount - 5;
        values[1] = values[0] > 0 ? 3 : amount - 2;
        values[2] = values[1] > 0 ? 2 : amount;

        return values;
    }
}
