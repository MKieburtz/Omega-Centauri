package MainPackage;

import java.awt.Font;
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

    private ArrayList<BufferedImage> imagesToReturn = new ArrayList<>();
    private ArrayList<Clip> soundsToReturn = new ArrayList<>();
    private ArrayList<Font> fontsToReturn = new ArrayList<>();

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
        imagePaths.add("resources/FILLERshield.png");
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

    }

    class FontInfo {

        public String path;
        public Float size;

        public FontInfo(String path, Float size) {
            this.path = path;
            this.size = size;
        }
        
        @Override
        public boolean equals(Object other)
        {
            if (!(other instanceof FontInfo))
            {
                return false;
            }
            else
            {
                FontInfo info = (FontInfo)other;
                if (info.path.equals(this.path) && info.size == size)
                {
                    return true;
                }
            }
            return false;
        }
    }

    public ArrayList<BufferedImage> getImagesForPlayer() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/FighterIdle.png"));
        imagesToReturn.add(images.get("resources/FighterThrust.png"));
        imagesToReturn.add(images.get("resources/FighterLeft.png"));
        imagesToReturn.add(images.get("resources/FighterRight.png"));
        imagesToReturn.add(images.get("resources/FighterThrustLeft.png"));
        imagesToReturn.add(images.get("resources/FighterThrustRight.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<Clip> getSoundsForPlayer() {
        clearList(ThingsToClear.sounds);
        soundsToReturn.add(sounds.get("resources/Pulse.wav"));
        return (ArrayList<Clip>) soundsToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForEnemyFighter() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/EnemyFighterIdle.png"));
        imagesToReturn.add(images.get("resources/EnemyFighterThrusting.png"));
        imagesToReturn.add(images.get("resources/EnemyFighterTurningLeft.png"));
        imagesToReturn.add(images.get("resources/EnemyFighterTurningRight.png"));
        imagesToReturn.add(images.get("resources/EnemyFighterThrustingLeft.png"));
        imagesToReturn.add(images.get("resources/EnemyFighterThrustingRight.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForMediumEnemyFighter() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/MediumEnemyFighter.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForTurret() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/Turret.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForHUD() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/TempScannerModule.png"));
        imagesToReturn.add(images.get("resources/healthbackground.png"));
        imagesToReturn.add(images.get("resources/HealthyTick.png"));
        imagesToReturn.add(images.get("resources/WarningTick.png"));
        imagesToReturn.add(images.get("resources/DangerTick.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForMainMenu() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/StartButtonNoHover.png"));
        imagesToReturn.add(images.get("resources/StartButtonHover.png"));
        imagesToReturn.add(images.get("resources/CloseButtonNoHover.png"));
        imagesToReturn.add(images.get("resources/CloseButtonHover.png"));
        imagesToReturn.add(images.get("resources/SettingsButtonNoHover.png"));
        imagesToReturn.add(images.get("resources/SettingsButtonHover.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<Clip> getSoundsForMainMenu() {
        clearList(ThingsToClear.sounds);
        soundsToReturn.add(sounds.get("resources/Mouseclick.wav"));
        return (ArrayList<Clip>) soundsToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForPulseShot() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/Pulse.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForEnemyPulseShot() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/EnemyShot.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<Font> getFontsForRenderer() {
        clearList(ThingsToClear.fonts);
        fontsToReturn.add(fonts.get(new FontInfo("resources/OCR A Std.ttf", 10f)));
        return (ArrayList<Font>) fontsToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForRenderer() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/PauseMenu.png"));
        imagesToReturn.add(images.get("resources/PauseButton_ToMenu.png"));
        imagesToReturn.add(images.get("resources/GameOver.png"));
        imagesToReturn.add(images.get("resources/ReturnToTheBattlefield.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForSettingsMenu() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/RadioButtonEnabled.png"));
        imagesToReturn.add(images.get("resources/RadioButtonDisabled.png"));
        imagesToReturn.add(images.get("resources/BackButtonHover.png"));
        imagesToReturn.add(images.get("resources/BackButtonNoHover.png"));
        imagesToReturn.add(images.get("resources/ControlsButtonHover.png"));
        imagesToReturn.add(images.get("resources/ControlsButtonNoHover.png"));
        imagesToReturn.add(images.get("resources/ResetButtonHover.png"));
        imagesToReturn.add(images.get("resources/ResetButtonNoHover.png"));
        imagesToReturn.add(images.get("resources/SaveButtonHover.png"));
        imagesToReturn.add(images.get("resources/SaveButtonNoHover.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<Clip> getSoundsForSettingsMenu() {
        clearList(ThingsToClear.sounds);
        soundsToReturn.add(sounds.get("resources/Mouseclick.wav"));
        return (ArrayList<Clip>) soundsToReturn.clone();
    }

    public ArrayList<Font> getFontsForSettingsMenu() {
        clearList(ThingsToClear.fonts);
        fontsToReturn.add(fonts.get(new FontInfo("resources/OCR A Std.ttf", 50f)));
        fontsToReturn.add(fonts.get(new FontInfo("resources/OCR A Std.ttf", 32f)));
        fontsToReturn.add(fonts.get(new FontInfo("resources/OCR A Std.ttf", 24f)));
        fontsToReturn.add(fonts.get(new FontInfo("resources/OCR A Std.ttf", 16f)));
        return (ArrayList<Font>) fontsToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForShield() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/FILLERshield.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForEnemyShield() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/FILLERshieldEnemy.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForTurretShot() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/Level2Shot.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImagesForMissle() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/Missile.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImageForFighterExplosion() {
        clearList(ThingsToClear.images);
        imagesToReturn.add(images.get("resources/FighterExplosionSpritesheet.png"));
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
    }

    public ArrayList<BufferedImage> getImageForMissileExplosion() {
        clearList(ThingsToClear.images);
        imagePaths.add("resources/MissileExplosionSpritesheet.png");
        return (ArrayList<BufferedImage>) imagesToReturn.clone();
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

    private void clearList(ThingsToClear whatToClear) {
        switch (whatToClear) {
            case images:
                imagesToReturn.clear();
                break;
            case sounds:
                soundsToReturn.clear();
                break;
            case fonts:
                fontsToReturn.clear();
                fonts.clear();
                break;
        }
    }

}
