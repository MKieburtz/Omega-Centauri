package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.sound.sampled.Clip;

/**
 *
 * @author Davis Freeman
 */
public class Settings {


    private Dimension screenResolution = new Dimension();
    private Dimension windowResolution = new Dimension();
    
    private MediaLoader loader;
    private boolean active = false;
    
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<String> soundPaths = new ArrayList<String>();
    private ArrayList<String> fontPaths = new ArrayList<String>();
    private ArrayList<Float> fontSizes = new ArrayList<Float>();
    
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private ArrayList<Clip> sounds = new ArrayList<Clip>();
    private ArrayList<Font> fonts = new ArrayList<Font>();
    
    private final int RADIOBUTTONENABLED = 0;
    private final int RADIOBUTTONDISABLED = 1;
    
    public Settings(Dimension screenSize, Dimension windowSize) {
        this.screenResolution = screenSize;
        this.windowResolution = windowSize;
        
        loader = new MediaLoader();
        
        imagePaths.add("resources/RadioButtonEnabled.png");
        imagePaths.add("resources/RadioButtonDisabled.png");
        
        images = loader.loadImages(imagePaths);
        
        soundPaths.add("resources/mouseclick.wav");
        
        sounds = loader.loadSounds(soundPaths);
        
        fontSizes.add(32f);
        fontPaths.add("resources/OCR A Std.ttf");
        
        fonts = loader.loadFonts(fontPaths, fontSizes);
    }

    public void draw(Graphics g)
    {
        BufferedImage drawingImage = new BufferedImage(screenResolution.width, screenResolution.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = drawingImage.createGraphics();
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, windowResolution.width, windowResolution.height);
        
        g2d.drawImage(images.get(RADIOBUTTONENABLED), 100, 100, null);
        
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
    }
}
