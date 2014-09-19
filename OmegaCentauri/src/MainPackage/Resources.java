package MainPackage;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.Clip;

/**
 * @author Michael Kieburtz
 */
public class Resources {
    
    private static enum ThingsToClear
    {
        images,
        sounds,
        fonts
    }
    
    private static MediaLoader loader = new MediaLoader();
    
    private static ArrayList<String> imagePaths = new ArrayList<>();
    private static HashMap<String, BufferedImage> images = new HashMap<>();
    
    private static ArrayList<String> soundPaths = new ArrayList<>();
    private static HashMap<String, Clip> sounds = new HashMap<>();
    
    private static HashMap<String, Float> fontSizes = new HashMap<>();
    private static ArrayList<String> fontPaths = new ArrayList<>();
    private static HashMap<HashMap<String, Float>, Font> fonts = new HashMap<>();
    
    
       
    public Resources()
    {
        imagePaths.add("resources/FighterIdle.png");
        imagePaths.add("resources/FighterThrust.png");
        imagePaths.add("resources/FighterLeft.png");
        imagePaths.add("resources/FighterRight.png");
        imagePaths.add("resources/FighterThrustLeft.png");
        imagePaths.add("resources/FighterThrustRight.png");
        imagePaths.add("resources/EnemyFighterIdle.png");
        imagePaths.add("resources/EnemyFighterThrusting.png");
        imagePaths.add("resources/EnemyFighterTurningLeft.png");
        imagePaths.add("resources/EnemyFighterTurningRight.png");
        imagePaths.add("resources/EnemyFighterThrustingLeft.png");
        imagePaths.add("resources/EnemyFighterThrustingRight.png");
        imagePaths.add("resources/MediumEnemyFighter.png");
        imagePaths.add("resources/Turret.png");
        imagePaths.add("resources/TempScannerModule.png");
        imagePaths.add("resources/healthbackground.png");
        imagePaths.add("resources/HealthyTick.png");
        imagePaths.add("resources/WarningTick.png");
        imagePaths.add("resources/DangerTick.png");
        imagePaths.add("resources/StartButtonNoHover.png");
        imagePaths.add("resources/StartButtonHover.png");
        imagePaths.add("resources/CloseButtonNoHover.png");
        imagePaths.add("resources/CloseButtonHover.png");
        imagePaths.add("resources/SettingsButtonNoHover.png");
        imagePaths.add("resources/SettingsButtonHover.png");
        imagePaths.add("resources/Pulse.png");
        imagePaths.add("resources/EnemyShot.png");
        imagePaths.add("resources/PauseMenu.png");
        imagePaths.add("resources/PauseButton_ToMenu.png");
        imagePaths.add("resources/GameOver.png");
        imagePaths.add("resources/ReturnToTheBattlefield.png");
        imagePaths.add("resources/RadioButtonEnabled.png");
        imagePaths.add("resources/RadioButtonDisabled.png");
        imagePaths.add("resources/BackButtonHover.png");
        imagePaths.add("resources/BackButtonNoHover.png");
        imagePaths.add("resources/ControlsButtonHover.png");
        imagePaths.add("resources/ControlsButtonNoHover.png");
        imagePaths.add("resources/ResetButtonHover.png");
        imagePaths.add("resources/ResetButtonNoHover.png");
        imagePaths.add("resources/SaveButtonHover.png");
        imagePaths.add("resources/SaveButtonNoHover.png");
        imagePaths.add("resources/FILLERshield.png");
        imagePaths.add("resources/FILLERshieldEnemy.png");
        imagePaths.add("resources/Level2Shot.png");
        imagePaths.add("resources/Missile.png");
        imagePaths.add("resources/FighterExplosionSpritesheet.png");
        imagePaths.add("resources/MissileExplosionSpritesheet.png");
        
        soundPaths.add("resources/Pulse.wav");
        soundPaths.add("resources/Mouseclick.wav");
        
        fontSizes.put("resources/OCR A Std.ttf", 10f);
        fontPaths.add("resources/OCR A Std.ttf");
        
        fontSizes.add(50f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(32f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(24f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(16f);
        fontPaths.add("resources/OCR A Std.ttf");
        
    }

    public static ArrayList<BufferedImage> getImagesForPlayer()
    {
        imagePaths.add("resources/FighterIdle.png");
        imagePaths.add("resources/FighterThrust.png");
        imagePaths.add("resources/FighterLeft.png");
        imagePaths.add("resources/FighterRight.png");
        imagePaths.add("resources/FighterThrustLeft.png");
        imagePaths.add("resources/FighterThrustRight.png");
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<Clip> getSoundsForPlayer()
    {
        
        
        soundPaths.add("resources/Pulse.wav");
        
        loadSounds();
        
        return (ArrayList <Clip>)sounds.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForEnemyFighter()
    {
        
        
        imagePaths.add("resources/EnemyFighterIdle.png");
        imagePaths.add("resources/EnemyFighterThrusting.png");
        imagePaths.add("resources/EnemyFighterTurningLeft.png");
        imagePaths.add("resources/EnemyFighterTurningRight.png");
        imagePaths.add("resources/EnemyFighterThrustingLeft.png");
        imagePaths.add("resources/EnemyFighterThrustingRight.png");
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForMediumEnemyFighter()
    {
        
        
        imagePaths.add("resources/MediumEnemyFighter.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForTurret()
    {
        
        
        imagePaths.add("resources/Turret.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForHUD()
    {
        
        
        imagePaths.add("resources/TempScannerModule.png");
        imagePaths.add("resources/healthbackground.png");
        imagePaths.add("resources/HealthyTick.png");
        imagePaths.add("resources/WarningTick.png");
        imagePaths.add("resources/DangerTick.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForMainMenu()
    {
        
        
        imagePaths.add("resources/StartButtonNoHover.png");
        imagePaths.add("resources/StartButtonHover.png");
        imagePaths.add("resources/CloseButtonNoHover.png");
        imagePaths.add("resources/CloseButtonHover.png");
        imagePaths.add("resources/SettingsButtonNoHover.png");
        imagePaths.add("resources/SettingsButtonHover.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<Clip> getSoundsForMainMenu()
    {
        
        soundPaths.add("resources/Mouseclick.wav");
        
        loadSounds();
        
        return (ArrayList <Clip>)sounds.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForPulseShot()
    {
        
        
        imagePaths.add("resources/Pulse.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForEnemyPulseShot()
    {
        
        
        imagePaths.add("resources/EnemyShot.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<Font> getFontsForRenderer()
    {
        
        
        fontSizes.add(10f);
        fontPaths.add("resources/OCR A Std.ttf");
        
        loadFonts();
        
        return (ArrayList<Font>)fonts.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForRenderer()
    {
        
        
        imagePaths.add("resources/PauseMenu.png");
        imagePaths.add("resources/PauseButton_ToMenu.png");
        imagePaths.add("resources/GameOver.png");
        imagePaths.add("resources/ReturnToTheBattlefield.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForSettingsMenu()
    {
        
        
        imagePaths.add("resources/RadioButtonEnabled.png");
        imagePaths.add("resources/RadioButtonDisabled.png");
        imagePaths.add("resources/BackButtonHover.png");
        imagePaths.add("resources/BackButtonNoHover.png");
        imagePaths.add("resources/ControlsButtonHover.png");
        imagePaths.add("resources/ControlsButtonNoHover.png");
        imagePaths.add("resources/ResetButtonHover.png");
        imagePaths.add("resources/ResetButtonNoHover.png");
        imagePaths.add("resources/SaveButtonHover.png");
        imagePaths.add("resources/SaveButtonNoHover.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<Clip> getSoundsForSettingsMenu()
    {
        
        
        soundPaths.add("resources/Mouseclick.wav");
        
        loadSounds();
        
        return (ArrayList <Clip>)sounds.clone();
    }
    
    public static ArrayList<Font> getFontsForSettingsMenu()
    {
        
        
        fontSizes.add(50f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(32f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(24f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(16f);
        fontPaths.add("resources/OCR A Std.ttf");
        
        loadFonts();
        
        return (ArrayList<Font>)fonts.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForShield()
    {
        
        
        imagePaths.add("resources/FILLERshield.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForEnemyShield()
    {
        
        
        imagePaths.add("resources/FILLERshieldEnemy.png");
        
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForTurretShot()
    {
        
        imagePaths.add("resources/Level2Shot.png");
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImagesForMissle()
    {
        
        imagePaths.add("resources/Missile.png");
        
        
        return (ArrayList<BufferedImage>)images.clone();
    }
    
    public static ArrayList<BufferedImage> getImageForFighterExplosion()
    {
        
        imagePaths.add("resources/FighterExplosionSpritesheet.png");
        
        
        return (ArrayList<BufferedImage>) images.clone();
    }
    
    public static ArrayList<BufferedImage> getImageForMissileExplosion()
    {
        
        imagePaths.add("resources/MissileExplosionSpritesheet.png");
        
        
        return (ArrayList<BufferedImage>) images.clone();
    }
    
    
    private static void loadAllImages()
    {
        for (String s : imagePaths)
        {
            images.put(s, loader.loadImage(s));
        }
    }
    
    private static void loadSounds()
    {
        for (String s : soundPaths)
        {
            sounds.put(s, loader.loadSound(s));
        }
    }
    
    private static void loadFonts()
    {
        for (String s : fontPaths)
        {
            fonts.put(s, loader.loadFont(s, Float.NaN))
        }
    }
}
