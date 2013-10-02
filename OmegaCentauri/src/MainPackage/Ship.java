package MainPackage;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

// @author Davis Freeman and Michael Kieburtz
public abstract class Ship {

    protected int hull;
    protected int fuel;
    protected int power;
    protected Type type;
    protected Point location;
    // File -> FileInputStream -> ImageIO -> buffered image
    protected BufferedImage image; // readonly
    
    public BufferedImage getImage()
    {
        return this.image;
    }
    
    protected File imageFile;
    protected FileInputStream inputStream;
    
    // MAKE SURE TO SET THE SHIP IMAGE IN THE CONSTRUCTOR!!!!!
    
    //@returns success
    protected boolean setUpShipImage()
    {
        try {
            inputStream = new FileInputStream(imageFile);
            image = ImageIO.read(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("error reading file: " + imageFile.getPath());
        } catch (NullPointerException ex2)
        {
            ex2.printStackTrace();
            System.err.println("this shouldn't have happend. Issue with base class 'Ship'");
            return false;
        }
        return true;
    }
    
    
}
