package MainPackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.sound.sampled.Clip;
import java.io.*;

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
    private final int BACKBUTTONHOVER = 2;
    private final int BACKBUTTONNOHOVER = 3;
    private final int CONTROLSBUTTONHOVER = 4;
    private final int CONTROLSBUTTONNOHOVER = 5;
    private final int RESETBUTTONHOVER = 6;
    private final int RESETBUTTONNOHOVER = 7;
    private final int SAVEBUTTONHOVER = 8;
    private final int SAVEBUTTONNOHOVER = 9;

    private final int TITLEFONT = 0;
    private final int SUBTITLEFONT = 1;
    private final int TEXTFONT = 2;

    private final int CLICKSOUND = 0;

    private boolean controlHover = false;
    private boolean backHover = false;
    private boolean resetHover = false;
    private boolean saveHover = false;

    private Rectangle controlsRectangle;
    private Rectangle backRectangle;
    private Rectangle saveRectangle;
    private Rectangle resetRectangle;
    private Rectangle lowGraphicsRectangle;
    private Rectangle highGraphicsRectangle;
    private Rectangle windowedResolutionRectangle;
    private Rectangle fullscreenResolutionRectangle;

    private SettingsData settingsData;

    public Settings(Dimension screenSize, Dimension windowSize) {
        this.screenResolution = screenSize;
        this.windowResolution = windowSize;

        loader = new MediaLoader();

        imagePaths.add("resources/RadioButtonEnabled.png");
        imagePaths.add("resources/RadioButtonDisabled.png");
        imagePaths.add("resources/BackButtonHover.png");
        imagePaths.add("resources/BackButtonNoHover.png");
        imagePaths.add("resources/ControlsButtonHover.png");
        imagePaths.add("resources/ControlsButtonNoHover.png");
        imagePaths.add("resources/ResetButtonHover.png");
        imagePaths.add("resources/ResetButtonNoHover.png");
        imagePaths.add("resources/SaveButtonHover.png");
        imagePaths.add("resources/SaveButtonNoHover.png");

        images = loader.loadImages(imagePaths);

        soundPaths.add("resources/Mouseclick.wav");

        sounds = loader.loadSounds(soundPaths);

        fontSizes.add(50f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(32f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(24f);
        fontPaths.add("resources/OCR A Std.ttf");

        fonts = loader.loadFonts(fontPaths, fontSizes);

        setRects();
      
        settingsData = new SettingsData();
        
        File f = new File("Data/settings.ser");
        if (f.exists())
        {
            load();
        }
    }

    public void draw(Graphics g) {
        BufferedImage drawingImage = new BufferedImage(windowResolution.width, windowResolution.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = drawingImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, windowResolution.width, windowResolution.height);

        g2d.setColor(new Color(81, 81, 81));
        g2d.setFont(fonts.get(TITLEFONT));
        g2d.drawString("OPTIONS", windowResolution.width / 2 - 125, 75);

        g2d.setFont(fonts.get(SUBTITLEFONT));
        g2d.drawString("GRAPHICS", 100, 200);

        g2d.setFont(fonts.get(TEXTFONT));
        g2d.drawString("LOW", 100, 250);
        g2d.drawString("HIGH", 100, 280);

        if (settingsData.getGraphicsQualityLow()) {
            g2d.drawImage(images.get(RADIOBUTTONENABLED), 190, 230, null);
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), 190, 260, null);
        } else {
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), 190, 230, null);
            g2d.drawImage(images.get(RADIOBUTTONENABLED), 190, 260, null);
        }

        g2d.setFont(fonts.get(SUBTITLEFONT));
        g2d.drawString("RESOLUTION", 400, 200);

        g2d.setFont(fonts.get(TEXTFONT));
        g2d.drawString("WINDOWED", 400, 250);
        g2d.drawString("FULLSCREEN", 400, 280);

        if (settingsData.getWindowed()) {
            g2d.drawImage(images.get(RADIOBUTTONENABLED), 590, 230, null);
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), 590, 260, null);
        } else {
            g2d.drawImage(images.get(RADIOBUTTONDISABLED), 590, 230, null);
            g2d.drawImage(images.get(RADIOBUTTONENABLED), 590, 260, null);
        }

        if (controlHover) {
            g2d.drawImage(images.get(CONTROLSBUTTONHOVER), controlsRectangle.x, controlsRectangle.y, null);
        } else {
            g2d.drawImage(images.get(CONTROLSBUTTONNOHOVER), controlsRectangle.x, controlsRectangle.y, null);
        }

        if (backHover) {
            g2d.drawImage(images.get(BACKBUTTONHOVER), backRectangle.x, backRectangle.y, null);
        } else {
            g2d.drawImage(images.get(BACKBUTTONNOHOVER), backRectangle.x, backRectangle.y, null);
        }

        if (saveHover) {
            g2d.drawImage(images.get(SAVEBUTTONHOVER), saveRectangle.x, saveRectangle.y, null);
        } else {
            g2d.drawImage(images.get(SAVEBUTTONNOHOVER), saveRectangle.x, saveRectangle.y, null);
        }

        if (resetHover) {
            g2d.drawImage(images.get(RESETBUTTONHOVER), resetRectangle.x, resetRectangle.y, null);
        } else {
            g2d.drawImage(images.get(RESETBUTTONNOHOVER), resetRectangle.x, resetRectangle.y, null);
        }

        g2d.setColor(Color.RED);
//        g2d.draw(controlsRectangle);
//        g2d.draw(lowGraphicsRectangle);
//        g2d.draw(highGraphicsRectangle);
//        g2d.draw(windowedResolutionRectangle);
//        g2d.draw(fullscreenResolutionRectangle);
//        g2d.draw(backRectangle);
//        g2d.draw(saveRectangle);
//        g2d.draw(resetRectangle);
//        
        g.drawImage(drawingImage, 0, 0, null);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setWindowSize(Dimension windowSize) {
        this.windowResolution = windowSize;
        setRects();
    }

    private void setRects() {
        controlsRectangle = new Rectangle(720, 220, images.get(CONTROLSBUTTONNOHOVER).getWidth(), images.get(CONTROLSBUTTONNOHOVER).getHeight());

        lowGraphicsRectangle = new Rectangle(100, 230, 120, 20);

        highGraphicsRectangle = new Rectangle(100, 260, 120, 20);

        windowedResolutionRectangle = new Rectangle(400, 230, 210, 20);

        fullscreenResolutionRectangle = new Rectangle(400, 260, 210, 20);

        backRectangle = new Rectangle(
                100,
                windowResolution.height - 75,
                images.get(BACKBUTTONNOHOVER).getWidth(),
                images.get(BACKBUTTONNOHOVER).getHeight()
        );

        saveRectangle = new Rectangle(
                windowResolution.width / 2 - images.get(SAVEBUTTONNOHOVER).getWidth() / 2,
                windowResolution.height - 75,
                images.get(SAVEBUTTONNOHOVER).getWidth(),
                images.get(SAVEBUTTONNOHOVER).getHeight()
        );

        resetRectangle = new Rectangle(
                windowResolution.width - 100 - images.get(RESETBUTTONNOHOVER).getWidth(),
                windowResolution.height - 75,
                images.get(RESETBUTTONNOHOVER).getWidth(),
                images.get(RESETBUTTONNOHOVER).getHeight()
        );

    }

    public void checkMouseMoved(Point location) {
        if (active) {
            if (controlsRectangle.contains(location)) {
                controlHover = true;
            } else if (backRectangle.contains(location)) {
                backHover = true;
            } else if (saveRectangle.contains(location)) {
                saveHover = true;
            } else if (resetRectangle.contains(location)) {
                resetHover = true;
            } else {
                controlHover = false;
                backHover = false;
                saveHover = false;
                resetHover = false;
            }
        }
    }

    public void checkMousePressed(Point location) {
        if (lowGraphicsRectangle.contains(location)) {
            settingsData.setGraphicsQualityLow(true);
        } else if (highGraphicsRectangle.contains(location)) {
            settingsData.setGraphicsQualityLow(false);
        } else if (windowedResolutionRectangle.contains(location)) {
            settingsData.setWindowed(true);
        } else if (fullscreenResolutionRectangle.contains(location)) {
            settingsData.setWindowed(false);
        } else if (backRectangle.contains(location)) {
            sounds.get(CLICKSOUND).setFramePosition(0);
            sounds.get(CLICKSOUND).start();
            active = false;
        } else if (resetRectangle.contains(location)) {
            sounds.get(CLICKSOUND).setFramePosition(0);
            sounds.get(CLICKSOUND).start();
            settingsData.resetDefaults();
        } else if (saveRectangle.contains(location)) {
            sounds.get(CLICKSOUND).setFramePosition(0);
            sounds.get(CLICKSOUND).start();
            
            save();
        }
    }

    public void checkMouseExited() {
        controlHover = false;
        backHover = false;
        saveHover = false;
        resetHover = false;
    }

    private void save() {
        try {
            OutputStream output = new FileOutputStream("Data/settings.ser");
            OutputStream buffer = new BufferedOutputStream(output);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(buffer);
            
            objectOutputStream.writeObject(settingsData);
            
            objectOutputStream.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void load() {
        SettingsData settings;
        try {
            InputStream input = new FileInputStream("Data/settings.ser");
            InputStream buffer = new BufferedInputStream(input);
            ObjectInputStream objectInputStream = new ObjectInputStream(buffer);
            
            
            settings = (SettingsData)objectInputStream.readObject();
            
            settingsData = settings;
            
            objectInputStream.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
