package MainPackage;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Renderer 
{

    private ArrayList<String> imagePaths = new ArrayList<>();
    
    private ArrayList<Font> fonts = new ArrayList<>();
    private ArrayList<BufferedImage> images = new ArrayList<>();
    private Font dataFont;
    private final int PAUSEMENU = 0;
    private final int PAUSETOMENU = 1;
    
    private HeadsUpDisplayPlayer headsUpDisplayPlayer;

    GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    VolatileImage drawingImage = config.createCompatibleVolatileImage(1, 1);
    boolean isMac;
    
    private Resources resources;

    private GameData gameData = new GameData();
    
    public Renderer() 
    {
        
        resources = gameData.getResources();
        
        fonts.add(resources.getFontForObject(new FontInfo("resources/OCR A Std.ttf", 10f)));
        

        imagePaths.add("resources/PauseMenu.png");
        imagePaths.add("resources/PauseButton_ToMenu.png");
        
        images = resources.getImagesForObject(imagePaths);

        dataFont = fonts.get(0);

        isMac = System.getProperty("os.name").contains("OS X");

        images = Calculator.toCompatibleImages(images);
        
        headsUpDisplayPlayer = new HeadsUpDisplayPlayer();
    }

    public void drawGameScreen(Graphics g, ArrayList<Ship> ships, double xRot, double yRot, int fps,
            ArrayList<StarChunk> stars, Camera camera, String version, int ups, boolean paused, HashSet<Shot> allShots, Dimension mapSize)
    {

        //long start = System.currentTimeMillis();
        
        if (drawingImage.getWidth() != camera.getSize().x || drawingImage.getHeight() != camera.getSize().y)
        {
            if (isMac)
            {
                drawingImage = config.createCompatibleVolatileImage(camera.getSize().x, camera.getSize().y, Transparency.TRANSLUCENT);
            } 
            else 
            {
                drawingImage = config.createCompatibleVolatileImage(camera.getSize().x, camera.getSize().y);
            }
            //System.err.println(drawingImage.getColorModel().equals(config.getColorModel()));
        }

        Graphics2D g2d = drawingImage.createGraphics(); // turns it into 2d graphics
        //System.out.println(images.get(0).getColorModel().equals(config.getColorModel()));

        // draw backround rectangle
        g2d.setColor(Color.BLACK);

        g2d.fillRect(0, 0, camera.getSize().x, camera.getSize().y);

        //System.out.println(ups);
        // enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw stars. Costs 2-7 ms
        for (StarChunk starChunk : stars) 
        {
            if (camera.insideView(starChunk.getBoundingRect()))
            {
                starChunk.draw(g2d, camera.getLocation());
            }
        }
        
        for (Ship ship : ships)
        {
            ship.draw(g2d);
        }
        
        // draw shots TODO: check if on screen.
        for (Shot shot : allShots)
        {
            shot.draw(g2d);  
        }
        
        // draw HUD
        headsUpDisplayPlayer.draw(g2d, ships, mapSize, fps, ups, version, allShots.size());
        
        //draw pause menu
        if (paused) 
        {
            g2d.drawImage(images.get(PAUSEMENU), null, 10, 100);
            g2d.drawImage(images.get(PAUSETOMENU), null, 20, 110);
        }
        
        // this is the most expensive call
        g.drawImage(drawingImage, 0, 0, null);

        g2d.dispose();
        g.dispose();
        //System.out.println(System.currentTimeMillis() - start);
    }

    public void drawLoadingScreen(Graphics g, int percentDone, int width, int height) 
    {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLUE);
        g2d.drawRect((width / 2) - 200, (height / 2) - 50, 399, 10);
        g2d.setColor(Color.CYAN);
        g2d.fillRect((width / 2) - 200, (height / 2) - 50, percentDone * 4, 10);

        g2d.setColor(new Color(0x00CECE)); // hex codes rock. This is pretty much cyan
        
//        for (int i = 0; i < percentDone; i += 20) {
//            g2d.drawString(".", width / 2 - 180 + i * 4, height / 2 - 50);
//        }

        g.drawImage(bufferedImage, 0, 0, null);

        g2d.dispose();
        g.dispose();
    }
}
