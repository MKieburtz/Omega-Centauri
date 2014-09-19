package MainPackage;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
* @author Michael Kieburtz
*/
public class MediaLoader {

    public MediaLoader() {
        // default constructor
    }

    public BufferedImage loadImage(String path) {
        BufferedImage image = null;
        InputStream stream = getClass().getResourceAsStream(path);

        try {
            image = ImageIO.read(stream);
            stream.close();
        } catch (IOException ex) {
            System.err.println("image loading problem");
        }

        return image;
    }

    public Clip loadSound(String path) {
        Clip sound = null;
        URL url = getClass().getResource(path);

        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(url);

            sound = AudioSystem.getClip();
            sound.open(a);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            System.err.println("error loading sound");
            ex.printStackTrace();

        }
        return sound;
    }
    
    public Font loadFont(String path, Float size)
    {
        Font font = null;
        InputStream stream = getClass().getResourceAsStream(path);
        
        try {
        font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
        stream.close();
        }  catch (FontFormatException ex) {
             System.err.println("Bad font");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Bad file");
            ex.printStackTrace();
        }
        return font;
    }
}
