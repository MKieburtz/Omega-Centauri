package MainPackage;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

// @author Michael Kieburtz
public class ImageLoader {

    public ImageLoader() {

    }

    public ArrayList<BufferedImage> loadImages(ArrayList<String> paths) {
        ArrayList<File> files = new ArrayList<File>();
        ArrayList<FileInputStream> streams = new ArrayList<FileInputStream>();
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        
        for (String p : paths) {
            files.add(new File(p));
        }
        
        for (File f : files)
        {
            try {
                streams.add(new FileInputStream(f));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.err.println("Error reading file: " + f.getPath());
            }
        }
        
        for (FileInputStream s : streams)
        {
            try {
                images.add(ImageIO.read(s));
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Image error, bad file");
            }
        }
        
        return images;
        
    }
}
