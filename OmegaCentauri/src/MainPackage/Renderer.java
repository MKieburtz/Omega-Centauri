package MainPackage;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */

public class Renderer {

    private ArrayList<String> fontPaths = new ArrayList<String>();
    private ArrayList<Float> fontSizes = new ArrayList<Float>();
    private ArrayList<Font> fonts = new ArrayList<Font>();
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private Font mainFont;
    private Font dataFont;
    private MediaLoader loader;
    private final int PAUSEMENU = 0;
    private final int PAUSETOMENU = 1;
    private final int GAMEOVER = 2;
    private final int RETURNTOBATTLEFIELD = 3;
    
    private HeadsUpDisplayPlayer headsUpDisplayPlayer = new HeadsUpDisplayPlayer();
    
    // Cached values:
    private ArrayList<Shot> shots = new ArrayList<Shot>();
    
   GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    
    BufferedImage drawingImage = config.createCompatibleImage(1, 1);
    boolean isMac;
    
    public Renderer() {

        loader = new MediaLoader();
        fontSizes.add(36f);
        fontSizes.add(10f);
        fontPaths.add("src/resources/OCR A Std.ttf");
        fontPaths.add("src/resources/OCR A Std.ttf");
        
        imagePaths.add("src/resources/PauseMenu.png");
        imagePaths.add("src/resources/PauseButton_ToMenu.png");
        imagePaths.add("src/resources/GameOver.png");
        imagePaths.add("src/resources/ReturnToTheBattlefield.png");
        images = loader.loadImages(imagePaths);

        fonts = loader.loadFonts(fontPaths, fontSizes);
        mainFont = fonts.get(0);
        dataFont = fonts.get(1);
        
        isMac = System.getProperty("os.name").contains("OS X");
        
        images = Calculator.toCompatibleImages(images);
    }

    public void drawScreen(Graphics g, ArrayList<Ship> ships, double xRot, double yRot, int fps,
            ArrayList<StarChunk> stars, Camera camera, String version, int ups, boolean paused) {

        if (drawingImage.getWidth() != camera.getSize().x || drawingImage.getHeight() != camera.getSize().y)
        {
            if (isMac)
                drawingImage = config.createCompatibleImage(camera.getSize().x, camera.getSize().y, Transparency.TRANSLUCENT);
            else
                drawingImage = config.createCompatibleImage(camera.getSize().x, camera.getSize().y);

            System.err.println(drawingImage.getColorModel().equals(config.getColorModel()));
        }
        
        Graphics2D g2d = drawingImage.createGraphics(); // turns it into 2d graphics
        //System.out.println(images.get(0).getColorModel().equals(config.getColorModel()));
        //long start = System.currentTimeMillis();
        
        shots.clear();

        // draw backround rectangle
        g2d.setColor(Color.BLACK);

        g2d.fillRect(0, 0, camera.getSize().x, camera.getSize().y);
        
        //System.out.println(ups);
        
        // enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // draw stars. Costs 3-5 ms
        for (StarChunk starChunk : stars) {
            if (camera.insideView(starChunk.getBoundingRect())) {
                starChunk.draw(g2d, camera.getLocation());
            }
        }
        // draw HUD
        headsUpDisplayPlayer.draw(g2d, camera);
        
        for (Ship ship : ships)
        {
            shots.addAll(ship.getShots());
            
            if (ship.getHullHealth() > 0) {
                ship.draw(g2d, camera);
            }
            
            if (ship instanceof Player) {
                g2d.setColor(Color.CYAN);
            } else if (ship instanceof EnemyShip) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.YELLOW);
            }

            Ellipse2D.Double minimapShip = new Ellipse2D.Double(camera.getSize().x - 201 + 100 + ship.getLocation().x / 100,
                    camera.getSize().y - 225 + 100 + ship.getLocation().y / 100, 1, 1);
            g2d.draw(minimapShip);
            
            if (ship instanceof Player)
            {
                if (ship.getHullHealth() <= 0)
                {
                    g2d.drawImage(images.get(GAMEOVER), null, 250,125);
                }
                else if ((ship.getLocation().x > 10000 || ship.getLocation().x < -10000) || (ship.getLocation().y > 10000 || ship.getLocation().y < -10000))
                {
                            g2d.drawImage(images.get(RETURNTOBATTLEFIELD),null, 200, 200);
                }
            }
        }
        // draw shots TODO: check if on screen.
        for (Shot shot : shots) {
            if (shot.imagesLoaded()) {
                shot.draw(g2d, camera.getLocation());
            }
        }
        
        // draw fps info and other stats
        g2d.setFont(dataFont);
        g2d.setColor(Color.WHITE);
        
        //version
        g2d.drawString("Version: " + version, camera.getSize().x - 150, 10);
        //fps
        g2d.drawString("FPS: " + String.valueOf(fps), camera.getSize().x - 130, 20);
        //ups
        g2d.drawString("UPS: " + String.valueOf(ups), camera.getSize().x - 130, 30);
        //shots on screen
        g2d.drawString("Shots: " + shots.size(), camera.getSize().x - 130, 40);
        //TODO: CHECK FOR INSIDE CAMERA
        
        
        //draw pause menu
        if (paused)
        {
            g2d.drawImage(images.get(PAUSEMENU), null, 10, 100);
            g2d.drawImage(images.get(PAUSETOMENU),null, 20, 110);
        }
        
        
        // this is the most expensive call
        g.drawImage(drawingImage, 0, 0, null);
        
        g2d.dispose();
        g.dispose();
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }
    
    public void drawLoadingScreen(Graphics g, int percentDone, int width, int height) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(Color.BLUE);
        g2d.drawRect((width / 2) - 200, (height / 2) - 50, 399, 10);
        g2d.setColor(Color.CYAN);
        g2d.fillRect((width / 2) - 200, (height / 2) - 50, percentDone * 4, 10);

        g2d.setFont(mainFont);
        g2d.setColor(new Color(0x00CECE)); // hex codes rock this is pretty much cyan
        //g2d.drawString("Loading", width / 2 - 175, height / 2 - 75);
        
        
        for (int i = 0; i < percentDone; i += 20)
            g2d.drawString(".", width/2 - 180 + i * 4, height / 2 - 50);
        
        g.drawImage(bufferedImage, 0, 0, null);

        g2d.dispose();
        g.dispose();
    }
}
