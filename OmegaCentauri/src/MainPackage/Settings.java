package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.sound.sampled.Clip;
import java.io.*;

/**
 *
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Settings
{
    private Dimension windowResolution = new Dimension();

    private boolean active = false;
    private boolean windowed;
    private boolean graphicsLow;

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> soundPaths = new ArrayList<>();
    private ArrayList<FontInfo> fontData = new ArrayList<>();
    
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private ArrayList<Clip> sounds = new ArrayList<Clip>();
    private ArrayList<Font> fonts = new ArrayList<Font>();

    private final int RADIOBUTTONENABLED = 0;
    private final int RADIOBUTTONDISABLED = 1;
    private final int BUTTONHOVER = 2;
    private final int BUTTONHOVERLONG = 3;

    private final int TITLEFONT = 0;
    private final int SUBTITLEFONT = 1;
    private final int BUTTONFONT = 2;
    private final int TEXTFONT = 3;

    private final int CLICKSOUND = 0;

    private boolean controlHover = false;
    private boolean saveAndBackHover = false;
    private boolean resetHover = false;
    
    private final int SAVEEXITHEIGHT = 24;
    private final int SAVEEXITWIDTH = 215;
    
    private final int RESETHEIGHT = 24;
    private final int RESETWIDTH = 120;
    
    private final int CONTROLSHEIGHT = 24;
    private final int CONTROLSWIDTH = 195;
    
    private Rectangle controlsRectangle;
    private Point controlsDrawPoint;
    
    private Rectangle saveAndBackRectangle;
    private Point saveAndBackDrawPoint;
    
    private Rectangle resetRectangle;
    private Point resetDrawPoint;
    
    private Rectangle lowGraphicsRectangle;
    private Rectangle highGraphicsRectangle;
    private Rectangle windowedResolutionRectangle;
    private Rectangle fullscreenResolutionRectangle;

    private SettingsData settingsData;    
    private GameActionListener settingsChangedListener;
    
    private Resources resources;
    private GameData gameData = new GameData();

    public Settings(Dimension windowSize, GameActionListener actionListener)
    {
        resources = gameData.getResources();
        this.windowResolution = windowSize;
        
        imagePaths.add("resources/RadioButtonEnabled.png");
        imagePaths.add("resources/RadioButtonDisabled.png");
        imagePaths.add("resources/ButtonHover.png");
        imagePaths.add("resources/ButtonHoverLong.png");
        
        images = resources.getImagesForObject(imagePaths);
        
        soundPaths.add("resources/Mouseclick.wav");
        
        sounds = resources.getSoundsForObject(soundPaths);
        
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 50f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 32f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 30f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 24f));
        
        fonts = resources.getFontsForObject(fontData);

        setRects();
      
        settingsData = new SettingsData();
        
        File f = new File("Data/settings.ser");
        if (f.exists())
        {
            load();
        }
        
        windowed = settingsData.getWindowed();
        graphicsLow = settingsData.getGraphicsQualityLow();
        
        settingsChangedListener = actionListener;
    }

    public void draw(Graphics g)
    {
        BufferedImage drawingImage = new BufferedImage(windowResolution.width, windowResolution.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = drawingImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, windowResolution.width, windowResolution.height);

        g2d.setColor(new Color(119, 119, 119));
        g2d.setFont(fonts.get(TITLEFONT));
        g2d.drawString("OPTIONS", windowResolution.width / 2 - 125, 75);

        g2d.setFont(fonts.get(SUBTITLEFONT));
        g2d.drawString("GRAPHICS", 100, windowResolution.height / 2 - 100);

        g2d.setFont(fonts.get(TEXTFONT));
        g2d.drawString("LOW", 100,  windowResolution.height / 2 - 50);
        g2d.drawString("HIGH", 100, windowResolution.height / 2 - 20);
        
        if (settingsData.getGraphicsQualityLow()) 
        {
            g2d.drawImage(images.get(RADIOBUTTONENABLED), 190, windowResolution.height / 2 - 70, null);
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), 190, windowResolution.height / 2 - 40, null);
        } 
        else 
        {
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), 190, windowResolution.height / 2 - 70, null);
            g2d.drawImage(images.get(RADIOBUTTONENABLED), 190, windowResolution.height / 2 - 40, null);
        }
        
        g2d.setFont(fonts.get(SUBTITLEFONT));
        g2d.drawString("RESOLUTION", windowResolution.width / 2 - 110, windowResolution.height / 2 - 100);

        g2d.setFont(fonts.get(TEXTFONT));
        g2d.drawString("WINDOWED", windowResolution.width / 2 - 110,   windowResolution.height / 2 - 50);
        g2d.drawString("FULLSCREEN", windowResolution.width / 2 - 110, windowResolution.height / 2 - 20);

        
        if (settingsData.getWindowed()) 
        {
            g2d.drawImage(images.get(RADIOBUTTONENABLED), windowResolution.width / 2 + 90, windowResolution.height / 2 - 70, null);
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), windowResolution.width / 2 + 90, windowResolution.height / 2 - 40, null);
        } 
        else 
        {
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), windowResolution.width / 2 + 90, windowResolution.height / 2 - 70, null);
            g2d.drawImage(images.get(RADIOBUTTONENABLED), windowResolution.width / 2 + 90, windowResolution.height / 2 - 40, null);
        }
        
        g2d.setFont(fonts.get(BUTTONFONT));

        g2d.drawString("SAVE + EXIT", saveAndBackDrawPoint.x, saveAndBackDrawPoint.y);
        g2d.drawString("CONTROLS", controlsDrawPoint.x, controlsDrawPoint.y);
        g2d.drawString("RESET", resetDrawPoint.x, resetDrawPoint.y);
        
//        g2d.setColor(Color.red);
//        g2d.draw(saveAndBackRectangle);
//        g2d.draw(resetRectangle);
//        g2d.draw(controlsRectangle);
        
        if (controlHover) 
        {
            g2d.drawImage(images.get(BUTTONHOVERLONG), controlsDrawPoint.x - (images.get(BUTTONHOVERLONG).getWidth() - CONTROLSWIDTH) / 2, controlsDrawPoint.y, null);
        } 

        if (saveAndBackHover)
        {
            g2d.drawImage(images.get(BUTTONHOVERLONG), saveAndBackDrawPoint.x- (images.get(BUTTONHOVERLONG).getWidth() - SAVEEXITWIDTH) / 2, saveAndBackDrawPoint.y, null);
        }
        
        if (resetHover)
        {
            g2d.drawImage(images.get(BUTTONHOVER), resetDrawPoint.x- (images.get(BUTTONHOVER).getWidth() - RESETWIDTH) / 2, resetDrawPoint.y, null);
        } 
        g.drawImage(drawingImage, 0, 0, null);
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setWindowSize(Dimension windowSize)
    {
        this.windowResolution = windowSize;
        setRects();
    }

    private void setRects() 
    {
        int distanceFromBottom = 55;
        lowGraphicsRectangle = new Rectangle
        (
                100,
                windowResolution.height / 2 - 70,
                120,
                20
        );

        highGraphicsRectangle = new Rectangle
        (
                100,
                windowResolution.height / 2 - 40,
                120,
                20
        );

        windowedResolutionRectangle = new Rectangle
        (
                windowResolution.width / 2 - 100,
                windowResolution.height / 2 - 70,
                210,
                20
        );

        fullscreenResolutionRectangle = new Rectangle
        (
                windowResolution.width / 2 - 100,
                windowResolution.height / 2 - 40,
                210,
                20
        );
        
        saveAndBackDrawPoint = new Point(100, windowResolution.height - distanceFromBottom);
        saveAndBackRectangle = new Rectangle
        (
              100,
              windowResolution.height - distanceFromBottom - SAVEEXITHEIGHT,
              SAVEEXITWIDTH,
              SAVEEXITHEIGHT
        );
        resetDrawPoint = new Point(windowResolution.width - 100 - RESETWIDTH, windowResolution.height - distanceFromBottom);
        resetRectangle = new Rectangle
        (
                windowResolution.width - 100 - RESETWIDTH,
                windowResolution.height - distanceFromBottom - RESETHEIGHT,
                RESETWIDTH,
                RESETHEIGHT
        );
        
        controlsDrawPoint = new Point((int)(saveAndBackRectangle.x + saveAndBackRectangle.getWidth() + resetRectangle.x) / 2 - CONTROLSWIDTH / 2,
        windowResolution.height - distanceFromBottom);
        controlsRectangle = new Rectangle
        (
                (int)(saveAndBackRectangle.x + saveAndBackRectangle.getWidth() + resetRectangle.x) / 2 - CONTROLSWIDTH / 2,
                windowResolution.height - distanceFromBottom - CONTROLSHEIGHT,
                CONTROLSWIDTH,
                CONTROLSHEIGHT
        );

    }

    public void checkMouseMoved(Point location) 
    {
        if (active) 
        {
            if (controlsRectangle.contains(location)) 
            {
                controlHover = true;
            } 
            else if (saveAndBackRectangle.contains(location))
            {
                saveAndBackHover = true;
            } 
            else if (resetRectangle.contains(location)) 
            {
                resetHover = true;
            }
            else 
            {
                controlHover = false;
                saveAndBackHover = false;
                resetHover = false;
            }
        }
    }

    public void checkMousePressed(Point location) 
    {
        if (lowGraphicsRectangle.contains(location)) 
        {
            settingsData.setGraphicsQualityLow(true);
        } 
        else if (highGraphicsRectangle.contains(location)) 
        {
            settingsData.setGraphicsQualityLow(false);
        } 
        else if (windowedResolutionRectangle.contains(location)) 
        {
            settingsData.setWindowed(true);
        } 
        else if (fullscreenResolutionRectangle.contains(location)) 
        {
            settingsData.setWindowed(false);
        } 
        else if (resetRectangle.contains(location)) 
        {
//            sounds.get(CLICKSOUND).setFramePosition(0);
//            sounds.get(CLICKSOUND).start();
            settingsData.resetDefaults();
        } 
        else if (saveAndBackRectangle.contains(location)) 
        {
//            sounds.get(CLICKSOUND).setFramePosition(0);
//            sounds.get(CLICKSOUND).start();
            
            save();
            active = false;
        }
    }

    public void checkMouseExited() 
    {
        controlHover = false;
        saveAndBackHover = false;
        resetHover = false;
    }

    private void save()
    {
        try 
        {
            OutputStream output = new FileOutputStream("Data/settings.ser");
            OutputStream buffer = new BufferedOutputStream(output);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(buffer);
            
            objectOutputStream.writeObject(settingsData);
            
            objectOutputStream.close();
            
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        if (!windowed && settingsData.getWindowed()) // if it wasn't windowed origionaly.
        {
            settingsChangedListener.exitedFullScreen();
        } 
        else if (windowed && !settingsData.getWindowed())
        {
            settingsChangedListener.enteredFullScreen();
        }
        
        if (!graphicsLow && settingsData.getGraphicsQualityLow()) // " "
        {
            settingsChangedListener.settingsChangedToLow();
        } 
        else if (graphicsLow && !settingsData.getGraphicsQualityLow())
        {
            settingsChangedListener.settingsChangedToHigh();
        }
    }

    private void load()
    {
        SettingsData settings;
        try 
        {    
            InputStream input = new FileInputStream("Data/settings.ser");
            InputStream buffer = new BufferedInputStream(input);
            ObjectInputStream objectInputStream = new ObjectInputStream(buffer);
            
            
            settings = (SettingsData)objectInputStream.readObject();
            
            settingsData = settings;
            
            objectInputStream.close();
            
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        
    }
    
    public SettingsData getData()
    {
        return settingsData;
    }
}
