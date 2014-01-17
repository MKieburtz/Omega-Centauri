package MainPackage;

import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

// @author Michael Kieburtz
public class MediaLoader {

    public MediaLoader() {
        // default constructor
    }

    public ArrayList<BufferedImage> loadImages(ArrayList<String> paths) {
        ArrayList<File> files = new ArrayList<File>();
        ArrayList<FileInputStream> streams = new ArrayList<FileInputStream>();
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        
        // path -> file -> FileInputStream -> BufferedImage
        for (String p : paths) {
            files.add(new File(p));
        }
        
        for (File f : files)
        {
            try {
                streams.add(new FileInputStream(f));
            } catch (FileNotFoundException ex) {
                System.err.println("Error reading file: " + f.getPath() + "\n");
                ex.printStackTrace();
            }
        }
        
        for (FileInputStream s : streams)
        {
            try {
                images.add(ImageIO.read(s));
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Image error, bad file\n");
            }
        }
        
        return images;
    }
    
     public ArrayList<Clip> loadSounds(ArrayList<String> paths)
    {
        ArrayList<File> files = new ArrayList<File>();
        ArrayList<AudioInputStream> streams = new ArrayList<AudioInputStream>();
        ArrayList<Clip> sounds = new ArrayList<Clip>();
        
        for (String p : paths) {
            files.add(new File(p));
        }
        
        for (File f : files)
        {
            try {
                try {
                    streams.add(AudioSystem.getAudioInputStream(f));
                } catch (IOException ex) {
                    System.err.println("Error reading file: " + f.getPath() + "\n");
                }
            } catch (UnsupportedAudioFileException ex) {
                System.err.println("Error reading file: " + f.getPath() + "\n");
                ex.printStackTrace();
            } // end catch
        } // end foreach loop
        
        for (AudioInputStream a : streams)
        {
            Clip clip = null;
            
            try {
                clip = AudioSystem.getClip();
                clip.open(a); // this actually loads the sound
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            
            sounds.add(clip);
        }
        
        return sounds;
    } // end method
}