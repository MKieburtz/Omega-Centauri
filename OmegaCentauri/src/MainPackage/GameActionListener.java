package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public interface GameActionListener 
{
    public void gameStart();
    public void enteredFullScreen();
    public void exitedFullScreen();
    public void settingsChangedToHigh();
    public void settingsChangedToLow();
    public void entityDoneExploding(GameEntity entity);
}
