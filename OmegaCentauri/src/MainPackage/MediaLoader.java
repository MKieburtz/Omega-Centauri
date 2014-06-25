package MainPackage;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

// @author Michael Kieburtz
public class MediaLoader {

    public MediaLoader() {
        // default constructor
    }

    public ArrayList<BufferedImage> loadImages(ArrayList<String> paths) {
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        ArrayList<InputStream> streams = new ArrayList<InputStream>();
        for (String s : paths) {
            streams.add(getClass().getResourceAsStream(s));
        }
        for (InputStream s : streams) {
            try {
                images.add(ImageIO.read(s));
                s.close();
            } catch (IOException ex) {
                System.err.println("image loading problem");
            }
        }
        return images;
    }

    public ArrayList<Clip> loadSounds(ArrayList<String> paths) {
        ArrayList<Clip> sounds = new ArrayList<>();
        ArrayList<URL> urls = new ArrayList<>();
        
        for (String s : paths) {
            urls.add(getClass().getResource(s));
        }
        for (URL url : urls) {
            try {
                AudioInputStream a = AudioSystem.getAudioInputStream(url);
 
                    Clip c = AudioSystem.getClip();
                    c.open(a);
                    sounds.add(c);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                System.err.println("error loading sound");
                ex.printStackTrace();
                
            }
        }
        return sounds;

    } // end method

    

    public ArrayList<Font> loadFonts(ArrayList<String> paths, ArrayList<Float> sizes) {
        ArrayList<Font> fonts = new ArrayList<Font>();
        ArrayList<InputStream> streams = new ArrayList<>();
        
        for (String path : paths) {
            streams.add(getClass().getResourceAsStream(path));
        }

        try {
            for (int i = 0; i < streams.size(); i++) // use for loop to access each size 
            {
                fonts.add(Font.createFont(Font.TRUETYPE_FONT, streams.get(i)).deriveFont(sizes.get(i)));
                streams.get(i).close();
            }

        } catch (FontFormatException ex) {
            System.err.println("Bad font");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("Bad file");
            ex.printStackTrace();
        }

        return fonts;
    }
}
