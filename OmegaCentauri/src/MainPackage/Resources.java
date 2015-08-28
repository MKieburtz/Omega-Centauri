package MainPackage;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.Clip;

/**
 * @author Michael Kieburtz
 */
public class Resources {

    private enum ThingsToClear
    {
        images,
        sounds,
        fonts
    }

    private MediaLoader loader = new MediaLoader();

    private ArrayList<String> imagePaths = new ArrayList<>();
    private HashMap<String, BufferedImage> images = new HashMap<>();

    private ArrayList<String> soundPaths = new ArrayList<>();
    private HashMap<String, Clip> sounds = new HashMap<>();

    private ArrayList<FontInfo> fontData = new ArrayList<>();
    private HashMap<FontInfo, Font> fonts = new HashMap<>();

    public Resources() 
    {
        imagePaths.add("resources/FighterIdle.png");
        imagePaths.add("resources/FighterThrust.png");
        imagePaths.add("resources/FighterLeft.png");
        imagePaths.add("resources/FighterRight.png");
        imagePaths.add("resources/FighterThrustLeft.png");
        imagePaths.add("resources/FighterThrustRight.png");
        imagePaths.add("resources/FighterHighlight.png");
        imagePaths.add("resources/EnemyFighterIdle.png");
        imagePaths.add("resources/EnemyFighterThrusting.png");
        imagePaths.add("resources/EnemyFighterTurningLeft.png");
        imagePaths.add("resources/EnemyFighterTurningRight.png");
        imagePaths.add("resources/EnemyFighterThrustingLeft.png");
        imagePaths.add("resources/EnemyFighterThrustingRight.png");
        imagePaths.add("resources/MediumEnemyFighter.png");
        imagePaths.add("resources/EMFWingTop.png");
        imagePaths.add("resources/EMFWingBottom.png");
        imagePaths.add("resources/Turret.png");
        imagePaths.add("resources/Pulse.png");
        imagePaths.add("resources/EnemyShot.png");
        imagePaths.add("resources/PauseMenu.png");
        imagePaths.add("resources/PauseButton_ToMenu.png");
        imagePaths.add("resources/GameOver.png");
        imagePaths.add("resources/ReturnToTheBattlefield.png");
        imagePaths.add("resources/RadioButtonEnabled.png");
        imagePaths.add("resources/RadioButtonDisabled.png");
        imagePaths.add("resources/EnemyFighterShield.png");
        imagePaths.add("resources/EnemyMediumFighterShield.png");
        imagePaths.add("resources/FighterShield.png");
        imagePaths.add("resources/Level2Shot.png");
        imagePaths.add("resources/Missile.png");
        imagePaths.add("resources/FighterExplosionSpritesheet.png");
        imagePaths.add("resources/MissileExplosionSpritesheet.png");
        imagePaths.add("resources/RangeExplosionSpritesheet.png");
        imagePaths.add("resources/EnemyMediumFighterBodyExplosion.png");
        imagePaths.add("resources/EMFWingExplosion.png");
        imagePaths.add("resources/DangerBar.png");
        imagePaths.add("resources/HealthyBar.png");
        imagePaths.add("resources/HUDBackground.png");
        imagePaths.add("resources/ShieldStatusBad.png");
        imagePaths.add("resources/ShieldStatusGood.png");
        imagePaths.add("resources/ShieldStatusWarning.png");
        imagePaths.add("resources/WarningBar.png");
        imagePaths.add("resources/ShipStatusGood.png");
        imagePaths.add("resources/ShipStatusWarning.png");
        imagePaths.add("resources/ShipStatusBad.png");
        imagePaths.add("resources/ButtonHover.png");
        imagePaths.add("resources/LogoWindows.png");
        imagePaths.add("resources/ButtonHoverLong.png");
        
//        soundPaths.add("resources/Pulse.wav");
//        soundPaths.add("resources/Mouseclick.wav");

        fontData.add(new FontInfo("resources/OCR A Std.ttf", 10f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 50f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 32f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 24f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 16f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 12f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 30f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 50f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 32f));
        fontData.add(new FontInfo("resources/Orbitron-Regular.ttf", 24f));

        loadAllImages();
        loadAllSounds();
        loadAllFonts();
    }

    public ArrayList<BufferedImage> getImagesForObject(ArrayList<String> paths) 
    {
        ArrayList<BufferedImage> imagesToReturn = new ArrayList<>();
        
        for (String s : paths)
        {
            imagesToReturn.add(images.get(s));
        }
        
        return imagesToReturn;
    }
    
    public BufferedImage getImageForObject(String path)
    {
        return images.get(path);
    }
    
    public ArrayList<Clip> getSoundsForObject(ArrayList<String> paths)
    {
        ArrayList<Clip> soundsToReturn = new ArrayList<>();
        
        for (String s : paths)
        {
            soundsToReturn.add(sounds.get(s));
        }
        
        return soundsToReturn;
    }

    public ArrayList<Font> getFontsForObject(ArrayList<FontInfo> fontInfos)
    {
        ArrayList<Font> fontsToReturn = new ArrayList<>();
        
        for (FontInfo info : fontInfos)
        {
            fontsToReturn.add(fonts.get(info));
        }
        
        return fontsToReturn;
    }
    
    public Font getFontForObject(FontInfo info)
    {
        return fonts.get(info);
    }

    private void loadAllImages() {
        for (String s : imagePaths) 
        {
            images.put(s, loader.loadImage(s));
        }
    }

    private void loadAllSounds() 
    {
        for (String s : soundPaths) 
        {
            sounds.put(s, loader.loadSound(s));
        }
    }

    private void loadAllFonts() 
    {
        for (FontInfo info : fontData) 
        {
            fonts.put(info, loader.loadFont(info.path, info.size));
        }
    }
}
