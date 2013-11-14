package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

// @author Davis Freeman and Michael Kieburtz
public abstract class Ship {

    protected int hull;
    protected int fuel;
    protected int power;
    protected Type type;
    protected Point2D.Double location;
    protected Point2D.Double nextLocation;
    // File -> FileInputStream -> ImageIO -> buffered image
    protected ArrayList <BufferedImage> images = new ArrayList<BufferedImage>();
    
    public BufferedImage getImage()
    {
        return this.images.get(0);
    }
    
    protected ArrayList <File> imageFiles = new ArrayList<File>();
    protected ArrayList <FileInputStream> inputStreams = new ArrayList<FileInputStream>();
    
    // MAKE SURE TO SET THE SHIP IMAGE IN THE CONSTRUCTOR!!!!!
    
    //returns success
    protected boolean setUpShipImage()
    {
        try {
            inputStreams.add(new FileInputStream(imageFiles.get(0)));
            images.add(ImageIO.read(inputStreams.get(0)));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("error reading file: " + imageFiles.get(0).getPath());
        } catch (NullPointerException ex2)
        {
            ex2.printStackTrace();
            System.err.println("this shouldn't have happend. Issue with base class 'Ship'");
            return false;
        }
        return true;
    }
    
    
}
