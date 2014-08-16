package MainPackage;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

/**
 * @author Kieburtz
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
    private static ArrayList<String> soundPaths = new ArrayList<>();
    private static ArrayList<String> fontPaths = new ArrayList<>();
    private static ArrayList<Float> fontSizes = new ArrayList<>();
    
    private static ArrayList<BufferedImage> images = new ArrayList<>();
    private static ArrayList<Clip> sounds = new ArrayList<>();
    private static ArrayList<Font> fonts = new ArrayList<>();
    
    private Resources(){}

    public static ArrayList<BufferedImage> getImagesForPlayer()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/FighterIdle.png");
        imagePaths.add("resources/FighterThrust.png");
        imagePaths.add("resources/FighterLeft.png");
        imagePaths.add("resources/FighterRight.png");
        imagePaths.add("resources/FighterThrustLeft.png");
        imagePaths.add("resources/FighterThrustRight.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<Clip> getSoundsForPlayer()
    {
        clearLists(ThingsToClear.sounds);
        
        soundPaths.add("resources/Pulse.wav");
        
        loadSounds();
        
        return sounds;
    }
    
    public static ArrayList<BufferedImage> getImagesForEnemyFighter()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/EnemyFighterIdle.png");
        imagePaths.add("resources/EnemyFighterThrusting.png");
        imagePaths.add("resources/EnemyFighterTurningLeft.png");
        imagePaths.add("resources/EnemyFighterTurningRight.png");
        imagePaths.add("resources/EnemyFighterThrustingLeft.png");
        imagePaths.add("resources/EnemyFighterThrustingRight.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<BufferedImage> getImagesForMediumEnemyFighter()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/MediumEnemyFighter.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<BufferedImage> getImagesForTurret()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/Turret.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<BufferedImage> getImagesForHUD()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/TempScannerModule.png");
        imagePaths.add("resources/healthbackground.png");
        imagePaths.add("resources/HealthyTick.png");
        imagePaths.add("resources/WarningTick.png");
        imagePaths.add("resources/DangerTick.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<BufferedImage> getImagesForMainMenu()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/StartButtonNoHover.png");
        imagePaths.add("resources/StartButtonHover.png");
        imagePaths.add("resources/CloseButtonNoHover.png");
        imagePaths.add("resources/CloseButtonHover.png");
        imagePaths.add("resources/SettingsButtonNoHover.png");
        imagePaths.add("resources/SettingsButtonHover.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<Clip> getSoundsForMainMenu()
    {
        clearLists(ThingsToClear.sounds);
        
        soundPaths.add("resources/Mouseclick.wav");

        loadSounds();
        
        return sounds;
    }
    
    public static ArrayList<BufferedImage> getImagesForPulseShot()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/Pulse.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<BufferedImage> getImagesForEnemyPulseShot()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/EnemyShot.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<Font> getFontsForRenderer()
    {
        clearLists(ThingsToClear.fonts);
        
        fontSizes.add(10f);
        fontPaths.add("resources/OCR A Std.ttf");
        
        loadFonts();
        
        return fonts;
    }
    
    public static ArrayList<BufferedImage> getImagesForRenderer()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/PauseMenu.png");
        imagePaths.add("resources/PauseButton_ToMenu.png");
        imagePaths.add("resources/GameOver.png");
        imagePaths.add("resources/ReturnToTheBattlefield.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<BufferedImage> getImagesForSettingsMenu()
    {
        clearLists(ThingsToClear.images);
        
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
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<Clip> getSoundsForSettingsMenu()
    {
        clearLists(ThingsToClear.sounds);
        
        soundPaths.add("resources/Mouseclick.wav");
        
        loadSounds();
        
        return sounds;
    }
    
    public static ArrayList<Font> getFontsForSettingsMenu()
    {
        clearLists(ThingsToClear.fonts);
        
        fontSizes.add(50f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(32f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(24f);
        fontPaths.add("resources/OCR A Std.ttf");
        fontSizes.add(16f);
        fontPaths.add("resources/OCR A Std.ttf");
        
        loadFonts();
        
        return fonts;
    }
    
    public static ArrayList<BufferedImage> getImagesForShield()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/FILLERshield.png");
        
        loadImages();
        
        return images;
    }
    
    public static ArrayList<BufferedImage> getImagesForEnemyShield()
    {
        clearLists(ThingsToClear.images);
        
        imagePaths.add("resources/FILLERshieldEnemy.png");
        
        loadImages();
        
        return images;
    }
    
    private static void clearLists(ThingsToClear whatToClear)
    {
        switch(whatToClear)
        {
            case images:
                imagePaths.clear();
                images.clear();
                break;
            case sounds:
                soundPaths.clear();
                sounds.clear();
                break;
            case fonts:
                fontPaths.clear();
                fontSizes.clear();
                fonts.clear();
                break;
        }
    }
    
    private static void loadImages()
    {
        images = Calculator.toCompatibleImages(loader.loadImages(imagePaths));
    }
    
    private static void loadSounds()
    {
        sounds = loader.loadSounds(soundPaths);
    }
    
    private static void loadFonts()
    {
        fonts = loader.loadFonts(fontPaths, fontSizes);
    }
}
