package MainPackage;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javax.sound.sampled.Clip;

/**
 * @author Michael Kieburtz
 */
public class Resources {

    private enum ThingsToClear {

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

    public Resources() {
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
        imagePaths.add("resources/shield.png");
        imagePaths.add("resources/FILLERshieldEnemy.png");
        imagePaths.add("resources/Level2Shot.png");
        imagePaths.add("resources/Missile.png");
        imagePaths.add("resources/FighterExplosionSpritesheet.png");
        imagePaths.add("resources/MissileExplosionSpritesheet.png");

        soundPaths.add("resources/Pulse.wav");
        soundPaths.add("resources/Mouseclick.wav");

        fontData.add(new FontInfo("resources/OCR A Std.ttf", 10f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 50f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 32f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 24f));
        fontData.add(new FontInfo("resources/OCR A Std.ttf", 16f));

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
        for (String s : imagePaths) {
            images.put(s, loader.loadImage(s));
        }
    }

    private void loadAllSounds() {
        for (String s : soundPaths) {
            sounds.put(s, loader.loadSound(s));
        }
    }

    private void loadAllFonts() {
        for (FontInfo info : fontData) {
            fonts.put(info, loader.loadFont(info.path, info.size));
        }
    }
}
