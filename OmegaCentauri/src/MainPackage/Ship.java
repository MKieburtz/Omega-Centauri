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
    protected ArrayList<String> imagePaths = new ArrayList<String>();
    protected ImageLoader imageLoader = new ImageLoader();
    
    protected ArrayList<Shot> shots = new ArrayList<Shot>();

    public BufferedImage getImage() {
        return activeImage;
    }

    public BufferedImage getImage(int index) {
        return this.images.get(index);
    }

    

}
