package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.sound.sampled.Clip;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class MainMenu 
{

    private GameActionListener startListener;

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> soundPaths = new ArrayList<>();
    private ArrayList<FontInfo> fontData = new ArrayList<>();

    private ArrayList<BufferedImage> images = new ArrayList<>();
    private ArrayList<Clip> sounds = new ArrayList<>();
    private ArrayList<Font> fonts = new ArrayList<>();

    private ArrayList<TwinklingStarChunk> stars = new ArrayList<>();

    private Settings settings;

    private final int HOVER = 0;
    
    private final int CLICKSOUND = 0;
    
    private final int FONT = 0;
    
    private final int TEXTWIDTHSETTINGS = 174;
    private final int TEXTHEIGHTSETTINGS = 24;
    
    private final int TEXTWIDTHSTART = 120;
    private final int TEXTHEIGHTSTART = 24;
    
    private final int TEXTWIDTHCLOSE = 120;
    private final int TEXTHEIGHTCLOSE = 24;
    

    private boolean startHover = false;
    private boolean closeHover = false;
    private boolean settingsHover = false;

    private Rectangle startRectangle;
    private Rectangle closeRectangle;
    private Rectangle settingsRectangle;
    
    private Point startDrawPoint;
    private Point closeDrawPoint;
    private Point settingsDrawPoint;

    private boolean active;

    private Point size;
    private Dimension screenSize;

    private Rectangle screenRect;
    
    private BufferedImage drawingImage;
    

    public MainMenu(OmegaCentauri game, Resources resources) 
    {
        active = true;
        startListener = game;
        
        imagePaths.add("resources/ButtonHover.png");
        images = resources.getImagesForObject(imagePaths);
        
        soundPaths.add("resources/Mouseclick.wav");        
        sounds = resources.getSoundsForObject(soundPaths);

        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 30f));
        
        fonts = resources.getFontsForObject(fontData);
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        size = new Point(game.getWidth(), game.getHeight());

        settings = new Settings(new Dimension(size.x, size.y), game, resources);
        
        screenRect = new Rectangle(0, 0, size.x, size.y);

        // load enough stars for the entire screen
        for (int x = 0; x < screenSize.width; x += 100) {
            
            for (int y = 0; y < screenSize.height; y += 100)
            {
                stars.add(new TwinklingStarChunk(x, y, 100, 3));
            }
        }
       
        drawingImage = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);

        setRects();
    }

    public void draw(Graphics g) 
    {
        if (!settings.isActive()) 
        {

            if (drawingImage.getWidth() != size.x || drawingImage.getHeight() != size.y)
            {
                drawingImage = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
            }
            
            Graphics2D g2d = drawingImage.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, size.x, size.y);

            for (TwinklingStarChunk s : stars) 
            {
                if (s.getBoundingRect().intersects(screenRect)) 
                {
                    s.draw(g2d);
                }
            }

            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, size.y - 100, size.x, size.y - 100);

            g2d.setColor(new Color(119, 119, 119));
            
            g2d.setFont(fonts.get(FONT));
            
            g2d.drawString("START", startDrawPoint.x, startDrawPoint.y);
            g2d.drawString("CLOSE", closeDrawPoint.x, closeDrawPoint.y);
            g2d.drawString("SETTINGS", settingsDrawPoint.x, settingsDrawPoint.y);
            
//            g2d.setColor(Color.red);
//            g2d.draw(settingsRectangle);
//            g2d.draw(startRectangle);
//            g2d.draw(closeRectangle)
            if (startHover) 
            {
                g2d.drawImage(images.get(HOVER), startDrawPoint.x - (images.get(HOVER).getWidth() - TEXTWIDTHSTART) / 2, startDrawPoint.y, null);
            } 
            
            if (closeHover) 
            {
                g2d.drawImage(images.get(HOVER), closeDrawPoint.x - (images.get(HOVER).getWidth() - TEXTWIDTHCLOSE) / 2, closeDrawPoint.y, null);
            } 

            if (settingsHover)
            {
                g2d.drawImage(images.get(HOVER), settingsDrawPoint.x - (images.get(HOVER).getWidth() - TEXTWIDTHSETTINGS) / 2, settingsDrawPoint.y, null);
            } 
          
            g2d.setColor(Color.CYAN);
            g2d.drawLine(0, size.y - 100, size.x, size.y - 100);
            //g2d.drawLine(settingsRectangle.x, settingsRectangle.y + settingsRectangle.height, size.x, settingsRectangle.y + settingsRectangle.height);

//        g2d.setColor(Color.RED);
//        g2d.draw(startRectangle);
//        g2d.draw(closeRectangle);
//        g2d.draw(settingsRectangle);
        
            g.drawImage(drawingImage, 0, 0, null);
        } 
        else 
        {
            settings.draw(g);
        }
    }
    
    public void checkMousePressed(Point location)
    {
        if (!settings.isActive() && active) 
        {
            if (startRectangle.contains(location))
            {
                active = false;
                startListener.gameStart();
//                sounds.get(CLICKSOUND).setFramePosition(0);
//                sounds.get(CLICKSOUND).start();
            }
            if (closeRectangle.contains(location)) 
            {
//                sounds.get(CLICKSOUND).setFramePosition(0);
//                sounds.get(CLICKSOUND).start();                
                System.exit(0);
            }
            if (settingsRectangle.contains(location)) 
            {
//                sounds.get(CLICKSOUND).setFramePosition(0);
//                sounds.get(CLICKSOUND).start();
                settings.setActive(true);
            }
        }
        else if (settings.isActive())
        {
            settings.checkMousePressed(location);
        }
    }

    public void checkMouseExited()
    {
        if (!settings.isActive() && active) 
        {
            startHover = false;
            closeHover = false;
            settingsHover = false;
        } 
        else if (settings.isActive())
        {
            settings.checkMouseExited();
        }
    }
    
    public void checkMouseMoved(Point location)
    {
        if (!settings.isActive() && active) 
        {
            if (startRectangle.contains(location))
            {
                startHover = true;
            } 
            else if (closeRectangle.contains(location))
            {
                closeHover = true;
            } 
            else if (settingsRectangle.contains(location))
            {
                settingsHover = true;
            } 
            else 
            {
                startHover = false;
                closeHover = false;
                settingsHover = false;
            }
        } 
        else if (settings.isActive())
        {
            settings.checkMouseMoved(location);
        }
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Point getSize()
    {
        return size;
    }

    public void setSize(int width, int height)
    {
        size.setLocation(width, height);
        settings.setWindowSize(new Dimension(width, height));
        setRects();
    }

    private void setRects()
    {
        int distanceFromBottom = 55;
        closeDrawPoint = new Point(size.x - 100 - TEXTWIDTHCLOSE, size.y - distanceFromBottom);
        closeRectangle = new Rectangle
        (
                size.x - 100 - TEXTWIDTHCLOSE,
                size.y - distanceFromBottom - TEXTHEIGHTCLOSE,
                TEXTWIDTHCLOSE,
                TEXTHEIGHTCLOSE
        );
        settingsDrawPoint = new Point(100, size.y - distanceFromBottom);
        settingsRectangle = new Rectangle
        (
                100,
                size.y - distanceFromBottom - TEXTHEIGHTSETTINGS,
                TEXTWIDTHSETTINGS,
                TEXTHEIGHTSETTINGS
        );
        startDrawPoint = new Point((int)(settingsRectangle.x + settingsRectangle.getWidth() + closeRectangle.x) / 2 - TEXTWIDTHSTART / 2,
                size.y - distanceFromBottom);
        startRectangle = new Rectangle
        (
                (int)(settingsRectangle.x + settingsRectangle.getWidth() + closeRectangle.x) / 2 - TEXTWIDTHSTART / 2 ,
                size.y - distanceFromBottom - TEXTHEIGHTSTART,
                TEXTWIDTHSTART,
                TEXTHEIGHTSTART
        );

        screenRect.setBounds(0, 0, size.x, size.y);
    }

    public Settings getSettings() 
    {
        return settings;
    }
}
