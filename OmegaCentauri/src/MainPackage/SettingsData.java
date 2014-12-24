package MainPackage;

import java.io.*;
/**
 * @author Michael Kieburtz
 */
public class SettingsData implements Serializable
{
    
    private boolean graphicsQualityLow = false;
    private boolean windowed = true;
    
    public boolean getGraphicsQualityLow()
    {
        return graphicsQualityLow;
    }
    
    public void setGraphicsQualityLow(boolean quality)
    {
        graphicsQualityLow = quality;
    }
    
    public boolean getWindowed()
    {
        return windowed;
    }
    
    public void setWindowed(boolean windowed)
    {
        this.windowed = windowed;
    }
    
    public void resetDefaults() 
    {
        windowed = true;
        graphicsQualityLow = false;
    }
}
