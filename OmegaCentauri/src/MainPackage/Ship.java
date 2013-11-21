package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

// @author Michael Kieburtz and Davis Freeman
public abstract class Ship {

    protected int hull;
    protected int fuel;
    protected int power;
    protected Type type;
    protected Point2D.Double location;
    protected Point2D.Double nextLocation;
    // File -> FileInputStream -> ImageIO -> buffered image
    protected ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    protected BufferedImage activeImage;

    public BufferedImage getImage() {
        return activeImage;
    }

    public BufferedImage getImage(int index) {
        return this.images.get(index);
    }

    protected ArrayList<File> imageFiles = new ArrayList<File>();
    protected ArrayList<FileInputStream> inputStreams = new ArrayList<FileInputStream>();

    // MAKE SURE TO SET THE SHIP IMAGE IN THE CONSTRUCTOR!!!!!
    //returns success
    protected boolean setUpShipImage() {
        int j = 0;
        try {
            for (int i = 0; i < imageFiles.size(); i++) {
                inputStreams.add(new FileInputStream(imageFiles.get(i)));
            }
            for (j = 0; j < inputStreams.size(); j++) {
                images.add(ImageIO.read(inputStreams.get(j)));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error reading file: " + imageFiles.get(j).getPath());
        } catch (NullPointerException ex2) {
            ex2.printStackTrace();
            System.err.println("This shouldn't have happend. Issue with base class 'Ship'");
            return false;
        }
        return true;
    }

}
