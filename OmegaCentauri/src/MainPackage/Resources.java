package MainPackage;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

/**
 * @author Kieburtz
 */
public class Resources {
    
    private MediaLoader loader = new MediaLoader();
    
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> soundPaths = new ArrayList<>();
    private ArrayList<String> fontPaths = new ArrayList<>();
    private ArrayList<Float> fontSizes = new ArrayList<>();
    
    private ArrayList<BufferedImage> images = new ArrayList<>();
    private ArrayList<Clip> sounds = new ArrayList<>();
    private ArrayList<Font> fonts = new ArrayList<>();
    
    public static int DEVSHIPMOVING = 0;
    public static int DEVSHIPTURNINGLEFT = 1;
    public static int DEVSHIPTURNINGRIGHT = 2;
    public static int DEVSHIPIDLE = 3;
    public static int EASTEREGGENEMYSHIP = 4;
    
    public static int BACKBUTTONHOVER = 5;
    public static int BACKBUTTONNOHOVER = 6;
    
    public static int CLOSEBUTTONHOVER = 7;
    public static int CLOSEBUTTONNOHOVER = 8;
    
    public static int CONTROLSBUTTONHOVER = 9;
    public static int CONTROLSBUTTONNOHOVER = 10;
    
    public static int RADIOBUTTONDISABLED = 11;
    public static int RADIOBUTTONENABLED = 12;
    
    public static int RESETBUTTONHOVER = 13;
    public static int RESETBUTTONNOHOVER = 14;
    
    public static int SAVEBUTTONHOVER = 15;
    public static int SAVEBUTTONNOHOVER = 16;
    
    public static int SETTINGSBUTTONHOVER = 17;
    public static int SETTINGSBUTTONNOHOVER = 18;
    
    public static int STARTBUTTONHOVER = 19;
    public static int STARTBUTTONNOHOVER = 20;
    
    public static int DANGERTICK = 21;
    public static int HEALTHYTICK = 22;
    public static int WARNINGTICK = 23;
    
    public static int ENEMYFIGHTERIDLE = 24;
    public static int ENEMYFIGHTERTHRUSTING = 25;
    public static int ENEMYFIGHTERTHRUSTINGLEFT = 26;
    public static int ENEMYFIGHTERTHRUSTINGRIGHT = 27;
    public static int ENEMYFIGHTERTURNINGRIGHT = 28;
    public static int ENEMYFIGHTERTURNINGLEFT = 29;
    
    public static int MEDIUMENEMYFIGHTER = 30;
    
    public static int MISSLE = 31;
    
    public static int ENEMYSHOT = 32;
    public static int LEVEL2SHOT = 33;
    public static int PULSE = 34;
    public static int SPEARSHOT = 35;
    
    public static int SHIELD = 36;
    public static int ENEMYSHIELD = 37;
    
    public static int FIGHTEREXPLOSION = 38;
    
    public static int FIGHTERIDLE = 39;
    public static int FIGHTERLEFT = 40;
    public static int FIGHTERRIGHT = 41;
    public static int FIGHTERTHRUST = 42;
    public static int FIGHTERTHRUSTLEFT = 43;
    public static int FIGHTERTHRUSTRIGHT = 44;
    
    public static int GAMEOVER = 45;
    public static int RETURNTOBATTLEFIELD = 46;
    
    public static int LOGO = 47;
    
    public static int TOMENU = 48;
    public static int PAUSEMENUBACKGROUND = 49;
    public static int SCANNERMODULE = 50;
    public static int HEALTHBACKGROUND = 51;
    public static int TURRET = 52;
    
    public static int MOUSECLICK = 0;
    public static int PULSESOUND = 1;
    
    public static int OCR_A_STD = 0;
    public static int ORBITRON = 1;
    
    
    public Resources()
    {
        imagePaths.add("64657673686970Moving.png");
        imagePaths.add("64657673686970TurningLeft.png");
        imagePaths.add("64657673686970TurningRight.png");
        imagePaths.add("64657673686970Idle.png");
        imagePaths.add("EasterEggShip.png");
        
        imagePaths.add("BackButtonHover.png");
        imagePaths.add("BackButtonNoHover.png");
        
        imagePaths.add("CloseButtonHover.png");
        imagePaths.add("CloseButtonNoHover.png");
        
        imagePaths.add("ControlsButtonHover.png");
        imagePaths.add("ControlsButtonNoHover.png");
        
        imagePaths.add("RadioButtonDisabled.png");
        imagePaths.add("RadioButtonEnabled.png");
        
        imagePaths.add("ResetButtonHover.png");
        imagePaths.add("ResetButtonNoHover.png");
        
        imagePaths.add("SaveButtonHover.png");
        imagePaths.add("SaveButtonNoHover.png");
        
        imagePaths.add("SettingsButtonHover.png");
        imagePaths.add("SettingsButtonNoHover.png");
        
        imagePaths.add("StartButtonHover.png");
        imagePaths.add("StartButtonNoHover.png");
        
        imagePaths.add("DangerTick.png");
        imagePaths.add("HealthyTick.png");
        imagePaths.add("WarningTick.png");
        
        imagePaths.add("EnemyFighterIdle.png");
        imagePaths.add("EnemyFighterThrusting.png");
        imagePaths.add("EnemyFighterThrustingLeft.png");
        imagePaths.add("EnemyFighterThrustingRight.png");
        imagePaths.add("EnemyFighterTurningRight.png");
        imagePaths.add("EnemyFighterTurningLeft.png");
        
        imagePaths.add("MediumEnemyFighter.png");
        
        imagePaths.add("Missile.png");
        imagePaths.add("EnemyShot.png");
        imagePaths.add("Level2Shot.png");
        imagePaths.add("Pulse.png");
        imagePaths.add("SpearShot.png");
        
        imagePaths.add("FILLERshield.png");
        imagePaths.add("FILLERshieldEnemy.png");
        
        imagePaths.add("FighterExplosion.png"); // sprite sheet?
        
        imagePaths.add("FighterIdle.png");
        imagePaths.add("FighterLeft.png");
        imagePaths.add("FighterRight.png");
        imagePaths.add("FighterThrust.png");
        imagePaths.add("FighterThrustLeft.png");
        imagePaths.add("FighterThrustRight.png");
        
        imagePaths.add("GameOver.png");
        imagePaths.add("ReturnToTheBattlefield.png");
        
        imagePaths.add("OmegaCentauriLogo.png");
        
        imagePaths.add("PauseButton_ToMenu.png");
        imagePaths.add("PauseMenu.png");
        
        imagePaths.add("TempScannerModule.png");
        imagePaths.add("healthbackground.png");
        
        imagePaths.add("Turret.png");
        
        // sounds
        soundPaths.add("Mouseclick.wav");
        soundPaths.add("Pulse.wav");
        
        // fonts
        fontPaths.add("OCR A Std.ttf");
        fontPaths.add("Orbitron-Regular.ttf");
        
        fontSizes.add(10f);
        
        images = Calculator.toCompatibleImages(loader.loadImages(imagePaths));
        sounds = loader.loadSounds(soundPaths);
        fonts = loader.loadFonts(fontPaths, fontSizes);
    }

    
    public ArrayList<BufferedImage> getImages(int[] indexes)
    {
        ArrayList<BufferedImage> imagesToReturn = new ArrayList<>();
        
        for (Integer i : indexes)
        {
            imagesToReturn.add(images.get(i));
        }
        
        return imagesToReturn;
    }
    
    public ArrayList<Clip> getSounds(int[] indexes)
    {
        ArrayList<Clip> soundsToReturn = new ArrayList<>();
        
        for (Integer i : indexes)
        {
            soundsToReturn.add(sounds.get(i));
        }
        
        return soundsToReturn;
    }
    
    public ArrayList<Font> getFonts(int[] indexes)
    {
        ArrayList<Font> fontsToReturn = new ArrayList<>();
        
        for (Integer i : indexes)
        {
            fontsToReturn.add(fonts.get(i));
        }
        
        return fontsToReturn;
    }
}
